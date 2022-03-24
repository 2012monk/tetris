package tetris.window;

import tetris.Component;
import tetris.ComponentContainer;
import tetris.Spatial;

public class Window extends ComponentContainer<Component> {

    public Window(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Window(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
    }

    public Window(int x, int y, int width, int height, Spatial screen) {
        super(x, y, width, height);
        setParent(screen);
    }
}
