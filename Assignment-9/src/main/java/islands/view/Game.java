package islands.view;

import islands.controller.GameController;
import islands.model.RowColPair;
import islands.model.player.SimulatedPlayer;
import islands.model.TileColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The GUI components of the game, including the grid and buttons.
 */
public class Game extends JPanel implements GameController.ViewDelegate {
    /**
     * Window height
     */
    public static final int BOARD_WIDTH = 800;

    /**
     * Window width
     */
    public static final int BOARD_HEIGHT = 800;

    /**
     * Default number of cells in each dimension
     */
    public static final int DEFAULT_GRID_DIMENSION = 5;

    private static final String HUMAN_DESIGNATION = "Human";
    private static final int[] SIZES = {2, 3, 5, 11, 25};

    // per game settings
    private int gridDimension;

    private final Map<String, Class<? extends SimulatedPlayer>> simulatorMap = new HashMap<>();
    private HexGrid hexGrid;
    private GameController gc;

    // widgets
    private JComboBox<String> simulateWhiteBox;
    private JComboBox<String> simulateBlackBox;
    private JLabel turnLabel;
    private JLabel winnerLabel;
    private JLabel whiteScoreLabel;
    private JLabel blackScoreLabel;

    /**
     * Creates the GUI, including the initial game grid, labels, and buttons.
     *
     * @param simulators the simulated player options
     */
    public Game(java.util.List<Class<? extends SimulatedPlayer>> simulators) {
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        gridDimension = DEFAULT_GRID_DIMENSION;
        initializeSimulatorMap(simulators);
        makeWidgets();

        // Initial game is human vs. human.
        initGame(null, null);
    }

    private void initializeSimulatorMap(java.util.List<Class<? extends SimulatedPlayer>> simulators) {
        for (Class<? extends SimulatedPlayer> clazz : simulators) {
            try {
                Constructor<? extends SimulatedPlayer> constructor = clazz.getConstructor();
                SimulatedPlayer sim = constructor.newInstance();
                simulatorMap.put(sim.getName(), clazz);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(String.format("Unable to find constructor %s(G)", clazz.getSimpleName()));
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(String.format("Problem calling constructor %s()", clazz.getSimpleName()));
            }
        }
    }

    private void makeWidgets() {
        // Upper panel
        JPanel northPan = new JPanel();
        whiteScoreLabel = new JLabel("White: 0");
        blackScoreLabel = new JLabel("Black: 0");
        northPan.add(whiteScoreLabel);
        northPan.add(blackScoreLabel);
        turnLabel = new JLabel("");
        northPan.add(turnLabel);
        winnerLabel = new JLabel("");
        northPan.add(winnerLabel);
        add(northPan, BorderLayout.NORTH);

        // Lower panel
        JPanel southPan = new JPanel();

        // Size selection
        JLabel sizeLabel = new JLabel("Board Size: ");
        southPan.add(sizeLabel);
        ButtonGroup group = new ButtonGroup();
        for (int size : SIZES) {
            JRadioButton sizeButton = new JRadioButton(Integer.toString(size));
            if (size == DEFAULT_GRID_DIMENSION) {
                sizeButton.setSelected(true);
            }
            sizeButton.addActionListener(v -> resetGridDimension(size));
            group.add(sizeButton);
            southPan.add(sizeButton);
        }

        // Lower panel simulator choices
        String[] simulatorChoices = new String[simulatorMap.size() + 1];
        simulatorChoices[0] = HUMAN_DESIGNATION;
        int i = 1;
        for (String name : simulatorMap.keySet()) {
            simulatorChoices[i++] = name;
        }
        JLabel simulateWhiteLabel = new JLabel("White: ");
        simulateWhiteBox = new JComboBox<>(simulatorChoices);
        JLabel simulateBlackLabel = new JLabel("Black: ");
        simulateBlackBox = new JComboBox<>(simulatorChoices);

        // Lower panel button
        JButton startGame = new JButton("new game");
        startGame.addActionListener(v -> createNewGame());

        // Lower panel placement
        southPan.add(simulateWhiteLabel);
        southPan.add(simulateWhiteBox);
        southPan.add(simulateBlackLabel);
        southPan.add(simulateBlackBox);
        southPan.add(startGame);
        add(southPan, BorderLayout.SOUTH);
    }

    private void resetLabel() {
        turnLabel.setText("Turn: White");
        winnerLabel.setText("");
    }

    private void createNewGame() {
        initGame(simulateWhiteBox.getSelectedItem().toString(), simulateBlackBox.getSelectedItem().toString());
    }

    private int getHexLength(int dimension) {
        if (dimension > 20) {
            return 10;
        } else if (dimension > 10) {
            return 20;
        } else if (dimension > 6) {
            return 25;
        } else {
            return 35;
        }
    }

    private void initGame(String whiteChoice, String blackChoice) {
        //  Redraw grid.
        Hexagon.hexagonLength = getHexLength(gridDimension);
        if (hexGrid != null) {
            remove(hexGrid);
            repaint(); // remove
        }
        hexGrid = new HexGrid(gridDimension);
        add(hexGrid, BorderLayout.CENTER);
        revalidate();

        // Stop previous game controller.
        if (gc != null) {
            gc.stopAll();
        }

        // Get simulator choices.
        Class<? extends SimulatedPlayer> whiteClass = simulatorMap.get(whiteChoice);
        Class<? extends SimulatedPlayer> blackClass = simulatorMap.get(blackChoice);

        // Create new game controller.
        gc = new GameController(gridDimension, whiteClass, blackClass, this);

        // Reset labels and score.
        resetLabel();
        setScore(0, 0); // Reset score when starting game.
    }

    private void resetGridDimension(int dim) {
        gridDimension = dim;
    }

    @Override
    public void paintComponent(Graphics g) {
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        g.setColor(new Color(0x33cccc));
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
    }

    @Override
    public void displayTurn(TileColor color) {
        turnLabel.setText("Turn: " + color.getName());
    }

    @Override
    public void setColor(int row, int col, TileColor tileColor) {
        hexGrid.setColor(row, col, tileColor.getColor());
    }

    @Override
    public void listen(MouseListener mouseListener) {
        hexGrid.addMouseListener(mouseListener);
    }

    @Override
    public Optional<RowColPair> getHexDim(int y, int x) {
        return hexGrid.getHexDim(y, x);
    }

    @Override
    public void setWinnerLabel(TileColor tileColor) {
        turnLabel.setText("Game Over");
        if (tileColor == TileColor.NONE) {
            winnerLabel.setText("TIE");
        } else {
            winnerLabel.setText(tileColor.getName() + " wins");
        }
        repaint();
    }

    /**
     * Displays the score.
     *
     * @param whiteScore the white score
     * @param blackScore the black score
     */
    public void setScore(int whiteScore, int blackScore) {
        whiteScoreLabel.setText("White: " + whiteScore);
        blackScoreLabel.setText("Black: " + blackScore);
    }
}
