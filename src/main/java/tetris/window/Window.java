package tetris.window;

import tetris.Component;
import tetris.ComponentContainer;
import tetris.Spatial;

public class Window extends ComponentContainer<Component> {

    private final String name;


    public Window(int x, int y, int width, int height, boolean borderOn, String name) {
        super(x, y, width, height, borderOn);
        this.name = name;
    }

    public Window(int x, int y, int width, int height, Spatial screen, String name) {
        super(x, y, width, height);
        setParent(screen);
        this.name = name;
    }

    public Window(String name) {
        super(0, 0, 0, 0, false);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
