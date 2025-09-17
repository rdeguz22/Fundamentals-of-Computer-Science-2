package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WaltzGeneratorTest {
    WaltzGenerator wg;

    @BeforeEach
    public void setup() {
        wg = new WaltzGenerator();
    }

    @Test
    public void rollDiceIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> wg.rollDice(0));
        assertThrows(IllegalArgumentException.class, () -> wg.rollDice(-1));
    }

    @Test
    public void rollDiceRolls() {
        for (int i = 1; i < 15; i++) {
            int total = wg.rollDice(i);
            int upperBounds = 6 * i;
            int lowerBounds = i;
            assertTrue((total >= lowerBounds) && (total <= upperBounds));
        }
    }

    @Test
    public void buildTableForTwo() throws IOException {
        List<String> input = new ArrayList<String>();
        input.add("2");
        input.add("96, 22, 141");
        input.add("32, 6, 128");
        input.add("69, 95, 158");
        input.add("40, 17, 113");
        input.add("148, 74, 163");
        input.add("104, 157, 27");
        input.add("152, 60, 171");
        input.add("119, 84, 114");
        input.add("98, 142, 42");
        input.add("3, 87, 165");
        input.add("54, 130, 10");
        String[][] expected = new String[input.size()][];
        expected[0] = new String[]{};
        expected[1] = new String[]{};
        for (int i = 2; i < input.size(); i++) {
            expected[i] = input.get(i).split(", ");
        }
        assertTrue(Arrays.deepEquals(wg.buildTable(input), expected));
    }

    @Test
    public void buildTableForOne() throws IOException {
        List<String> input = new ArrayList<String>();
        input.add("1");
        input.add("72, 6, 59");
        input.add("56, 82, 42");
        input.add("75, 39, 54");
        input.add("40, 73, 16");
        input.add("83, 3, 28");
        input.add("18, 45, 62");
        String[][] arr = {
                {},
                {"72", "6", "59"},
                {"56", "82", "42"},
                {"75", "39", "54"},
                {"40", "73", "16"},
                {"83", "3", "28"},
                {"18", "45", "62"}
        };
        assertTrue(Arrays.deepEquals(wg.buildTable(input), arr));
    }
}