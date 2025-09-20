package islands.model.student;

import islands.model.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static islands.model.student.TestHelperMethods.makeModel;
import static org.junit.jupiter.api.Assertions.*;

class GameModelImplementationTest {
    GameModelImplementation model;

    @BeforeEach
    public void setup() {
        model = new GameModelImplementation(3);
    }


    @Test
    public void getBoardStringWorks3() {
        assertEquals("nnn\nnnn\nnnn\n", model.getBoardString());
    }

    @Test
    public void testBoardInitialization() {
        assertEquals(3, model.getSize());
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                assertEquals(TileColor.NONE, model.getTileColor(row, col));
            }
        }
    }

    @Test
    public void testGetTileColor() {
        assertEquals(TileColor.NONE, model.getTileColor(0, 0));
        model.makePlay(0, 0, TileColor.WHITE);
        assertSame(TileColor.WHITE, model.getTileColor(0, 0));
        model.makePlay(1, 0, TileColor.BLACK);
        assertSame(TileColor.BLACK, model.getTileColor(1, 0));
    }

    @Test
    public void testCanPlay() {
        assertTrue(model.canPlay(0, 0));
        model.makePlay(0, 0, TileColor.WHITE);
        assertFalse(model.canPlay(0, 0));
        model.makePlay(1, 0, TileColor.BLACK);
        assertFalse(model.canPlay(1, 0));
        assertThrows(IllegalArgumentException.class, () -> model.makePlay(model.getSize() + 1, 0, TileColor.BLACK));
        assertThrows(IllegalArgumentException.class, () -> model.makePlay(0, model.getSize() + 1, TileColor.WHITE));
        assertThrows(IllegalArgumentException.class, () -> model.makePlay(-1, 0, TileColor.BLACK));
        assertThrows(IllegalArgumentException.class, () -> model.makePlay(0, -1, TileColor.WHITE));
    }

    @Test
    public void testMakePlay() {
        model.makePlay(0, 0, TileColor.WHITE);
        assertEquals(TileColor.WHITE, model.getTileColor(0, 0));
        model.makePlay(0, 1, TileColor.BLACK);
        assertEquals(TileColor.BLACK, model.getTileColor(0, 1));
        assertThrows(IllegalArgumentException.class, () -> model.makePlay(0, 0, TileColor.BLACK));
    }

    @Test
    public void testGetScore() {
        model.makePlay(0, 0, TileColor.WHITE);
        assertEquals(1, model.getScore(TileColor.WHITE));
        model.makePlay(0, 2, TileColor.WHITE);
        assertEquals(2, model.getScore(TileColor.WHITE));
        model.makePlay(1, 0, TileColor.BLACK);
        assertEquals(1, model.getScore(TileColor.BLACK));
    }

    @Test
    public void testIsGameOver() {
        model.makePlay(0, 0, TileColor.BLACK);
        model.makePlay(0, 1, TileColor.WHITE);
        model.makePlay(0, 2, TileColor.BLACK);
        model.makePlay(1, 0, TileColor.WHITE);
        model.makePlay(1, 1, TileColor.BLACK);
        model.makePlay(1, 2, TileColor.WHITE);
        model.makePlay(2, 0, TileColor.BLACK);
        model.makePlay(2, 1, TileColor.WHITE);
        assertFalse(model.isGameOver());
        model.makePlay(2, 2, TileColor.BLACK);
        assertTrue(model.isGameOver());
    }

    @Test
    public void testIsGameOverRow() {
        model.makePlay(1, 0, TileColor.WHITE);
        model.makePlay(1, 1, TileColor.WHITE);
        assertFalse(model.isGameOver());
        model.makePlay(1, 2, TileColor.WHITE);
        assertTrue(model.isGameOver());
    }

    @Test
    public void testIsGameOverColumn() {
        model.makePlay(0, 1, TileColor.WHITE);
        model.makePlay(1, 1, TileColor.WHITE);
        assertFalse(model.isGameOver());
        model.makePlay(2, 1, TileColor.WHITE);
        assertTrue(model.isGameOver());
    }
}
