package tetris.window;

import java.util.concurrent.LinkedBlockingDeque;
import tetris.console.Console;
import tetris.constants.Char;
import tetris.helper.AutoDropper;
import tetris.system.MessageBroker;
import tetris.system.TaskManager;

public class WindowPoolManager {

    private static final LinkedBlockingDeque<Window> windowPool = new LinkedBlockingDeque<>();
    private static Spatial screen;

    public static Spatial getScreen() {
        if (screen == null) {
            screen = new Window(0, 0, Console.getScreenWidth(), Console.getScreenHeight(), false);
        }
        return screen;
    }

    public static void refreshAll() {
        windowPool.forEach(w -> TaskManager.addTask(() -> {
            Console.startDraw();
            w.update();
            Console.endDraw();
        }));
//        windowPool.forEach(Window::update);
//        Console.endDraw();
    }

    public static void addWindow(int x, int y, int width, int height) {
        windowPool.push(new Window(x, y, width, height, getScreen()));
//        refreshAll();
    }

    public static void addWindow(Window window) {
        windowPool.addFirst(window);
        window.setParent(getScreen());
//        refreshAll();
    }

    public static Window addWindow() {
        Window window = new Window(0, 0, getScreen().getInnerWidth(), getScreen().getInnerHeight(),
            false);
        addWindow(window);
        return window;
    }

    public synchronized static void init() {
        Console.initConsole();
        TaskManager.init();
        MessageBroker.init();
        WindowInputListener.init();
    }

    public synchronized static void shutDown() {
        WindowInputListener.shutDown();
        MessageBroker.shutDown();
        TaskManager.shutDown();
        AutoDropper.shutDown();
        Console.shutdown();
    }

    public static void notifyKey(Char chr) {
        if (windowPool.isEmpty()) {
            return;
        }
        windowPool.getLast().handleKey(chr);
    }

    public static void unFocus(Window window) {
        if (getFocusedWindow().equals(window)) {
            windowPool.removeIf(w -> w.equals(window));
            addWindow(window);
        }
        refreshAll();
    }

    public static void focus(Window window) {
        if (!getFocusedWindow().equals(window)) {
            addFocusedWindow(window);
        }
    }

    private static void addFocusedWindow(Window window) {
        windowPool.removeIf(w -> w.equals(window));
        windowPool.addLast(window);
    }

    private static Window getFocusedWindow() {
        return windowPool.getLast();
    }
}
