package student;

import com.google.common.annotations.VisibleForTesting;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class Poem implements Playable {
    @VisibleForTesting
    static final NamedComparator<Poem> INCREASING_TITLE_NCOMPARATOR = new NamedComparator<>(
            "sort alphabetically", (poem1, poem2) -> poem1.title.compareToIgnoreCase(poem2.title));

    @VisibleForTesting
    static final NamedComparator<Poem> DECREASING_TITLE_NCOMPARATOR = new NamedComparator<>(
            "sort reverse alphabetically", (poem1, poem2) -> poem2.title.compareToIgnoreCase(poem1.title));

    @VisibleForTesting
    static final NamedComparator<Poem> INCREASING_TITLE_LENGTH_NCOMPARATOR = new NamedComparator<>(
            "sort by length", (poem1, poem2) -> Integer.compare(poem1.title.length(), poem2.title.length()));

    @VisibleForTesting
    static final List<NamedComparator<Poem>> COMPARATORS = List.of(
            INCREASING_TITLE_NCOMPARATOR,
            DECREASING_TITLE_NCOMPARATOR,
            INCREASING_TITLE_LENGTH_NCOMPARATOR
    );

    private final String title;
    private final String urlString;
    private final String content;

    public Poem(String title, String urlString) {
        this.title = title;
        this.urlString = urlString;
        this.content = fetchContentFromURL(urlString);
    }

    private String fetchContentFromURL(String urlString) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(urlString);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            return "Failed to load poem content.";
        }
        return content.toString();
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public void play() {
        try {
            JFrame frame = new JFrame(title);
            JTextArea textArea = new JTextArea(content);
            frame.add(new JScrollPane(textArea));
            frame.setSize(textArea.getWidth(), textArea.getHeight());
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.printf("Unable to display %s%n", title);
        }
    }
}
