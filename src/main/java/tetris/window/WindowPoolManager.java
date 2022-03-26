package tetris.window;

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;
import tetris.Spatial;
import tetris.console.Console;
import tetris.constants.Char;
import tetris.system.MessageBroker;
import tetris.system.TaskManager;

public class WindowPoolManager {

    private static final LinkedBlockingDeque<Window> windowPool = new LinkedBlockingDeque<>();
    private static final String SCREEN_NAME = "__DEFAULT_SCREEN";
    private static Spatial screen;

    public synchronized static void init() {
        Console.initConsole();
        TaskManager.init();
        MessageBroker.init();
        WindowInputListener.init();
    }

    public synchronized static void shutDown() {
        WindowInputListener.shutDown();
        TaskManager.shutDown();
        MessageBroker.shutDown();
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
        TaskManager.addTask(() -> getFocusedWindow().handleKey(chr));
    }

    public static Spatial getScreen() {
        if (screen == null) {
            screen = new Window(0, 0, Console.getScreenWidth(), Console.getScreenHeight(), false,
                SCREEN_NAME);
        }
        return screen;
    }

    public static void refreshAll() {
        windowPool.forEach(w -> TaskManager.addTask(w::render));
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
