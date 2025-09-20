package islands.view;

import java.awt.*;

/**
 * A Single hexagon tile in the game board
 */
public class Hexagon {
    private Polygon poly;
    private Color color;

    static int hexagonLength = 25;
    private static final double THETA = (Math.PI * 2) / 6.0;

    /**
     * create a hexagon centered at x,y having color clr
     *
     * @param x   the column
     * @param y   the row
     * @param clr the color
     */
    public Hexagon(int x, int y, Color clr) {
        color = clr;
        poly = new Polygon();
        for (int i = 0; i < 6; i++) {
            int x1 = (int) (x + hexagonLength * Math.cos(THETA * i));
            int y1 = (int) (y + hexagonLength * Math.sin(THETA * i));
            poly.addPoint(x1, y1);
        }
    }

    void setColor(Color clr) {
        color = clr;
    }

    Polygon getPolygon() {
        return poly;
    }

    void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(poly);
    }
}
