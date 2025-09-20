package student;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.sound.sampled.*;

/**
 * Keeps track of a dataset of Meows of audio files. Each audio file is named
 * in the format "C_NNNNN_BB_SS_OOOOO_RXX.wav" where NNNNN is a unique cat ID,
 * R is the recording session, and XX is the vocalization counter.
 */
public abstract class Dataset {
    protected List<Meow> data;
    private Random rand;

    public Dataset() {
        rand = new Random();
    }

    private void addEach(File[] files, boolean addToFront) {
        if (addToFront) {
            for (int i = files.length - 1; i >= 0; i--) {
                data.add(new Meow(files[i]));
            }
        } else {
            for (File file : files) {
                data.add(new Meow(file));
            }
        }
    }

    /**
     * Adds each file to the front of the dataset.
     * E.g., if the array contains [file1, file2], then
     * first file1 will be added to the front of the data, and
     * then file2 will be added in front of that.
     * This assumes the filenames follows the convention specified.
     *
     * @param files the array of files to be added to the dataset
     */
    public void addEachToFront(File[] files) {
        addEach(files, true);
    }

    /**
     * Adds each file to the end of the dataset.
     * E.g., if the array contains [file1, file2], then
     * first file1 will be added to the back of the data, and
     * then file2 will be added after that.
     * This assumes the filenames follows the convention specified.
     *
     * @param files the array of files to be added to the dataset
     */
    public void addEachToBack(File[] files) {
        addEach(files, false);
    }

    /**
     * Gets a random Meow from the data set.
     *
     * @return a randomly selected Meow from the dataset
     * @throws IllegalStateException if the dataset is empty
     */
    public Meow getRandomMeow() throws IllegalStateException {
        if (data.isEmpty()) {
            throw new IllegalStateException("There is no meow in the dataset.");
        }
        return data.get(rand.nextInt(data.size()));
    }

    /**
     * Sort the Meows in the dataset. Meows are primarily ordered by cat ID. If two Meows
     * have the same cat ID, they are ordered by recording session and vocalization counter.
     */
    public void sortDataset() {
        Collections.sort(data);
    }


    /**
     * Represents a Meow in the dataset of cat sounds. Keeps track of the audio file,
     * the cat ID, and the recording session plus vocal counter.
     */
    public static class Meow implements Comparable<Meow> {

        protected File audioFile;
        protected String catID;
        protected int recordingSessionVocalCounter;

        protected Meow(File file) {
            audioFile = file;
            String[] splittedFileName = file.getName().split("_");
            catID = splittedFileName[1];
            String sessionAsString = splittedFileName[splittedFileName.length - 1].replace(".wav", "");
            recordingSessionVocalCounter = Integer.parseInt(sessionAsString);
        }

        @Override
        public int compareTo(Meow other) {
            int comparison = this.catID.compareTo(other.catID);
            if (comparison != 0) {
                return comparison;
            }
            return Integer.compare(this.recordingSessionVocalCounter, other.recordingSessionVocalCounter);
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

        /**
         * Plays the audio clip of the cat sound. If the sound cannot be played, it
         * prints a message saying it could not be played.
         */
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
                System.out.printf("Could not play %s\n", audioFile);
            }
        }
    }
}