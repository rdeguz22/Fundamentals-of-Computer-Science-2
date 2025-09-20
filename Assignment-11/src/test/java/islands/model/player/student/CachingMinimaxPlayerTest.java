package islands.model.player.student;

import islands.model.player.MinimaxPlayer;
import islands.model.player.MinimaxPlayerTest;

public class CachingMinimaxPlayerTest extends MinimaxPlayerTest {
    @Override
    public MinimaxPlayer getNewPlayer() {
        return new CachingMinimaxPlayer();
    }
}
