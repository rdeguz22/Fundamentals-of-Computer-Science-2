package student;

import java.util.List;

public class PoemGuiLauncher extends GuiLauncher<Poem> {
    private static final List<Poem> POEMS = List.of(
            new Poem("Sonnet 18 by William Shakespeare", "https://www.poetryfoundation.org/poems/45087/sonnet-18-shall-i-compare-thee-to-a-summers-day"),
            new Poem("She Walks in Beauty by Lord Byron", "https://www.poetryfoundation.org/poems/43844/she-walks-in-beauty"),
            new Poem("The Road Not Taken by Robert Frost", "https://www.poetryfoundation.org/poems/44272/the-road-not-taken"),
            new Poem("Dreams by Langston Hughes", "https://www.poetryfoundation.org/poems/150995/dreams-5d767850da976"));

    /**
     * Constructs the launcher.
     */
    public PoemGuiLauncher() {
    }

    @Override
    public String getName() {
        return "Poem Collection";
    }

    @Override
    public void launchGui() {
        guiLauncherHelper(POEMS, Poem.COMPARATORS);
    }
}
