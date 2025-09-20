package model;

import edu.princeton.cs.stdlib.StdAudio;

/**
 * A mapping from keys to guitar strings.
 */
public class GuitarKeyboard {
    private static final String KEY_CHARS = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static final double A4_FREQ = 440;
    private static final double SEMITONE_RATIO = 1.05956;
    private static final int A4_OFFSET = 24;

    private final GuitarString[] strings;

    /**
     * Constructs the mapping from keys to guitar strings.
     */
    public GuitarKeyboard() {
        strings = new GuitarString[KEY_CHARS.length()];
        double freq = A4_FREQ * Math.pow(SEMITONE_RATIO, -A4_OFFSET);
        for (int i = 0; i < KEY_CHARS.length(); i++) {
            strings[i] = new GuitarString(freq);
            freq *= SEMITONE_RATIO;
        }
    }

    /**
     * Plays the designated note.
     *
     * @param c the character designating the note
     */
    public void playNote(char c) {
        int index = KEY_CHARS.indexOf(c);
        if (index == -1) {
            System.out.println("No such key: " + c);
        }
        System.out.println("Plucking " + c);
        strings[index].pluck();
    }

    /**
     * Plays one tick of active guitar strings.
     */
    public void tick() {
        // Calculate the average of all samples from
        // vibrating strings (i.e., strings whose sample is not 0).
        // If there were any vibrating strings, play this average
        // value using StdAudio.play().
        //
        // You must also call the tick() method of each
        // vibrating string.
        double sum = 0.0;
        int count = 0;
        for (int i = 0; i < strings.length; i++) {
            double sample = strings[i].sample();
            if (sample != 0) {
                sum += sample;
                count++;
            }
            strings[i].tick();
        }
        if (count > 0) {
            double avgSample = sum / count;
            StdAudio.play(avgSample);
        }
    }
}
