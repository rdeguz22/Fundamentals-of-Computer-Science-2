package student;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Random;


/**
 * Keeps track of the functionality of a display on which a user can draw falling sand (and other
 * particles).
 */
public class SandLab {

    /**
     * Creates and runs a falling sand display with {@link #NUM_ROWS} rows and {@link #NUM_COLS} columns.
     */
    public static void main(String[] args) {
        SandDisplay display = new SandDisplay("Falling Sand", NUM_ROWS, NUM_COLS);
        SandLab lab = new SandLab(display);
        lab.run();
    }

    protected final ISandDisplay display;

    /**
     * The number of rows of the display to create.
     */
    public static final int NUM_ROWS = 120;

    /**
     * The number of columns of the display to create.
     */
    public static final int NUM_COLS = 80;
    private final Random rand;

    /**
     * Constructs a new SandLab display.
     *
     * @param display the GUI
     */
    public SandLab(ISandDisplay display) {
        this.display = display;
        rand = new Random();
    }

    /**
     * Updates the value in the grid to reflect the user painting that pixel using the given tool.
     *
     * @param row  row where the user clicked
     * @param col  column where the user clicked
     * @param tool Particle tool with which the user is painting. If the user is erasing, the tool is
     *             null.
     */
    protected void locationClicked(int row, int col, Particle tool) {
        display.setValue(row, col, tool);
    }

    /**
     * Updates the display to show the right color in each pixel in the grid
     */
    public void updateDisplay() {
        for (int i = 0; i < NUM_COLS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                if (display.getValue(j, i) == Particle.METAL) {
                    display.setColor(j, i, java.awt.Color.GRAY);
                } else if (display.getValue(j, i) == Particle.WATER) {
                    display.setColor(j, i, java.awt.Color.CYAN);
                } else if (display.getValue(j, i) == Particle.SAND) {
                    display.setColor(j, i, java.awt.Color.YELLOW);
                } else {
                    display.setColor(j, i, java.awt.Color.BLACK);
                }
            }
        }
    }

    /**
     * Chooses a random spot on the grid, and if it contains a
     * particle, then moves that particle in a manner appropriate to the particle type.
     */
    public void step() {
        Random random = new Random();
        int x = random.nextInt(NUM_ROWS - 1);
        int y = random.nextInt(NUM_COLS - 1);
        if (display.getValue(x, y) == Particle.SAND) {
            if (display.getValue(x + 1, y) == null) {
                display.setValue(x + 1, y, Particle.SAND);
                display.setValue(x, y, null);
            } else if (display.getValue(x + 1, y) == Particle.WATER) {
                display.setValue(x + 1, y, Particle.SAND);
                display.setValue(x, y, Particle.WATER);
            }
        } else if (display.getValue(x, y) == Particle.WATER) {
            List<Integer> directionsY = List.of(0, 1, -1);
            List<Integer> directionsX = List.of(1, 1, 1);
            int direction = random.nextInt(directionsX.size());
            int directionX = directionsX.get(direction);
            int directionY = directionsY.get(direction);
            int newX = x + directionX;
            int newY = y + directionY;
            if (display.getValue(newX, newY) == null) {
                display.setValue(newX, newY, Particle.WATER);
                display.setValue(x, y, null);
            }
        }
    }

    /**
     * Keeps the program running (updating the display based on mouseclicks) until the user quits the
     * program.
     */
    public void run() {
        while (true) {
            for (int i = 0; i < display.getSpeed(); i++) {
                step();
            }
            updateDisplay();
            display.repaint();
            display.pause(1);  //wait for redrawing and for mouse
            int[] mouseLoc = display.getMouseLocation();
            if (mouseLoc != null) { //test if mouse clicked
                locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
            }
        }
    }
}