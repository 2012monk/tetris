package tetris.ui.window;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import tetris.system.TaskManager;
import tetris.ui.console.Console;
import tetris.ui.constants.Char;

public class WindowInputListener {

    private static final long DELAY = 0;
    private static final long RATE = 10;
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private static boolean isRunning = false;

    private WindowInputListener() {
    }

    public static void init() {
        isRunning = true;
        service.scheduleAtFixedRate((task()), DELAY, RATE, TIME_UNIT);
    }

    public static void shutDown() {
        isRunning = false;
        service.shutdownNow();
    }

    private static Runnable task() {
        return () -> {
            if (!isRunning) {
                return;
            }
            int input = Console.readBytes();
            Char chr = new Char(input);
            if (input < 0) {
                return;
            }
            TaskManager.addTask(() -> WindowPoolManager.notifyKey(chr));
        };
    }

    public boolean isRunning() {
        return isRunning;
    }
}
