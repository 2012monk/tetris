package tetris.components;

import tetris.console.Console;
import tetris.window.Rectangle;
import tetris.window.Spatial;

public class Point extends ComponentImpl {

    private static final Color DEFAULT_FOREGROUND_COLOR = Color.BLACK;
    private static final int POINT_SIZE = 1;
    private static final char CELL = ' ';
    private Color color;

    public Point(int x, int y, Color color) {
        setSpace(new Rectangle(x, y, POINT_SIZE, POINT_SIZE));
        this.color = color;
    }

    public Point(Spatial space, Color color) {
        setSpace(space);
        this.color = color;
    }

    private void verifyCoordinate(int p) {
        if (p < 0 || p >= space.getParent().getWidth()) {
            throw new IllegalArgumentException(String.valueOf(space.getParent().getWidth()));
        }
    }

    @Override
    public void update() {
        clear();
        Console.drawChar(getAbsoluteX(), getAbsoluteY(), CELL, DEFAULT_FOREGROUND_COLOR, color);
    }

    public Point copy() {
        return new Point(getAbsoluteX(), getAbsoluteY(), color);
    }
}
