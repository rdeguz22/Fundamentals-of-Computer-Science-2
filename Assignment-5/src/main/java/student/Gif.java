package student;

import com.google.common.annotations.VisibleForTesting;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * An animated gif.
 */
public class Gif implements Playable {
    @VisibleForTesting
    static final NamedComparator<Gif> INCREASING_NAME_NCOMPARATOR = new NamedComparator<>(
            "sort alphabetically", (gif1, gif2) -> gif1.name.compareToIgnoreCase(gif2.name));

    @VisibleForTesting
    static final NamedComparator<Gif> DECREASING_NAME_NCOMPARATOR = new NamedComparator<>(
            "sort reverse alphabetically", (gif1, gif2) -> gif2.name.compareToIgnoreCase(gif1.name));

    @VisibleForTesting
    static final NamedComparator<Gif> INCREASING_NAME_LENGTH_NCOMPARATOR = new NamedComparator<>(
            "sort by length", (gif1, gif2) -> Integer.compare(gif1.name.length(), gif2.name.length()));

    @VisibleForTesting
    static final List<NamedComparator<Gif>> COMPARATORS = List.of(
            INCREASING_NAME_NCOMPARATOR,
            DECREASING_NAME_NCOMPARATOR,
            INCREASING_NAME_LENGTH_NCOMPARATOR
    );

    private final String name;
    private final String urlString;

    /**
     * Constructs an animated gif.
     *
     * @param name      the name to be displayed
     * @param urlString the URL of the gif
     */
    public Gif(String name, String urlString) {
        this.name = name;
        this.urlString = urlString;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void play() {
        try {
            JFrame frame = new JFrame(name);
            ImageIcon icon = new ImageIcon(new URL(urlString));
            frame.add(new JLabel(icon));
            frame.setSize(icon.getIconWidth(), icon.getIconHeight());
            frame.setVisible(true);
        } catch (MalformedURLException e) {
            System.out.printf("Unable to display %s%n", name);
        }
    }
}
