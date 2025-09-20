package islands.model.player.student;

import islands.model.GameModel;
import islands.model.Move;
import islands.model.RowColPair;
import islands.model.TileColor;
import islands.model.player.MinimaxPlayer;

/**
 * A player that chooses the highest-scoring move based on the assumption
 * that the opponent chooses moves randomly.
 */
public class RandomMaxPlayer extends MinimaxPlayer {
    /**
     * Constructs a player that chooses the highest-scoring move based on the assumption that the
     * opponent chooses moves randomly.
     */
    public RandomMaxPlayer() {
    }

    @Override
    public String getName() {
        return "RandomMax";
    }

    // Because this only does the opponent move, it does not need to provide a move, just a value.
    @Override
    public double getOpponentValue(GameModel model, int depth, TileColor tileColor) {
        if (depth < 0) {
            throw new IllegalArgumentException();
        }
        if (depth == 0 || model.isGameOver()) {
            return getValue(model, tileColor.getOpposite());
        }
        double total = 0.0;
        int moveCount = 0;
        for (RowColPair position : getLegalPositions(model)) {
            GameModel child = model.deepCopy();
            child.makePlay(position.row(), position.column(), tileColor);
            Move childMove = getMyMove(child, depth - 1, tileColor.getOpposite());
            total += childMove.value();
            moveCount++;
        }
        if (moveCount == 0) {
            return 0.0;
        } else {
            return total / moveCount;
        }
    }
}
