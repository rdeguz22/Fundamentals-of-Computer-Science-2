// Students should not modify this file.
package student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A graphical user interface for sorting and selecting playable items.
 *
 * @param <T> the type of the items
 */
public class Display<T extends Playable> extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    public static final String CLOSE_WINDOW_ACTION = "closeWindow";

    private final List<T> playables;
    private final List<NamedComparator<T>> comparators;
    private final DefaultListModel<Playable> listModel;
    private final JList<Playable> jList;

    /**
     * Constructs and launches a GUI that allows the provided data to be
     * reordered and played.
     *
     * @param playables   the data items
     * @param comparators the comparators used to sort them
     */
    public Display(List<T> playables, List<NamedComparator<T>> comparators) {
        this.playables = new ArrayList<>(playables); // ensure mutability
        this.comparators = new ArrayList<>(comparators); // defensive copy
        this.listModel = new DefaultListModel<>();
        this.jList = new JList<>(listModel);

        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Meows in a GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(jList), BorderLayout.CENTER);
        enableExitOnEscape();

        // Create and add widgets.
        JPanel buttonPanel = new JPanel();
        createList(buttonPanel);
        addPlayButton(buttonPanel);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    // based on https://stackoverflow.com/a/26737111/631051
    private void enableExitOnEscape() {
        JRootPane rootPane = getRootPane();
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), CLOSE_WINDOW_ACTION);
        rootPane.getActionMap().put(
                CLOSE_WINDOW_ACTION,
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Display.this.dispatchEvent(
                                new WindowEvent(Display.this, WindowEvent.WINDOW_CLOSING));
                    }
                }
        );
    }

    private void addPlayButton(JPanel buttonPanel) {
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Playable playable = jList.getSelectedValue();
                if (playable != null) {
                    playable.play();
                } else {
                    JOptionPane.showMessageDialog(Display.this, "Please select an item to play.");
                }
            }
        });

        buttonPanel.add(playButton);
    }

    private void createList(JPanel buttonPanel) {
        updateJList();
        for (NamedComparator<T> namedComparator : comparators) {
            JButton button = new JButton(namedComparator.getName());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playables.sort(namedComparator.getComparator());
                    updateJList();
                }
            });
            buttonPanel.add(button);
        }
    }

    private void updateJList() {
        listModel.clear();
        for (Playable playable : playables) {
            listModel.addElement(playable);
        }
    }
}
