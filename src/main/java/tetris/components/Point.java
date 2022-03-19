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
        if (getInnerX() < 0 || getInnerY() < 0) {
            return;
        }
        clear();
        Console.drawChar(getInnerX(), getInnerY(), CELL, DEFAULT_FOREGROUND_COLOR, color);
    }

    public Point copy() {
        return new Point(getRelativeX(), getRelativeY(), color);
    }
}
