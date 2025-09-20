package student;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.util.List;
import java.util.Random;

/**
 * An implementation of Mozart's musical dice game to generate waltzes.
 */
public class WaltzGenerator {
    // Constants
    private static final int NUM_SIDES = 6;
    private static final int NUM_MEASURES_IN_MINUET = 16;
    private static final int NUM_DICE_FOR_MINUET = 2;
    private static final int NUM_MEASURES_IN_TRIO = 16;
    private static final int NUM_DICE_FOR_TRIO = 1;
    private static final String MINUET_PATH = "./res/minuet.csv";
    private static final String TRIO_PATH = "./res/trio.csv";

    // Instance variables
    private final long seed; // initialized in constructor
    private final Random random; // initialized in constructor
    private double[] waltz; // initialized lazily in getWaltz()

    /**
     * Constructs a new waltz generator.
     */
    public WaltzGenerator() {
        // This version of the constructor does not get a seed as a
        // parameter. Create a seed by getting the current time in
        // milliseconds, and call the other constructor with that as
        // an argument.
        this(System.currentTimeMillis());
    }

    /**
     * Constructs a new waltz generator using the specified seed for dice rolls.
     *
     * @param seed the seed
     */
    public WaltzGenerator(long seed) {
        // Initialize the appropriate instance variables.
        this.seed = seed;
        this.random = new Random(seed);
    }

    private double[] getWaltz() throws IOException {
        if (waltz == null) {
            String[] minuetMeasures = makeMinuet(buildTable(MINUET_PATH));
            String[] trioMeasures = makeTrio(buildTable(TRIO_PATH));
            waltz = SupportCode.buildWaltz(minuetMeasures, trioMeasures);
        }
        return waltz;
    }

    /**
     * Plays the waltz created by this generator. If called repeatedly on
     * the same instance (or another instance with the same {@link #seed}),
     * this always plays the same waltz.
     *
     * @throws LineUnavailableException if {@link javax.sound.sampled.AudioSystem}
     *                                  is unavailable to satisfy requests
     * @throws IOException              if the files containing dice data or music cannot be
     *                                  read
     */
    public void playWaltz() throws LineUnavailableException, IOException {
        StdAudio.play(getWaltz());
    }

    /**
     * Saves the waltz created by this generator into a file whose name
     * includes the {@link #seed}.
     *
     * @return the name of the file
     * @throws IOException if the files containing dice data or music cannot be
     *                     read
     */
    public String saveWaltz() throws IOException {
        String filename = String.format("waltz-%s.wav", seed);
        StdAudio.save(filename, getWaltz());
        return filename;
    }

    // Returns the total of rolling the specified number of 6-sided dice.
    // This should throw IllegalArgumentException if numDice < 1.
    @VisibleForTesting
    int rollDice(int numDice) {
        if (numDice < 1) {
            throw new IllegalArgumentException("numDice has to be greater than 0.");
        }
        int diceValue = 0;
        for (int i = 0; i < numDice; i++) {
            diceValue += random.nextInt(1, NUM_SIDES + 1);
        }
        return diceValue;
    }

    @VisibleForTesting
    String[] convertCsvToStringArray(String data) {
        return data.split(", ");
    }

    // You will need to implement this method, which takes a path as an argument
    // and returns a two-dimensional array of integers (like the ones shown on
    // Canvas) represented as Strings (e.g., "3", not 3).
    private String[][] buildTable(String path) throws IOException {
        // Read the lines from the file as the specified path.
        File file = new File(path);
        List<String> lines = Files.readLines(file, Charsets.UTF_8);

        // The remainder of this method should be pulled into a helper method
        // with the same name that takes a List<String> as an argument.

        // Get the number of dice from the first line.

        // Allocate a 2D array of String with the necessary number of rows
        // for the table. For example, if there are 2 dice, you would allocate
        // an array with 13 rows (because 12 is the largest total of two dice).
        // The first two rows would never be used, since 0 and 1 could never
        // be the total of two dice. Make those rows 0-element arrays.

        // You are encouraged to create well-named local variables, such as numRows.

        // Iterate over the remaining lines, splitting each into an array of
        // String, which is returned and assigned to the appropriate row.

        // You may assume that the input is one or more valid numbers
        // delimited by ", ". You don't have to check if lines are null,
        // empty, malformed, etc.

        // Return the populated 2D-array.

        return buildTable(lines);
    }

    @VisibleForTesting
    String[][] buildTable(List<String> lines) {
        int n = Integer.parseInt(lines.get(0));
        String[][] table = new String[lines.size()][];
        for (int i = 0; i < n; i++) {
            table[i] = new String[0];
        }
        for (int i = n; i < lines.size(); i++) {
            String line = lines.get(i);
            table[i] = line.split(", ");
        }
        return table;
    }


    // Create and populate an array of samples to use for the minuet.
    private String[] makeMinuet(String[][] minuetMeasures) {
        // For each entry, use the result of rolling two dice and the
        // measure number to look up a sample number in minuetMeasures.
        // Prepend the retrieved string with "M" before adding it to the
        // result array. For example, if the first dice roll totals 7,
        // the first entry in the result array will be "M104" (referring to
        // the table on Canvas).
        String[] minuet = new String[16];
        for (int i = 0; i < 16; i++) {
            int diceRoll = random.nextInt(1, 12);
            String measure = minuetMeasures[diceRoll][i];
            minuet[i] = "M" + measure;
        }

        return minuet;
    }

    // Create and populate an array of samples to use for the trio.
    private String[] makeTrio(String[][] trioMeasures) {
        String[] trio = new String[16];
        for (int i = 0; i < 16; i++) {
            int diceRoll = random.nextInt(1, 6);
            String measure = trioMeasures[diceRoll][i];
            trio[i] = "T" + measure;
        }
        return trio;
    }

    public static void main(String[] args) throws LineUnavailableException, IOException {
        WaltzGenerator generator = new WaltzGenerator();
        generator.playWaltz();
        // Uncomment the following two lines to save your waltzes.
        // String filename = generator.saveWaltz();
        // System.out.printf("Saved waltz to %s.%n", filename);
    }
}
