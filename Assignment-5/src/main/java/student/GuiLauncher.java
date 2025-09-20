package student;

import javax.swing.*;

/**
 * This interface encapsulates the name and ability to launch a graphical
 * user interface enabling the user to sort and select playable items.
 *
 * @param <T> the type of the items
 */
public abstract class GuiLauncher<T extends Playable> {
    /**
     * Gets the name of this launcher.
     *
     * @return the name of this launcher
     */
    public abstract String getName();

    /**
     * Launches a GUI for sorting and selecting playable items.
     */
    public abstract void launchGui();

    /**
     * Helper method for common code in MeowGuiLauncher and GifGuiLauncher
     */
    protected void guiLauncherHelper(java.util.List<T> items, java.util.List<NamedComparator<T>> comparators) {
        Display<T> display = new Display<>(items, comparators);
        SwingUtilities.invokeLater(() -> display.setVisible(true));
    }
}
