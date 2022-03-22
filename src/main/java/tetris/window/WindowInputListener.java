package tetris.window;

import java.util.Timer;
import java.util.TimerTask;
import tetris.console.Console;
import tetris.constants.Char;
import tetris.system.TaskManager;

public class WindowInputListener {

    private static final Timer timer = new Timer();
    private static final int DELAY = 0;
    private static final int RATE = 20;
    private static boolean isRunning = false;

    private WindowInputListener() {
    }

    public static void init() {
        isRunning = true;
        timer.schedule(wrap(task()), DELAY, RATE);
    }

    public static void shutDown() {
        isRunning = false;
        timer.cancel();
    }

    private static Runnable task() {
        return () -> {
            int input = Console.readBytes();
            Char chr = new Char(input);
//            keyLog(input);
            WindowPoolManager.notifyKey(chr);
            if (!isRunning) {
                timer.cancel();
            }
        };
    }

    private static TimerTask wrap(Runnable runnable) {
        return new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };
    }

    private static void keyLog(int key) {
        TaskManager.addTask(() -> {
            Console.clearLine(50);
            Console.clearLine(51);
            Console.drawString(50, Console.getScreenWidth() - 10, String.valueOf((char) key));
            Console.drawString(51, Console.getScreenWidth() - 10, String.valueOf(key));
        });
    }

    public boolean isRunning() {
        return isRunning;
    }
}
