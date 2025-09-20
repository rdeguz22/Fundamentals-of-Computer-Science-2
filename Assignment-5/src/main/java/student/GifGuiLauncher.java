package student;

import java.util.List;

public class GifGuiLauncher extends GuiLauncher<Gif> {
    private static final List<Gif> GIFS = List.of(
            new Gif(
                    "Computer Science Security GIF by Sandia National Labs",
                    "https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExMm1lNXRwNHJ1dzUxbTV6Mmc1anpncHh6Z3BoMDJ0Mmd3ZGdyemE1OCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/RDZo7znAdn2u7sAcWH/giphy.gif"),
            new Gif("Work From Home GIF by SalesForce",
                    "hhttps://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExcDhjZnA1eWpxZzlxM251NTJ2MHRvNmxhMmFldnYydTk1aTU1YWh4YiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/1GEATImIxEXVR79Dhk/giphy.gif"),
            new Gif("Looney Tunes GIF by Looney Tunes World of Mayhem",
                    "hhttps://media2.giphy.com/media/v1.Y2lkPTc5MGI3NjExcHdnYWI3bXM2cnB1MTRubjBydXZodXM1YmR3ZTdtYmRwYnNpZTduZSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/RbDKaczqWovIugyJmW/giphy.gif"),
            new Gif("Coding Computer Science GIF by XRay.Tech", "https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExNzhwb2R3cWZ3YXpjbG8wODJuc2s0cDF6ZnQzZmN1enJ1ejhnbHBueiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/QNFhOolVeCzPQ2Mx85/giphy.gif")
    );

    public GifGuiLauncher() {
    }

    @Override
    public String getName() {
        return "GIF Collection";
    }

    @Override
    public void launchGui() {
        guiLauncherHelper(GIFS, Gif.COMPARATORS);
    }

}
