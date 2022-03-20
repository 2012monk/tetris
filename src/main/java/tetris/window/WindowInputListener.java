package tetris.window;

import tetris.console.Console;
import tetris.constants.Char;

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

    private void keyLog(int key) {
        TaskManager.addTask(() -> {
            Console.clearLine(50);
            Console.clearLine(51);
            Console.drawString(50, Console.getScreenWidth() - 10, String.valueOf((char) key));
            Console.drawString(51, Console.getScreenWidth() - 10, String.valueOf(key));
        });
    }

    @Override
    public void run() {
        while (isRunning) {
            int input = Console.readBytes();
            Char chr = new Char(input);
            keyLog(input);
            WindowPoolManager.notifyKey(chr);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
