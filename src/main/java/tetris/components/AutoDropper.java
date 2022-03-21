package tetris.components;

import java.util.Timer;
import java.util.TimerTask;
import tetris.constants.Char;
import tetris.constants.SpecialKeyCode;
import tetris.window.WindowPoolManager;

public class AutoDropper {

    private static final int DROP_RATE = 300;
    private static final Timer timer = new Timer();
    private static boolean isRunning = false;

    private AutoDropper() {
    }

    public static void init() {
        isRunning = true;
        timer.scheduleAtFixedRate(wrap(task()), 0, DROP_RATE);
    }

    public static void shutDown() {
        isRunning = false;
        timer.cancel();
    }

    public static TimerTask wrap(Runnable r) {
        return new TimerTask() {
            @Override
            public void run() {
                r.run();
            }
        };
    }

    public static Runnable task() {
        return () -> {
            WindowPoolManager.notifyKey(new Char(SpecialKeyCode.KEY_DOWN));
            if (!isRunning) {
                timer.cancel();
            }
        };
    }

}
