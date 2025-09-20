package islands.model.player;

import islands.model.GameModel;
import islands.model.RowColPair;
import islands.model.TileColor;

import java.util.Random;

/**
 * A simulated Islands of Hex player who places tiles randomly.
 */
public class SimulatedRandomPlayer extends SimulatedPlayer {
    // The maximum number of tries to choose a move randomly.
    // If this is surpassed, just choose the first available cell.
    private static final int MAX_RANDOM_TRIES = 50;

    // Hardcode seed for debugging. Remove it for move randomness.
    private final Random random = new Random(123);

    /**
     * Constructs a simulated random player.
     */
    public SimulatedRandomPlayer() {
    }

    @Override
    public String getName() {
        return "Random";
    }

    @Override
    public RowColPair chooseNextMove(GameModel model, TileColor tileColor) {
        int size = model.getSize();

        // Try to choose a random point.
        for (int tries = 0; tries < MAX_RANDOM_TRIES; tries++) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);

            if (model.canPlay(row, col)) {
                return new RowColPair(row, col);
            }
        }

        // If we haven't found one, just find the first match.
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (model.canPlay(row, col)) {
                    return new RowColPair(row, col);
                }
            }
        }

        // This code should be unreachable in the completed game, because this
        // method wouldn't be called unless there is an empty cell.
        // This code is reached if the stubs in GameModelImplementation are not
        // replaced with working implementations.
        throw new RuntimeException("GameModelImplementation is incorrect.");
    }
}
