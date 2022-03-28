package tetris.ui.window;

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;
import tetris.system.ScheduledTaskHelper;
import tetris.system.TaskManager;
import tetris.ui.Spatial;
import tetris.ui.console.Console;
import tetris.ui.constants.Char;

public class WindowPoolManager {

    private static final LinkedBlockingDeque<Window> windowPool = new LinkedBlockingDeque<>();
    private static final String SCREEN_NAME = "__DEFAULT_SCREEN";
    private static Spatial screen;

    public synchronized static void init() {
        Console.initConsole();
        TaskManager.init();
        WindowInputListener.init();
    }

    public synchronized static void shutDown() {
        WindowInputListener.shutDown();
        ScheduledTaskHelper.shutDown();
        TaskManager.shutDown();
        Console.shutdown();
    }

    public static void addWindow(Window window) {
        windowPool.removeIf(w -> w.equals(window));
        windowPool.addFirst(window);
        window.setParent(getScreen());
    }

    public static Window addWindow(String name) {
        Window window = new Window(0, 0, getScreen().getInnerWidth(), getScreen().getInnerHeight(),
            false, name);
        addWindow(window);
        return window;
    }

    public static void focus(Window window) {
        if (!getFocusedWindow().equals(window)) {
            focusWindow(window);
        }
        window.onWindowFocused();
        window.render();
    }

    public static void notifyKey(Char chr) {
        if (windowPool.isEmpty()) {
            return;
        }
        getFocusedWindow().handleKey(chr);
    }

    public static Spatial getScreen() {
        if (screen == null) {
            screen = new Window(0, 0, Console.getScreenWidth(), Console.getScreenHeight(), false,
                SCREEN_NAME);
        }
        return screen;
    }

    private static void focusWindow(Window window) {
        getFocusedWindow().onWindowUnFocused();
        windowPool.removeIf(w -> w.equals(window));
        windowPool.addLast(window);
    }

    private static Window getFocusedWindow() {
        return windowPool.getLast();
    }

    public static Window getWindow(String name) {
        return windowPool.stream().filter(w -> w.getName().equals(name))
            .findAny()
            .orElseThrow(NoSuchElementException::new);
    }
}
