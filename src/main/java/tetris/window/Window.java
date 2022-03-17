package tetris.window;

import java.util.ArrayList;
import java.util.List;
import tetris.components.Component;
import tetris.console.Console;

public class Window implements Spatial {

    private Spatial space;

    private boolean activateBorder = true;

    private List<Component> components = new ArrayList<>();

    public Window(int x, int y, int width, int height) {
        this.space = new Rectangle(x, y, width, height);
    }

    public Window(int width, int height) {
        this(0, 0, width, height);
    }

    public Window(int x, int y, int width, int height, Spatial screen) {
        this.space = new Rectangle(screen, x, y, width, height);
    }

    public void addComponent(Component component) {
        components.add(component);
        component.setParent(space);
        refreshWindow();
    }

    public void refreshWindow() {
        if (activateBorder) {
            Console.drawBorder(this.space);
        }
        Console.clearArea(this.space);
        components.forEach(Component::update);
    }

    @Override
    public int getAbsoluteX() {
        return this.space.getAbsoluteX();
    }

    @Override
    public int getAbsoluteY() {
        return this.space.getAbsoluteY();
    }

    @Override
    public int getWidth() {
        return this.space.getWidth();
    }

    @Override
    public int getHeight() {
        return this.space.getHeight();
    }

    @Override
    public Spatial getParent() {
        return this.space.getParent();
    }

    @Override
    public void setParent(Spatial parent) {
    }

    public void clear() {
        Console.clearArea(this.space);
    }
}
