package controller;

import model.GuitarKeyboard;
import view.PianoKeyboard;

/**
 * A piano keyboard for playing guitar notes.
 */
public final class StrumBoard implements PianoKeyboard.KeyPressCallback {
    private final GuitarKeyboard guitarKeyboard;
    private final PianoKeyboard pianoKeyboard;

    private StrumBoard() {
        guitarKeyboard = new GuitarKeyboard();
        pianoKeyboard = new PianoKeyboard(this);
    }

    private void loop() {
        // This runs until the user exits the program by pressing escape.
        while (true) {
            guitarKeyboard.tick();
            pianoKeyboard.poll();
        }
    }

    @Override
    public void onKeyPress(char key) {
        guitarKeyboard.playNote(key);
    }

    /**
     * Launches a piano keyboard for playing guitar notes.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        new StrumBoard().loop();
    }
}
