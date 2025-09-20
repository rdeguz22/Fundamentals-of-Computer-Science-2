package islands.model.player.student;

import islands.model.GameModel;
import islands.model.Move;
import islands.model.TileColor;
import islands.model.player.MinimaxPlayer;

/**
 * A player that uses caching to improve on the minimax algorithm.
 *
 * @see TranspositionTable
 */
public class CachingMinimaxPlayer extends MinimaxPlayer {
    private final TranspositionTable cache = new TranspositionTable();

    /**
     * Constructs a caching minimax player.
     */
    public CachingMinimaxPlayer() {
    }

    @Override
    public String getName() {
        return "Caching Minimax";
    }

    @Override
    public Move getMyMove(GameModel model, int depth, TileColor tileColor) {
        if (!model.isGameOver() && depth > 0 && cache.hasMove(model, depth)) {
            return cache.getMove(model, depth);
        }
        Move move = super.getMyMove(model, depth, tileColor);
        if (!model.isGameOver() && depth > 0 && move.getPosition() != null) {
            cache.putMove(model, depth, move);
        }
        return move;
    }
}
