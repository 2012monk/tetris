package tetris.components;

import tetris.console.Console;
import tetris.window.Rectangle;
import tetris.window.Spatial;

public abstract class ComponentImpl implements Component {

    private static final int MINIMUM_SIZE = 1;
    private static final int DEFAULT_COORDINATE = 0;
    protected Spatial space;

    public ComponentImpl(Spatial space) {
        this.space = space;
    }

    public ComponentImpl(int x, int y, int width, int height) {
        this.space = new Rectangle(x, y, width, height);
    }

    protected ComponentImpl() {
        this.space = new Rectangle(
            DEFAULT_COORDINATE, DEFAULT_COORDINATE,
            MINIMUM_SIZE, MINIMUM_SIZE);
    }

    @Override
    public void setSpace(Spatial space) {
        this.space = space;
    }

    @Override
    public int getAbsoluteX() {
        return space.getAbsoluteX();
    }

    @Override
    public int getAbsoluteY() {
        return space.getAbsoluteY();
    }

    @Override
    public int getWidth() {
        return space.getWidth();
    }

    @Override
    public int getHeight() {
        return space.getHeight();
    }

    @Override
    public Spatial getParent() {
        return this.space.getParent();
    }

    @Override
    public void setParent(Spatial space) {
        this.space.setParent(space);
    }

    @Override
    public void clear() {
        Console.clearArea(space);
    }
}
