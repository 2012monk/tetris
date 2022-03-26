package tetris.components;

import tetris.ComponentImpl;
import tetris.console.Console;
import tetris.constants.Color;

public class PointView extends ComponentImpl {

    private static final int POINT_SIZE = 1;
    private static final char CELL = ' ';
    private final char cell;

    public PointView(int x, int y, Color color) {
        super(x, y, POINT_SIZE, POINT_SIZE, false);
        setBg(color);
        this.cell = CELL;
    }

    public PointView(int x, int y, Color fg, Color bg, char cell) {
        super(x, y, POINT_SIZE, POINT_SIZE, false);
        setFg(fg);
        setBg(bg);
        this.cell = cell;
    }

    @Override
    public void render() {
        if (!isInsideParent()) {
            return;
        }
        clear();
        Console.drawChar(getInnerX(), getInnerY(), cell, this.fg, this.bg);
    }

    @Override
    public void clear() {
        if (!isInsideParent()) {
            return;
        }
        Console.drawChar(getInnerX(), getInnerY(), CELL);
    }

    public boolean isInsideParent() {
        return isInsideSpace(getInnerX(), getInnerY());
    }

    public boolean isOverlapped(PointView point) {
        return getAbsoluteX() == point.getAbsoluteX() && getAbsoluteY() == point.getAbsoluteY();
    }

    public Color getColor() {
        return this.bg;
    }

    public char getCell() {
        return cell;
    }
}
