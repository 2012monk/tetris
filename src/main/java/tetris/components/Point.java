package tetris.components;

import tetris.console.Console;

public class Point extends ComponentImpl {

    private static final Color DEFAULT_FOREGROUND_COLOR = Color.BLACK;
    private static final char CELL = ' ';
    private int x;
    private int y;
    private Color color;

    public Point(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public void update() {
        int calibratedX = x + getCurrentX();
        int calibratedY = y + getCurrentY();
        Console.drawChar(calibratedX, calibratedY, CELL, DEFAULT_FOREGROUND_COLOR, color);
    }
}
