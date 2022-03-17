package tetris.window;

import tetris.console.Console;

public class Rectangle implements Spatial {

    protected Spatial parent;
    private int x;
    private int y;
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Spatial parent, int x, int y, int width, int height) {
        this(x, y, width, height);
        this.parent = parent;
    }

    public int getAbsoluteX() {
        if (parent == null) {
            return x + 1;
        }
        return getCalibratedX();
    }

    public int getAbsoluteY() {
        if (parent == null) {
            return y + 1;
        }
        return getCalibratedY();
    }

    public int getWidth() {
        return width - 2;
    }

    public int getHeight() {
        return height - 2;
    }

    public int getCalibratedX() {
        return this.parent.getAbsoluteX() + x;
    }

    public int getCalibratedY() {
        return this.parent.getAbsoluteY() + y;
    }

    @Override
    public Spatial getParent() {
        return this.parent;
    }

    @Override
    public void setParent(Spatial parent) {
        this.parent = parent;
    }

    @Override
    public void clear() {
        Console.clearArea(this);
    }
}
