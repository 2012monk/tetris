package tetris.gameobject;

import tetris.ui.constants.Color;

public class Cell implements Point {

    private static final char DEFAULT_SPACE = ' ';
    private final Color color;
    private final char space;
    private int x;
    private int y;

    public Cell(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.space = DEFAULT_SPACE;
    }

    public Cell(int x, int y, Color color, char space) {
        this.color = color;
        this.space = space;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void setCartesianZero(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public Color getColor() {
        return color;
    }

    public char getSpace() {
        return space;
    }

    public boolean isOverLapped(Cell cell) {
        return this.x == cell.getX() && this.y == cell.getY();
    }

    public boolean isOverlapped(Cell cell) {
        return cell.getX() == getX() && cell.getY() == getY();
    }
}
