package islands.view;

import islands.model.RowColPair;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * GUI grid of the hexagon tiles
 */
public class HexGrid extends JComponent {
    int size;
    private Hexagon[][] hexagons;

    /**
     * Creates a hex grid with the specified number of cells in each dimension
     *
     * @param dimension the number of cells in each dimension
     */
    public HexGrid(int dimension) {
        setPreferredSize(new Dimension(600, 800));
        size = dimension;
        hexagons = new Hexagon[size][size];
        makeHexagons();
    }

    private void makeHexagons() {
        //1.5 and .75
        int topLeftY = (int) (Game.BOARD_HEIGHT / 2.0 - 1.5 * size * (Hexagon.hexagonLength - Hexagon.hexagonLength / 2));
        int topLeftX = (int) (Game.BOARD_WIDTH / 2.0 - 0.75 * size * (Hexagon.hexagonLength + Hexagon.hexagonLength / 3));
        //iterate the columns first since x is fixed
        //per column
        for (int col = 0; col < size; col++) {
            int x = topLeftX + col * (Hexagon.hexagonLength - 1) * 2;
            int y = topLeftY - col * (Hexagon.hexagonLength - 2);
            for (int row = 0; row < size; row++) {
                int columnY = y + row * (Hexagon.hexagonLength) * 2;

                hexagons[row][col] = new Hexagon(x, columnY, Color.LIGHT_GRAY);
            }
        }
    }

    /**
     * Sets the color for a hexagob,
     *
     * @param row   the row position
     * @param col   the column position
     * @param color the color to set
     */
    public void setColor(int row, int col, Color color) {
        if (color == Color.WHITE) {
            hexagons[row][col].setColor(Color.WHITE);
        } else
            hexagons[row][col].setColor(Color.BLACK);
        repaint();
    }

    /**
     * Return the grid position of the mouse click.
     *
     * @param y mouse y (row)
     * @param x mouse x coordinate (column)
     * @return the corresponding location of the hexagon in the board
     */
    public Optional<RowColPair> getHexDim(int y, int x) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (hexagons[row][col].getPolygon().contains(x, y)) {
                    return Optional.of(new RowColPair(row, col));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void paintComponent(Graphics g) {
        for (Hexagon[] hs : hexagons) {
            for (Hexagon h : hs) {
                h.draw(g);
            }
        }
    }
}
