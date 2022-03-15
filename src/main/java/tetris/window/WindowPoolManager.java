package tetris.window;

import java.util.LinkedList;
import tetris.console.Console;

public class WindowPoolManager {

    private static final LinkedList<Window> windowPool = new LinkedList<>();

    public static void refreshAll() {
        windowPool.forEach(w -> w.paint());
        Console.refresh();
    }

    public static void addWindow(Window window) {
        windowPool.add(window);
        refreshAll();
    }
}
