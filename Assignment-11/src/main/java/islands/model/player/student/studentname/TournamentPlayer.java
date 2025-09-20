package islands.model.player.student.studentname; // TODO: Change package.

import islands.model.*;
import islands.model.player.student.AlphaBetaPlayer;
import islands.model.player.TimeLimitedSimulatedPlayer;

/**
 * A simulated player for a timed tournament.
 */
public class TournamentPlayer extends TimeLimitedSimulatedPlayer {
    private final AlphaBetaPlayer player = new AlphaBetaPlayer();

    /**
     * Creates a simulated player for a timed tournament.
     */
    public TournamentPlayer() {
    }

    @Override
    public String getName() {
        return "Ellen's timed player";
    }

    @Override
    public void makeMove(GameModel model, TileColor tileColor, Listener listener) {
        int depth = 1;

        // Loop is interrupted externally when time runs out.
        while (!Thread.interrupted() && depth < model.getSize() * model.getSize()) {
            RowColPair position = player.chooseNextMove(model, depth, tileColor);
            listener.receiveMove(position);
            depth++;
        }
    }
}
