package tetris.window;

import java.util.ArrayList;
import java.util.List;
import tetris.components.Component;
import tetris.console.Console;

public class Window {

    private Rectangle border;

    private boolean activateBorder = true;

    private List<Component> components = new ArrayList<>();

    public Window(int x, int y, int width, int height) {
        this.border = new Rectangle(x, y, width, height);
    }

    public Window(Rectangle rectangle) {
        this.border = rectangle;
    }

    public Window(int width, int height) {
        this.border = new Rectangle(width, height);
    }

    public void addComponent(Component component) {
        components.add(component);
        component.setWindow(this);
        refreshWindow();
    }

    public void refreshWindow() {
        if (activateBorder) {
            Console.drawBorder(this.border);
        }
        Console.clearArea(this.border);
        components.forEach(Component::update);
    }

    public int getY() {
        return this.border.getY();
    }

    public int getX() {
        return this.border.getX();
    }

    public int getHeight() {
        return this.border.getHeight();
    }

    public int getWidth() {
        return this.border.getWidth();
    }
}
