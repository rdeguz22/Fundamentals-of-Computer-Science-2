package islands.model.player.student;

import islands.model.GameModel;
import islands.model.Move;
import islands.model.player.student.TranspositionTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static islands.model.TestHelperMethods.makeModel;
import static org.junit.jupiter.api.Assertions.*;

public class TranspositionTableTest {
    private static final int SAMPLE_DEPTH = 3;
    private static final int SAMPLE_VALUE = 20;
    GameModel model3a, model3b, model3c, model3d;
    List<GameModel> equivalentModels;
    List<GameModel> nonequivalentModels;
    TranspositionTable table;
    Move sampleMove;

    @BeforeEach
    public void setup() {
        model3a = makeModel("WWn", "Bnn", "nnB");
        model3b = makeModel("Bnn", "nnB", "nWW");
        model3c = makeModel("Bnn", "nnW", "nBW");
        model3d = makeModel("WBn", "Wnn", "nnB");
        nonequivalentModels = List.of(model3c, model3d);
        table = new TranspositionTable();
        sampleMove = new Move(2, 1, SAMPLE_VALUE);
        table.putMove(model3a, SAMPLE_DEPTH, sampleMove);
    }

    @Test
    public void testHasMove() {
        // If we add an entry with a depth of 3, it should satisfy requests
        // for depths of 0-2 also (but not depths greater than 3).
        for (int i = 0; i <= SAMPLE_DEPTH; i++) {
            assertTrue(table.hasMove(model3a, i));
        }
        assertFalse(table.hasMove(model3a, SAMPLE_DEPTH + 1));
    }

    @Test
    public void testGetMove() {
        // If we add an entry with a depth of 3, it should satisfy requests
        // for depths of 0-2 also (but not depths greater than 3).
        for (int i = 0; i <= SAMPLE_DEPTH; i++) {
            assertEquals(sampleMove, table.getMove(model3a, i));
        }
    }

    @Test
    public void testTransformation() {
        assertEquals(new Move(0, 1, SAMPLE_VALUE), table.getMove(model3b, 3));
        for (GameModel model : nonequivalentModels) {
            assertFalse(table.hasMove(model, 1));
        }
    }
}
