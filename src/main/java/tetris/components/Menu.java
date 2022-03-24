package tetris.components;

import java.util.NoSuchElementException;
import tetris.ComponentContainer;
import tetris.Task;
import tetris.console.Console;
import tetris.constants.Char;
import tetris.constants.Color;
import tetris.constants.SpecialKeyCode;

public class Menu extends ComponentContainer<MenuItem> {

    private static final Color DEFAULT_POINTER_COLOR = Color.MAGENTA;
    private static final char DEFAULT_POINTER = '>';
    private int selected = 0;

    public Menu(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {
        super.update();
        printCursor();
    }

    @Override
    public void handleKey(Char chr) {
        if (chr.is(SpecialKeyCode.KEY_ENTER)) {
            components.get(selected).action();
            return;
        }
        if (chr.is(SpecialKeyCode.KEY_UP)) {
            selectPrevious();
        }
        if (chr.is(SpecialKeyCode.KEY_DOWN)) {
            selectNext();
        }
    }

    public MenuItem getItemByName(String name) {
        return components.stream().filter(m -> m.getName().equals(name))
            .findAny()
            .orElseThrow(NoSuchElementException::new);
    }

    public void addMenuItem(String name, Task task) {
        addComponent(new MenuItem(name, task));
        align();
    }

    private void printCursor() {
        if (components.isEmpty()) {
            return;
        }
        int x = getInnerX() + (selected * getItemMaxHeight());
        int y = getInnerY();
        if (!isInsideSpace(x, y)) {
            return;
        }
        Console.drawChar(x, y, DEFAULT_POINTER, DEFAULT_POINTER_COLOR, bg);
    }

    private void selectNext() {
        this.selected = (this.selected + 1) % components.size();
        update();
    }

    private void selectPrevious() {
        selected = (this.selected - 1) % components.size();
        if (this.selected < 0) {
            selected = components.size() - 1;
        }
        update();
    }

    private void align() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i)
                .resize(getNextX(i), getYAlign(), getItemMaxWidth(), getItemMaxHeight());
        }
    }

    private int getNextX(int i) {
        return getItemMaxHeight() * i;
    }

    private int getYAlign() {
        return 1;
    }

    private int getItemMaxHeight() {
        return getInnerHeight() / this.components.size();
    }

    private int getItemMaxWidth() {
        return getInnerWidth() - 2;
    }
}
