package islands.model;

/**
 * The game board and associated logic.
 */
public interface GameModel {
    /**
     * Gets the size of this board (the number of rows or the number of columns).
     *
     * @return the width or height of this board
     */
    int getSize();

    /**
     * Gets the color of the tile at the specified row and column.
     *
     * @param row the row
     * @param col the column
     * @return the color ({@link TileColor#BLACK}, {@link TileColor#WHITE},
     * or {@link TileColor#NONE})
     */
    TileColor getTileColor(int row, int col);

    /**
     * Checks whether a play can be made at the specified row and column,
     * where (0, 0) is the top left cell of the board.
     *
     * @param row the row
     * @param col the column
     * @return true if the position is empty
     * @throws IllegalArgumentException if the position is out of bounds
     */
    boolean canPlay(int row, int col);

    /**
     * Plays a piece at the specified row and column if that space is in
     * bounds and unoccupied and the specified color is either
     * {@link TileColor#BLACK} or {@link TileColor#WHITE}.
     *
     * @param row       the row
     * @param col       the column
     * @param tileColor the color
     * @throws IllegalArgumentException if the position is occupied or out of
     *                                  bounds or if the color is
     *                                  {@link TileColor#NONE}
     */
    void makePlay(int row, int col, TileColor tileColor);

    /**
     * Returns the score for the specified color.
     *
     * @param tileColor the color
     * @return the score for the specified color
     */
    int getScore(TileColor tileColor);

    /**
     * Checks whether the game is over. There are two ways the game can end:
     * <ol>
     *     <li>
     *         There is a {@link TileColor#BLACK} path between the leftmost and
     *         rightmost columns.
     *     </li>
     *     <li>
     *         There is a {@link TileColor#WHITE} path between the topmost and
     *         bottommost rows.
     *     </li>
     * </ol>
     *
     * @return true if the game is over, false otherwise
     */
    boolean isGameOver();

    /**
     * Gets a string representation of this model showing the colors at each
     * board position. Specifically, each position is represented by a single
     * character indicating the color of the tile ("B" for black, "W" for
     * white, and "n" for none). A newline character follows each row.
     * <p>
     * For example, a 2x2 board with a white tile in the upper-left corner
     * and a black tile in the upper-right corner, would be represented as
     * "WB\nn\n".
     *
     * @return a string representation of the board position
     */
    String getBoardString();

    /**
     * Makes a fully independent "deep" copy of this model sharing none of its
     * data.
     *
     * @return the deep copy of this model
     */
    GameModel deepCopy();
}
