package tetris.components;

import tetris.console.Console;
import tetris.constants.Color;

public class Point extends ComponentImpl {

    private static final Color DEFAULT_FOREGROUND_COLOR = Color.BLACK;
    private static final int POINT_SIZE = 1;
    private static final char CELL = ' ';
    private final Color color;

    public Point(int x, int y, Color color) {
        super(x, y, POINT_SIZE, POINT_SIZE, false);
        this.color = color;
    }

    @Override
    public void update() {
        if (!isInsideParent()) {
            return;
        }
        clear();
        Console.drawChar(getInnerX(), getInnerY(), CELL, DEFAULT_FOREGROUND_COLOR, color);
    }

    @Override
    public void clear() {
        if (!isInsideParent()) {
            return;
        }
        super.clear();
    }

    public boolean isInsideParent() {
        return isInsideSpace(getInnerX(), getInnerY());
    }

    public Point copy() {
        return new Point(getRelativeX(), getRelativeY(), color);
    }

    public boolean isOverlapped(Point point) {
        return getAbsoluteX() == point.getAbsoluteX() && getAbsoluteY() == point.getAbsoluteY();
    }
}
