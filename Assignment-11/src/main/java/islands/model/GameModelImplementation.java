package islands.model;

import java.util.*;

/**
 * A concrete representation of the state of a game of Islands of Hex.
 */
public class GameModelImplementation implements GameModel {
    private int size;
    private Graph<TileColor> graph;
    private Graph.Node<TileColor>[][] board;

    // used by deepCopy()
    private GameModelImplementation() {
        // instance variables will be initialized by caller
    }

    /**
     * Constructs a model with an empty game board with the specified
     * number of rows and columns.
     *
     * @param size the number of rows (and the number of columns) on the board
     */
    public GameModelImplementation(int size) {
        this.size = size;
        graph = new Graph<>();

        board = (Graph.Node<TileColor>[][]) new Graph.Node<?>[size][size];
        addBoardNodes();
        addBoardEdges();
    }

    private void addBoardNodes() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Graph.Node<TileColor> node = graph.addNode(TileColor.NONE);
                board[row][col] = node;
            }
        }
    }

    private void addBoardEdges() {
        // Add edges between pairs of nodes.
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                Graph.Node<TileColor> node = board[row][col];
                addEdgeIfInBounds(node, row, col + 1); // east
                addEdgeIfInBounds(node, row + 1, col + 1); // southeast
                addEdgeIfInBounds(node, row + 1, col); // south
            }
        }
    }

    private void addEdgeIfInBounds(Graph.Node<TileColor> node, int row, int col) {
        if (isInBounds(row, col)) {
            graph.addEdge(node, board[row][col]);
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    @Override
    public String toString() {
        return getBoardString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof GameModel otherModel) {
            return getBoardString().equals(otherModel.getBoardString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }


    @Override
    public int getSize() {
        return board.length;
    }

    @Override
    public TileColor getTileColor(int row, int col) {
        return board[row][col].getData();
    }

    @Override
    public boolean canPlay(int row, int col) {
        if (isOutOfBounds(row, col)) {
            throw new IllegalArgumentException("Position is out of bounds.");
        }
        return board[row][col].getData() == TileColor.NONE;
    }

    private boolean isOutOfBounds(int row, int col) {
        return !isInBounds(row, col);
    }

    @Override
    public void makePlay(int row, int col, TileColor tileColor) {
        // The call will throw IllegalArgumentException if the position is out
        // of bounds.
        if (!canPlay(row, col)) {
            throw new IllegalArgumentException("The position is already occupied");
        }
        if (tileColor == TileColor.NONE) {
            throw new IllegalArgumentException("Color cannot be EMPTY");
        }
        Graph.Node<TileColor> node = board[row][col];
        node.setData(tileColor);
        for (Graph.Node<TileColor> neighbor : node.getNeighbors()) {
            if (neighbor.getData() == tileColor) {
                node.union(neighbor);
            }
        }
    }

    @Override
    public int getScore(TileColor tileColor) {
        return graph.getSetCount(tileColor);
    }

    @Override
    public boolean isGameOver() {
        return hasVerticalPath() || hasHorizontalPath();
    }

    private boolean hasHorizontalPath() {
        Set<Graph.Node<TileColor>> islands = new HashSet<>();

        // Build set of islands in left column.
        for (int row = 0; row < size; row++) {
            Graph.Node<TileColor> node = board[row][0];
            if (node.getData() == TileColor.BLACK) {
                islands.add(node.find());
            }
        }

        // Check for matches in the right column.
        for (int row = 0; row < size; row++) {
            Graph.Node<TileColor> node = board[row][size - 1];
            if (node.getData() == TileColor.BLACK && islands.contains(node.find())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasVerticalPath() {
        Set<Graph.Node<TileColor>> islands = new HashSet<>();

        // Build set of islands in top row.
        for (int col = 0; col < size; col++) {
            Graph.Node<TileColor> node = board[0][col];
            if (node.getData() == TileColor.WHITE) {
                islands.add(node.find());
            }
        }

        // Check for matches in the bottom row.
        for (int col = 0; col < size; col++) {
            Graph.Node<TileColor> node = board[size - 1][col];
            if (node.getData() == TileColor.WHITE && islands.contains(node.find())) {
                return true;
            }
        }
        return false;
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

    @Override
    public GameModelImplementation deepCopy() {
        GameModelImplementation copy = new GameModelImplementation();
        copy.size = size;
        copy.graph = graph.deepCopy();
        copy.board = (Graph.Node<TileColor>[][]) new Graph.Node<?>[size][size];
        // Add nodes to board in the order they were added to the graph.
        Iterator<Graph.Node<TileColor>> iterator = copy.graph.getNodes().iterator();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                copy.board[row][col] = iterator.next();
            }
        }
        return copy;
    }
}
