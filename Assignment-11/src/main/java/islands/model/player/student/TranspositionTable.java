package islands.model.player.student;

import islands.model.GameModel;
import islands.model.Move;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A table for storing and retrieving the results of calls to {@link
 * islands.model.player.SimulatedGameTreePlayer#getMyMove(GameModel, int, islands.model.TileColor)}.
 */
public class TranspositionTable {
    // A record is the Java equivalent of a Kotlin data class.
    private record CachedInfo(int depth, Move move) {
    }

    // We need the thread-safe version of HashMap in case this becomes a tournament entry.
    private final Map<String, CachedInfo> cachedMoves = new ConcurrentHashMap<>();

    /**
     * Constructs an empty transposition table.
     */
    public TranspositionTable() {
    }

    /**
     * Records that calling {@link
     * islands.model.player.SimulatedGameTreePlayer#getMyMove(GameModel, int, islands.model.TileColor)}
     * with the given model and depth produced the specified move.
     *
     * @param model the model
     * @param depth the depth
     * @param move  the move
     */
    public void putMove(GameModel model, int depth, Move move) {
        // Make an addition to the hash table.
        // The key should be the model's board string.
        // The value should be an instance of CachedInfo with the depth and move.
        String boardString = model.getBoardString();
        CachedInfo info = new CachedInfo(depth, move);
        cachedMoves.put(boardString, info);
        // Add the transformed board and move to the hash table.
        putTransformation(model.getSize(), boardString, info);
    }

    // Adds entry for the board rotated 180 degrees.
    private void putTransformation(int size, String boardString, CachedInfo cachedInfo) {
        // Generate the board string of the 180-degree rotation of
        // the current model, using its board string. While board strings
        // are not arrays, you can get any row and column position of
        // boardString by calling the helper method getTileChar().
        StringBuilder transformed = new StringBuilder();
        // You will have to build up the other board string one character
        // at a time using the translation formula provided on Canvas:
        //   (row, col) ---> (size - 1 - row, size - 1 - col)
        // Don't forget to add a newline character at the end of each row.
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                char c = getTileChar(size, boardString, size - 1 - row, size - 1 - col);
                transformed.append(c);
            }
            transformed.append('\n');
        }
        // In addition to transforming the board, you need to use the same formula
        // to translate the move. Add the new board and move to the hash table.
        Move originalMove = cachedInfo.move();
        Move transformedMove = new Move(
                size - 1 - originalMove.getPosition().row(),
                size - 1 - originalMove.getPosition().column(),
                originalMove.value()
        );
        cachedMoves.put(transformed.toString(), new CachedInfo(cachedInfo.depth(), transformedMove));
    }

    private static char getTileChar(int size, String boardString, int row, int col) {
        int offset = row * (size + 1) + col; // + 1 for the newline character in boardString
        return boardString.charAt(offset);
    }

    /**
     * Checks whether this table has the move recommended for this model
     * (or its transformations) when searching to the specified depth or
     * deeper.
     *
     * @param model the model
     * @param depth the minimum search depth
     * @return true if a move is available, false otherwise
     */
    public boolean hasMove(GameModel model, int depth) {
        // Check if the hash table has an entry with the model's board string as the key
        // and that the associated value includes a depth greater than or equal
        // to the passed depth.
        CachedInfo info = cachedMoves.get(model.getBoardString());
        return info != null && info.depth() >= depth;
    }

    /**
     * Gets the stored move for this model computed to the given depth or
     * deeper.
     *
     * @param model the model
     * @param depth the depth
     * @return the stored move
     * @throws NoSuchElementException if this table does not have an entry
     *                                with the specified model with a depth
     *                                greater than or equal to the
     *                                requested depth
     */
    public Move getMove(GameModel model, int depth) {
        CachedInfo info = cachedMoves.get(model.getBoardString());
        if (info != null && info.depth() >= depth) {
            return info.move();
        }
        throw new NoSuchElementException("No move cached at depth " + depth + ", or deeper, for this board.");
    }
}
