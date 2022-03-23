package tetris.components;

import tetris.console.Console;
import tetris.window.Task;

public class MenuItem extends ComponentImpl {

    private final String name;
    private final Task task;

    public MenuItem(String name, Task task) {
        this.name = name;
        this.task = task;
    }

    public String getName() {
        return name;
    }

    public void resize(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void update() {
        if (!hasParent()) {
            return;
        }
        clear();
        Console.drawString(getInnerX(), (getInnerWidth() - name.length()) / 2 + getInnerY(), name);
    }

    public void action() {
        task.action();
    }
}
