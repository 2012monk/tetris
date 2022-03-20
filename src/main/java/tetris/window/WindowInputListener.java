package tetris.window;

import tetris.console.Console;
import tetris.constants.KeyCode;

public class WindowInputListener implements Runnable {

    private static WindowInputListener instance;
    private static Thread thread;
    private static boolean isRunning = false;

    private WindowInputListener() {
    }

    public static WindowInputListener getInstance() {
        if (instance == null) {
            instance = new WindowInputListener();
        }
        return instance;
    }

    public static void init() {
        isRunning = true;
        if (thread == null) {

            thread = new Thread(getInstance());
            thread.start();
        }
    }

    public static void shutDown() {
        isRunning = false;
        thread.interrupt();
    }

    @Override
    public void run() {
        while (isRunning) {
            int input = Console.readBytes();
            TaskManager.addTask(() -> {
                Console.clearLine(50);
                Console.drawString(50, Console.getScreenWidth() - 10, String.valueOf(input));
            });
            KeyCode key = KeyCode.getKeyCode(input);
            WindowPoolManager.notifyKey(key);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
