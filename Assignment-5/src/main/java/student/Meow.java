package student;

import com.google.common.annotations.VisibleForTesting;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Represents a cat vocalization.
 */
public class Meow implements Playable {
    @VisibleForTesting
    static final NamedComparator<Meow> INCREASING_CAT_ID_NCOMPARATOR =
            new NamedComparator<>("Sort by increasing cat ID",
                    (meow1, meow2) -> meow1.catID.compareTo(meow2.catID));

    @VisibleForTesting
    static final NamedComparator<Meow> INCREASING_RECORDING_ID_NCOMPARATOR =
            new NamedComparator<>("Sort by increasing recording ID",
                    (meow1, meow2) -> meow1.recordingSessionVocalCounter - meow2.recordingSessionVocalCounter);

    @VisibleForTesting
    static final NamedComparator<Meow> DECREASING_CAT_ID_NCOMPARATOR =
            new NamedComparator<>("Sort by decreasing cat ID", (meow1, meow2) -> meow2.catID.compareTo(meow1.catID));

    @VisibleForTesting
    static final List<NamedComparator<Meow>> COMPARATORS = List.of(
            INCREASING_CAT_ID_NCOMPARATOR,
            INCREASING_RECORDING_ID_NCOMPARATOR,
            DECREASING_CAT_ID_NCOMPARATOR
    );

    private final File audioFile;
    private final String catID;
    private final int recordingSessionVocalCounter;

    /**
     * Constructs a meow from the specified file
     *
     * @param file the file
     */
    public Meow(File file) {
        audioFile = file;
        String[] splitFilename = file.getName().split("_");
        catID = splitFilename[1];
        String sessionAsString = splitFilename[splitFilename.length - 1].replace(".wav", "");
        recordingSessionVocalCounter = Integer.parseInt(sessionAsString);
    }

    @VisibleForTesting
    public Meow(String catID, int recordingSessionVocalCounter) {
        this.catID = catID;
        this.recordingSessionVocalCounter = recordingSessionVocalCounter;
        this.audioFile = null;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Meow meow) {
            return audioFile.equals(meow.audioFile);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return audioFile.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Meow ID: %s, Vocal Count: %d", catID, recordingSessionVocalCounter);
    }

    /**
     * Plays the audio clip of the cat sound. If the sound cannot be played, it
     * prints a message saying it could not be played.
     */
    @Override
    public void play() {
        AudioInputStream stream;
        AudioFormat format;
        DataLine.Info info;
        Clip clip;

        try {
            stream = AudioSystem.getAudioInputStream(audioFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.out.printf("Could not play %s%n", audioFile);
        }
    }
}
