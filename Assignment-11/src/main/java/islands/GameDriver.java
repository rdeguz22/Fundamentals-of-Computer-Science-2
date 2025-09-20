package islands;

import islands.model.player.MinimaxPlayer;
import islands.model.player.RandomPlayer;
import islands.model.player.SimulatedPlayer;
import islands.model.player.student.AlphaBetaPlayer;
import islands.model.player.student.CachingMinimaxPlayer;
import islands.model.player.student.RandomMaxPlayer;
import islands.view.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Main launching point for the Islands of Hex Game.
 */
public final class GameDriver {
    private static final String CLOSE_WINDOW_ACTION = "closeWindow";

    // private to prevent instantiation
    private GameDriver() {
    }

    private static final List<Class<? extends SimulatedPlayer>> SIMULATORS = List.of(
            RandomPlayer.class,
            MinimaxPlayer.class,
            RandomMaxPlayer.class,
            CachingMinimaxPlayer.class,
            AlphaBetaPlayer.class
    );

    /**
     * Launches Islands of Hex game.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Islands of Hex");

        Game game = new Game(SIMULATORS);
        frame.add(game);
        frame.setSize(new Dimension(Game.BOARD_WIDTH, Game.BOARD_HEIGHT));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        enableExitOnEscape(frame);
    }

    // based on https://stackoverflow.com/a/26737111/631051
    private static void enableExitOnEscape(JFrame jframe) {
        JRootPane rootPane = jframe.getRootPane();
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), CLOSE_WINDOW_ACTION);
        rootPane.getActionMap().put(
                CLOSE_WINDOW_ACTION,
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jframe.dispatchEvent(
                                new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
                    }
                }
        );
    }
}
