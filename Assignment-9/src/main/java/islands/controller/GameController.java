package islands.controller;

import islands.model.*;
import islands.model.student.GameModelImplementation;
import islands.model.player.SimulatedPlayer;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * Controller for Islands of Hex game.
 */
public class GameController implements MouseListener {
    private static final TileColor START_COLOR = TileColor.WHITE;
    private static final int MS_BETWEEN_SIMULATED_MOVES = 10;

    private final GameModel model;
    private Timer timer;
    private ViewDelegate viewDelegate;
    private TileColor turn = START_COLOR;
    private boolean gameOver = false;

    private SimulatedPlayer whiteSimulatedPlayer;
    private SimulatedPlayer blackSimulatedPlayer;

    /**
     * The required interface to display the game
     */
    public interface ViewDelegate {
        /**
         * Displays whose turn it is.
         *
         * @param color the color whose turn it is
         */
        void displayTurn(TileColor color);

        /**
         * Places a tile of the specified color at a position.
         *
         * @param row       the row to play the piece into
         * @param col       the column to play the piece into
         * @param tileColor the color of the piece
         */
        void setColor(int row, int col, TileColor tileColor);

        /**
         * Listens for mouse clicks.
         *
         * @param mouseListener the mouse listener
         */
        void listen(MouseListener mouseListener);

        /**
         * Converts x and y pixel coordinates to a position on the game board.
         *
         * @param y the y-coordinate
         * @param x the x-coordinate
         * @return the corresponding position on the game board
         */
        Optional<RowColPair> getHexDim(int y, int x);

        /**
         * Displays who won the game.
         *
         * @param winner the winner
         */
        void setWinnerLabel(TileColor winner);

        /**
         * Displays each side's score.
         *
         * @param whiteScore white's score
         * @param blackScore black's score
         */
        void setScore(int whiteScore, int blackScore);
    }

    private static SimulatedPlayer constructSimulatedPlayer(
            Class<? extends SimulatedPlayer> clazz
    ) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(String.format("Error trying to call constructor %s()"));
        }
    }

    /**
     * Constructs the controller for a game.
     *
     * @param size         the size
     * @param whiteClass   the white player (or <code>null</code> for a human player)
     * @param blackClass   the black player (or <code>null</code> for a human player)
     * @param viewDelegate the view delegate
     */
    public GameController(
            int size,
            Class<? extends SimulatedPlayer> whiteClass,
            Class<? extends SimulatedPlayer> blackClass,
            ViewDelegate viewDelegate) {
        model = new GameModelImplementation(size);

        // Create simulators if requested.
        if (whiteClass != null) {
            this.whiteSimulatedPlayer = constructSimulatedPlayer(whiteClass);
        }
        if (blackClass != null) {
            this.blackSimulatedPlayer = constructSimulatedPlayer(blackClass);
        }
        if (whiteClass != null || blackClass != null && MS_BETWEEN_SIMULATED_MOVES > 0) {
            timer = new Timer(MS_BETWEEN_SIMULATED_MOVES, (e) -> runSimulatedPlayers());
            timer.start();
        }

        setDelegate(viewDelegate);
    }

    /**
     * Change the turn from white to black or vice versa.
     */
    public void toggleTurn() {
        turn = turn.getOpposite();
        viewDelegate.displayTurn(turn);
    }

    private void runSimulatedPlayers() {
        if (turn == TileColor.WHITE && whiteSimulatedPlayer != null) {
            tryRowColFromPoint(whiteSimulatedPlayer.timeAndChooseNextMove(model, turn));
        } else if (turn == TileColor.BLACK && blackSimulatedPlayer != null) {
            tryRowColFromPoint(blackSimulatedPlayer.timeAndChooseNextMove(model, turn));
        }
    }

    /**
     * Sets the view delegate.
     *
     * @param vd the view delegate
     */
    public void setDelegate(ViewDelegate vd) {
        viewDelegate = vd;
        vd.displayTurn(START_COLOR);
        vd.listen(this);
    }

    /**
     * Tries to play a tile of the current color at the specified position.
     *
     * <p>If the placement is successful, the score is updated, and the end of
     * game condition is checked. If the game is over, the winner is announced;
     * otherwise, the turn is toggled.</p>
     *
     * <p>If the position is not legal, this has no effect.</p>
     *
     * @param position the requested position
     */
    public void tryRowColFromPoint(RowColPair position) {
        int col = position.column();
        int row = position.row();
        tryRowCol(row, col);
    }

    /**
     * Tries to play a tile of the current color at the specified position.
     *
     * <p>If the placement is successful, the score is updated, and the end of
     * game condition is checked. If the game is over, the winner is announced;
     * otherwise, the turn is toggled.</p>
     *
     * <p>If the position is not legal, this has no effect.</p>
     *
     * @param row the requested row
     * @param col the requested column
     */
    public void tryRowCol(int row, int col) {
        if (model.canPlay(row, col)) {
            viewDelegate.setColor(row, col, turn);
            model.makePlay(row, col, turn);
            if (model.isGameOver()) {
                stopAll();
                gameOver = true;
                viewDelegate.setWinnerLabel(getWinner());
            } else {
                toggleTurn();
            }
            viewDelegate.setScore(model.getScore(TileColor.WHITE), model.getScore(TileColor.BLACK));
        }
    }

    // The caller is responsible for ensuring that the game has ended.
    private TileColor getWinner() {
        int whiteScore = model.getScore(TileColor.WHITE);
        int blackScore = model.getScore(TileColor.BLACK);
        return switch (Integer.signum(whiteScore - blackScore)) {
            case -1 -> TileColor.BLACK;
            case 1 -> TileColor.WHITE;
            default -> TileColor.NONE;  // tie
        };
    }

    /**
     * Stops the timer used to slow down simulated moves.
     */
    public void stopAll() {
        if (timer != null) {
            timer.stop();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Ignore clicks for simulated moves
        if (turn == TileColor.WHITE && whiteSimulatedPlayer != null
                || turn == TileColor.BLACK && blackSimulatedPlayer != null) {
            return;
        }
        if (!gameOver) {
            Optional<RowColPair> point = viewDelegate.getHexDim(e.getY(), e.getX());
            point.ifPresent(this::tryRowColFromPoint);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
