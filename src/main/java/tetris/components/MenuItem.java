package tetris.components;

import java.util.HashSet;
import java.util.Set;
import tetris.ComponentImpl;
import tetris.console.Console;
import tetris.message.MenuSelectedMessage;

public class MenuItem extends ComponentImpl {

    private static final Set<String> menuItemPool = new HashSet<>();
    private static final String ERR_INVALID_NAME = "이름이 이미 존재합니다";
    private final String name;
    private final String displayName;

    public MenuItem(String menuName, String displayName) {
        verifyUniqueName(menuName);
        this.displayName = displayName;
        this.name = menuName;
        menuItemPool.add(menuName);
    }

    public void select() {
        publishMessage(new MenuSelectedMessage(getName()));
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void resize(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private void verifyUniqueName(String name) {
        if (menuItemPool.contains(name)) {
            throw new IllegalArgumentException(ERR_INVALID_NAME + "[" + name + "]");
        }
    }

    @Override
    public void render() {
        if (!hasParent()) {
            return;
        }
        clear();
        Console.drawString(getInnerX(), (getInnerWidth() - displayName.length()) / 2 + getInnerY(),
            displayName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuItem item = (MenuItem) o;

        return name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
