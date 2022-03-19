package tetris.window;

import tetris.components.Component;
import tetris.components.ComponentContainer;

public class Window extends ComponentContainer<Component> {

    public Window(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Window(int width, int height) {
        this(0, 0, width, height);
    }

    public Window(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
    }

    public Window(int x, int y, int width, int height, Spatial screen) {
        super(x, y, width, height);
        setParent(screen);
    }
//
//    public void addComponent(Component component) {
//        components.add(component);
//        component.setParent(this);
//        refreshWindow();
//    }
//
//    public void refreshWindow() {
//        if (borderOn) {
//            Console.drawBorder(this);
//        }
//        Console.clearArea(this);
//        components.forEach(Component::update);
//    }

}
