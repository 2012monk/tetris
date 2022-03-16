package tetris.components;

import tetris.window.Window;

public abstract class ComponentImpl implements Component {

    protected Window window;

    @Override
    public void setWindow(Window window) {
        this.window = window;
    }

    protected int getCurrentX() {
        return this.window.getX() + 1;
    }

    protected int getCurrentY() {
        return this.window.getY() + 1;
    }

    protected int getMaxWidth() {
        return this.window.getWidth() - 2;
    }

    protected int getMaxHeight() {
        return this.window.getHeight() - 2;
    }
}
