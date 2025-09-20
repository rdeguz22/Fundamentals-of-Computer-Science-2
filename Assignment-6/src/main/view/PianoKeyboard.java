package view;

import edu.princeton.cs.stdlib.StdDraw;

import java.awt.event.KeyEvent;

/**
 * A display of a partial piano keyboard.
 */
public class PianoKeyboard {
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 400;

    private static final int MOUSE_DELAY = 10; // ms
    private static final int HIGHLIGHT_LENGTH = 100; // ms

    // Constants for keyboard layout
    private static final double WHITE_KEY_WIDTH = 0.08;
    private static final double WHITE_KEY_HEIGHT = 0.3;
    private static final double BLACK_KEY_WIDTH = 0.04;
    private static final double BLACK_KEY_HEIGHT = 0.2;
    private static final double TOP_ROW_Y_WHITE_CENTER = 0.7;
    private static final double TOP_ROW_Y_BLACK_CENTER = TOP_ROW_Y_WHITE_CENTER + (WHITE_KEY_HEIGHT - BLACK_KEY_HEIGHT) / 2;
    private static final double BOTTOM_ROW_Y_WHITE_CENTER = 0.3;
    private static final double BOTTOM_ROW_Y_BLACK_CENTER = BOTTOM_ROW_Y_WHITE_CENTER + (WHITE_KEY_HEIGHT - BLACK_KEY_HEIGHT) / 2;
    private static final double ROW_START_X = 0.1;
    private static final String TOP_ROW_WHITE_KEYS = "qwertyuiop[";
    private static final String TOP_ROW_BLACK_KEYS = "245789-=";
    private static final String BOTTOM_ROW_WHITE_KEYS = "zxcvbnm,./ ";
    private static final String BOTTOM_ROW_BLACK_KEYS = "dfgjk;'";
    private static final int NUM_KEYS = TOP_ROW_WHITE_KEYS.length() + TOP_ROW_BLACK_KEYS.length()
            + BOTTOM_ROW_WHITE_KEYS.length() + BOTTOM_ROW_BLACK_KEYS.length();
    private static final int[] TOP_BLACK_X_OFFSETS = {2, 1, 2, 1, 1, 2, 1, 0};
    private static final int[] BOTTOM_BACK_X_OFFSETS = {1, 1, 2, 1, 2, 1, 0};
    private static final double BLACK_KEY_OFFSET = 1.5;
    private static final int KEY_NAME_OFFSET = 3;

    private static class Key {
        private final char keyChar;
        private final double x;
        private final double y;
        private final double width;
        private final double height;
        private final boolean isBlack;

        Key(char keyChar, double x, double y, boolean isBlack) {
            this.keyChar = keyChar;
            this.x = x;
            this.y = y;
            this.isBlack = isBlack;
            this.width = isBlack ? BLACK_KEY_WIDTH : WHITE_KEY_WIDTH;
            this.height = isBlack ? BLACK_KEY_HEIGHT : WHITE_KEY_HEIGHT;
        }

        boolean contains(double mouseX, double mouseY) {
            return mouseX >= x - width / 2 && mouseX <= x + width / 2
                    && mouseY >= y - height / 2 && mouseY <= y + height / 2;

        }
    }

    private Key[] keys;
    private final KeyPressCallback callback;

    /**
     * Constructs a piano keyboard with the provided callback function.
     *
     * @param callback the function called when a key is pressed
     */
    public PianoKeyboard(KeyPressCallback callback) {
        this.callback = callback;
        initializeKeyboard();
        drawKeyboard();
    }

    private void initializeKeyboard() {
        // Set up the drawing window
        StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);

        keys = new Key[NUM_KEYS];

        // Top white row
        double x = ROW_START_X;
        int index = 0;
        for (char c : TOP_ROW_WHITE_KEYS.toCharArray()) {
            keys[index++] = new Key(c, x, TOP_ROW_Y_WHITE_CENTER, false);
            x += WHITE_KEY_WIDTH;
        }

