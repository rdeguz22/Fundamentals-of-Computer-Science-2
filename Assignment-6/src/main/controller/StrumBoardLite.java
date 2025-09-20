package controller;

import edu.princeton.cs.stdlib.StdAudio;
import edu.princeton.cs.stdlib.StdDraw;

import model.GuitarString;

import java.awt.event.KeyEvent;

/**
 * Sample client of {@link GuitarString} that creates and plucks A and C
 * strings and enables the user to do so.
 */
public final class StrumBoardLite {
    private static final double CONCERT_A = 440.0;
    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);

    private StrumBoardLite() {
    }

    /**
     * Plucks the A and C strings and enables the user to do so.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        // Initialize StdDraw so keyboard input will work.
        StdDraw.point(0.5, 0.5);

        // Create two guitar strings, for concert A and C
        GuitarString stringA = new GuitarString(CONCERT_A);
        GuitarString stringC = new GuitarString(CONCERT_C);

        stringA.pluck();
        stringC.pluck();

        // the main input loop
        while (true) {
            // check if the user has typed a key, and, if so, process it
            if (StdDraw.hasNextKeyTyped()) {

                // the user types this character
                char key = StdDraw.nextKeyTyped();

                // pluck the corresponding string
                if (key == 'a') {
                    stringA.pluck();
                }
                if (key == 'c') {
                    stringC.pluck();
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

            // compute the superposition of the samples
            double sample = (stringA.sample() + stringC.sample()) / 2;

            // send the result to standard audio
            StdAudio.play(sample);

            // advance the simulation of each guitar string by one step
            stringA.tick();
            stringC.tick();
        }
    }
}
