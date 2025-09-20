package islands.model.student;

import com.google.common.annotations.VisibleForTesting;
import islands.model.GameModel;
import islands.model.Graph;
import islands.model.TileColor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A concrete representation of the state of a game of Islands of Hex.
 */
public class GameModelImplementation implements GameModel {
    private final int size;
    private final TileColor[][] board;
    private final Graph.Node<TileColor>[][] nodes;

    /**
     * Constructs a model with an empty game board with the specified
     * number of rows and columns.
     *
     * @param size the number of rows (and the number of columns) on the board
     */
    public GameModelImplementation(int size) {
        this.size = size;
        this.board = new TileColor[size][size];
        this.nodes = new Graph.Node[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                nodes[i][j] = new Graph.Node<>(null);
            }
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public TileColor getTileColor(int row, int col) {
        if (board[row][col] == null) {
            return TileColor.NONE;
        }
        return board[row][col];
    }

    @Override
    public boolean canPlay(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Row or column is out of bounds.");
        }
        return board[row][col] == null || board[row][col] == TileColor.NONE;
    }

    @Override
    public void makePlay(int row, int col, TileColor tileColor) {
        if (!canPlay(row, col)) {
            throw new IllegalArgumentException("Cannot play at this position.");
        }
        board[row][col] = tileColor;
        nodes[row][col] = new Graph.Node<>(tileColor);
        List<int[]> adjacentPositions = getAdjacentSurrounding(row, col);
        for (int[] position : adjacentPositions) {
            int adjRow = position[0];
            int adjCol = position[1];
            if (adjRow >= 0 && adjRow < size && adjCol >= 0 && adjCol < size) {
                if (board[adjRow][adjCol] == tileColor) {
                    nodes[row][col].union(nodes[adjRow][adjCol]);
                }
            }
        }
    }

    @VisibleForTesting
    private List<int[]> getAdjacentSurrounding(int row, int col) {
        return List.of(
                new int[]{row - 1, col},
                new int[]{row + 1, col},
                new int[]{row, col - 1},
                new int[]{row, col + 1}
        );
    }

    @Override
    public int getScore(TileColor tileColor) {
        Set<Graph.Node<TileColor>> uniqueRoots = new HashSet<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == tileColor) {
                    uniqueRoots.add(nodes[row][col].find());
                }
            }
        }
        return uniqueRoots.size();
    }

    @Override
    public boolean isGameOver() {
        for (int row = 0; row < size; row++) {
            if (isRowComplete(row)) {
                return true;
            }
        }
        for (int col = 0; col < size; col++) {
            if (isColumnComplete(col)) {
                return true;
            }
        }
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == null || board[row][col] == TileColor.NONE) {
                    return false;
                }
            }
        }
        return true;
    }

    @VisibleForTesting
    private boolean isRowComplete(int row) {
        TileColor color = board[row][0];
        if (color == null) {
            return false;
        }
        for (int col = 1; col < size; col++) {
            if (board[row][col] != color) {
                return false;
            }
        }
        return true;
    }

    @VisibleForTesting
    private boolean isColumnComplete(int col) {
        TileColor color = board[0][col];
        if (color == null) {
            return false;
        }
        for (int row = 1; row < size; row++) {
            if (board[row][col] != color) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getBoardString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                sb.append(getTileColor(row, col).getAbbreviation());
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
