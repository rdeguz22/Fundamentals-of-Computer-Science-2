package islands.model;

import java.awt.*;

/**
 * A representation of the colors of board positions and tiles in Islands of
 * Hex.
 */
public enum TileColor {
    /**
     * An empty position.
     */
    NONE("none", Color.GRAY), // color not used

    /**
     * A white tile or a position with a white tile.
     */
    WHITE("White", Color.WHITE),

    /**
     * A black tile or a position with a black tile.
     */
    BLACK("Black", Color.BLACK);

    private final String name;
    private final Color color;

    TileColor(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Gets the name of this tile color.
     *
     * @return the name ("Black", "White", or "none")
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the display color of this tile color.
     *
     * @return the display color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the one-character representation of this tile
     * (the first character of its name).
     * <ul>
     *     <li> 'B' for {@link #BLACK}</li>
     *     <li> 'W' for {@link #WHITE}</li>
     *     <li> 'n' for {@link #NONE }</li>
     * </ul>
     *
     * @return the one-character representation
     */
    public char getAbbreviation() {
        return name.charAt(0);
    }

    /**
     * Returns the opposite of this color, where {@link #BLACK} and
     * {@link #WHITE} are opposites.
     *
     * @return the opposite of this color
     * @throws IllegalArgumentException if this is {@link #NONE}
     */
    public TileColor getOpposite() {
        return switch (this) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
            case NONE -> throw new IllegalArgumentException("Cannot toggle EMPTY");
        };
    }
}
