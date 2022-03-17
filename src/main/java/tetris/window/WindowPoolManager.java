package tetris.window;

import java.util.LinkedList;
import tetris.console.Console;

public class WindowPoolManager {

    private static final LinkedList<Window> windowPool = new LinkedList<>();
    private static Spatial screen;

    private static Spatial getScreen() {
        if (screen == null) {
            screen = new Window(0, 0, Console.getScreenWidth(), Console.getScreenHeight(), false);
        }
        return screen;
    }

    public static void refreshAll() {
        windowPool.forEach(Window::update);
        Console.refresh();
    }

    public static void addWindow(int x, int y, int width, int height) {
        windowPool.add(new Window(x, y, width, height, getScreen()));
        refreshAll();
    }

    public static void addWindow(Window window) {
        windowPool.add(window);
        window.setParent(getScreen());
        refreshAll();
    }
}
