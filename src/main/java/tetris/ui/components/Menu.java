package tetris.ui.components;

import java.util.NoSuchElementException;
import tetris.ui.ComponentContainer;
import tetris.ui.console.Console;
import tetris.ui.constants.Char;
import tetris.ui.constants.Color;
import tetris.ui.constants.SpecialKeyCode;

public class Menu extends ComponentContainer<MenuItem> {

    private static final Color DEFAULT_POINTER_COLOR = Color.MAGENTA;
    private static final char DEFAULT_POINTER = '>';
    private int cursor = 0;

    public Menu(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render() {
        super.render();
        printCursor();
    }

    @Override
    public void handleKey(Char chr) {
        if (chr.is(SpecialKeyCode.KEY_ENTER)) {
            activateMenu();
            return;
        }
        if (chr.is(SpecialKeyCode.KEY_UP)) {
            selectPrevious();
        }
        if (chr.is(SpecialKeyCode.KEY_DOWN)) {
            selectNext();
        }
    }

    private void activateMenu() {
        try {
            components.get(cursor).select();
        } catch (Exception ignore) {
        }
    }

    public MenuItem getItemByName(String name) {
        return components.stream().filter(m -> m.getName().equals(name))
            .findAny()
            .orElseThrow(NoSuchElementException::new);
    }

    public void addMenuItem(String name) {
        addMenuItem(name, name);
    }

    public void addMenuItem(String name, String displayName) {
        addComponent(new MenuItem(name, displayName));
        align();
    }

    private void printCursor() {
        if (components.isEmpty()) {
            return;
        }
        int x = getInnerX() + (cursor * getItemMaxHeight());
        int y = getInnerY();
        if (!isInsideSpace(x, y)) {
            return;
        }
        Console.drawChar(x, y, DEFAULT_POINTER, DEFAULT_POINTER_COLOR, bg);
    }

    private void selectNext() {
        this.cursor = (this.cursor + 1) % components.size();
        this.render();
    }

    private void selectPrevious() {
        cursor = (this.cursor - 1) % components.size();
        if (this.cursor < 0) {
            cursor = components.size() - 1;
        }
        this.render();
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
