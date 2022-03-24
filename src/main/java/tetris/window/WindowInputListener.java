package tetris.window;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import tetris.Spatial;
import tetris.console.Console;
import tetris.constants.Char;

public class WindowInputListener {

    private static final long DELAY = 0;
    private static final long RATE = 10;
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private static ScheduledExecutorService service;
    private static boolean isRunning = false;
    private static Spatial keyLogSpace;

    private WindowInputListener() {
    }

    public static void init() {
        shutDown();
        isRunning = true;
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(wrap(task()), DELAY, RATE, TIME_UNIT);
    }

    public static void shutDown() {
        if (service == null) {
            return;
        }
        isRunning = false;
        service.shutdown();
        service = null;
    }

    private static Runnable task() {
        return () -> {
            int input = Console.readBytes();
            Char chr = new Char(input);
            if (input < 0) {
                return;
            }
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
        Console.drawString(Console.getScreenHeight() - 2, Console.getScreenWidth() - 10,
            String.valueOf((char) key));
        Console.drawString(Console.getScreenHeight() - 1, Console.getScreenWidth() - 10,
            String.valueOf(key));
    }

    private static Spatial getSpace() {
        if (keyLogSpace == null) {
            keyLogSpace = new Window(50, Console.getScreenWidth() - 10, 5, 2, false);
        }
        return keyLogSpace;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