        // Top black row
        x = ROW_START_X + .5 * WHITE_KEY_WIDTH; // initial offset
        for (int i = 0; i < TOP_ROW_BLACK_KEYS.length(); i++) {
            keys[index++] = new Key(TOP_ROW_BLACK_KEYS.charAt(i), x, TOP_ROW_Y_BLACK_CENTER, true);
            x += WHITE_KEY_WIDTH * TOP_BLACK_X_OFFSETS[i];
        }

        // Top white row
        x = ROW_START_X;
        for (char c : BOTTOM_ROW_WHITE_KEYS.toCharArray()) {
            keys[index++] = new Key(c, x, BOTTOM_ROW_Y_WHITE_CENTER, false);
            x += WHITE_KEY_WIDTH;
        }

        // Bottom black row
        x = ROW_START_X + BLACK_KEY_OFFSET * WHITE_KEY_WIDTH;
        for (int i = 0; i < BOTTOM_ROW_BLACK_KEYS.length(); i++) {
            keys[index++] = new Key(BOTTOM_ROW_BLACK_KEYS.charAt(i), x, BOTTOM_ROW_Y_BLACK_CENTER, true);
            x += WHITE_KEY_WIDTH * BOTTOM_BACK_X_OFFSETS[i];
        }
    }

    private void drawKeyboard() {
        StdDraw.clear();

        // Draw white keys first
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Key key : keys) {
            if (key == null) {
                break;
            }
            if (!key.isBlack) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledRectangle(key.x, key.y, key.width / 2, key.height / 2);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.rectangle(key.x, key.y, key.width / 2, key.height / 2);
                StdDraw.text(key.x, key.y - key.height / KEY_NAME_OFFSET, String.valueOf(key.keyChar));
            }
        }

        // Draw black keys on top
        for (Key key : keys) {
            if (key == null) {
                break;
            }
            if (key.isBlack) {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledRectangle(key.x, key.y, key.width / 2, key.height / 2);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(key.x, key.y - key.height / KEY_NAME_OFFSET, String.valueOf(key.keyChar));
            }
        }

        StdDraw.show();
    }

    private void handleKeyPress(char key, KeyPressCallback callback) {
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        for (Key k : keys) {
            if (k.keyChar == key) {
                // Highlight the pressed key
                StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                StdDraw.filledRectangle(k.x, k.y, k.width / 2, k.height / 2);
                StdDraw.show();

                // Call the callback with the pressed key
                callback.onKeyPress(key);

                // Remove highlight after short delay.
                StdDraw.pause(HIGHLIGHT_LENGTH);
                drawKeyboard();
                break;
            }
        }
    }

    /**
     * The listener interface for handling key selection.
     */
    public interface KeyPressCallback {
        /**
         * Invoked when a key has been clicked on or its name has been typed.
         *
         * @param key the name of the key
         */
        void onKeyPress(char key);
    }

    private void handleClick(double mouseX, double mouseY, KeyPressCallback callback) {
        // Check black keys first since they're on top
        for (Key key : keys) {
            if (key.isBlack && key.contains(mouseX, mouseY)) {
                handleKeyPress(key.keyChar, callback);
                return;
            }
        }

        // Then check white keys
        for (Key key : keys) {
            if (!key.isBlack && key.contains(mouseX, mouseY)) {
                handleKeyPress(key.keyChar, callback);
                return;
            }
        }
    }

    /**
     * Respond to keyboard and mouse input to this keyboard.
     */
    public void poll() {
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            handleKeyPress(key, callback);
        }

        // Check for mouse input
        if (StdDraw.isMousePressed()) {
            handleClick(StdDraw.mouseX(), StdDraw.mouseY(), callback);

            // Wait for mouse release to prevent multiple triggers
            while (StdDraw.isMousePressed()) {
                StdDraw.pause(MOUSE_DELAY);
            }
        }
    }
}