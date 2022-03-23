package tetris.window;

import java.util.Timer;
import java.util.TimerTask;
import tetris.console.Console;
import tetris.constants.Char;

public class WindowInputListener {

    private static final int DELAY = 0;
    private static final int RATE = 20;
    private static Timer timer;
    private static boolean isRunning = false;
    private static Spatial keyLogSpace;

    private WindowInputListener() {
    }

    public static void init() {
        shutDown();
        isRunning = true;
        timer = new Timer();
        timer.schedule(wrap(task()), DELAY, RATE);
    }

    public static void shutDown() {
        if (timer == null) {
            return;
        }
        isRunning = false;
        timer.cancel();
        timer = null;
    }

    private static Runnable task() {
        return () -> {
            int input = Console.readBytes();
            Char chr = new Char(input);
            if (input < 0) {
                return;
            }
//            keyLog(input);
            WindowPoolManager.notifyKey(chr);
            if (!isRunning) {
                shutDown();
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
        getSpace().clear();
        Console.drawString(50, Console.getScreenWidth() - 10, String.valueOf((char) key));
        Console.drawString(51, Console.getScreenWidth() - 10, String.valueOf(key));
    }

    private static Spatial getSpace() {
        if (keyLogSpace == null) {
            keyLogSpace = new Space(50, Console.getScreenWidth() - 10, 5, 2, false);
        }
        return keyLogSpace;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
