package tetris.window;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import tetris.components.AutoDropper;
import tetris.console.Console;
import tetris.constants.KeyCode;

public class WindowPoolManager {

    private static final List<Window> windowPool = Collections.synchronizedList(new LinkedList<>());
    private static final WindowInputListener inputListener = WindowInputListener.getInstance();
    private static final TaskManager taskManager = TaskManager.getInstance();
    private static Spatial screen;

//    static {
//        init();
//    }

    private static Spatial getScreen() {
        if (screen == null) {
            screen = new Window(0, 0, Console.getScreenWidth(), Console.getScreenHeight(), false);
        }
        return screen;
    }

    public static void refreshAll() {
        TaskManager.addTask(() -> {
            windowPool.forEach(Window::update);
            Console.refresh();
        });
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

    public synchronized static void init() {
        Console.initConsole();
        TaskManager.init();
        WindowInputListener.init();
        AutoDropper.init();
    }

    public synchronized static void shutDown() {
        WindowInputListener.shutDown();
        TaskManager.shutDown();
        AutoDropper.shutDown();
        Console.shutdown();
    }

    public static void notifyKey(KeyCode keyCode) {
        if (keyCode == KeyCode.KEY_ESC) {
            shutDown();
            return;
        }
        windowPool.forEach(w -> TaskManager.addTask(() -> w.handleKey(keyCode)));
    }
}
