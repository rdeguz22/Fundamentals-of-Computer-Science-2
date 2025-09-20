package islands.model.player.student;

import islands.model.player.MinimaxPlayer;
import islands.model.player.MinimaxPlayerTest;

public class AlphaBetaPlayerTest extends MinimaxPlayerTest {
    @Override
    public MinimaxPlayer getNewPlayer() {
        return new AlphaBetaPlayer();
    }
}
