package tetris.window;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import tetris.console.Console;
import tetris.constants.Char;
import tetris.constants.SpecialKeyCode;
import tetris.helper.AutoDropper;
import tetris.system.MessageBroker;

public class WindowPoolManager {

    private static final List<Window> windowPool = Collections.synchronizedList(new LinkedList<>());
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
        if (chr.is(SpecialKeyCode.KEY_ESC) || chr.is('q')) {
            shutDown();
            return;
        }
        windowPool.forEach(w -> TaskManager.addTask(() -> {
            Console.startDraw();
            w.handleKey(chr);
            Console.endDraw();
        }));
    }
}
