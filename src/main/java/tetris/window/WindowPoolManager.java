package tetris.window;

import java.util.LinkedList;
import tetris.console.Console;

public class WindowPoolManager {

    private static final LinkedList<Window> windowPool = new LinkedList<>();
    private static Spatial screen;

    static {
        screen = new Rectangle(0, 0, Console.getScreenWidth(), Console.getScreenHeight());
    }

    public static void refreshAll() {
        windowPool.forEach(Window::refreshWindow);
        Console.refresh();
    }

    public static void addWindow(int x, int y, int width, int height) {
        windowPool.add(new Window(x, y, width, height, screen));
        refreshAll();
    }

    public static void addWindow(Window window) {
        windowPool.add(window);
        window.setParent(screen);
        refreshAll();
    }
}
