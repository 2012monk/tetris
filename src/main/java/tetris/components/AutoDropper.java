package tetris.components;

import tetris.constants.Char;
import tetris.constants.SpecialKeyCode;
import tetris.system.FrameCounter;
import tetris.window.WindowPoolManager;

public class AutoDropper implements Runnable {

    private static final int DROP_RATE = 300;
    private static Thread thread;
    private static AutoDropper instance;
    private static boolean isRunning = false;

    private AutoDropper() {
    }

    public static AutoDropper getInstance() {
        if (instance == null) {
            instance = new AutoDropper();
        }
        return instance;
    }

    public static void init() {
        if (thread != null) {
            return;
        }
        isRunning = true;
        thread = new Thread(getInstance());
        thread.start();
    }

    public static void shutDown() {
        isRunning = false;
        thread.interrupt();
        thread = null;
    }

    @Override
    public void run() {
        while (isRunning) {
            FrameCounter.wait(DROP_RATE);
            WindowPoolManager.notifyKey(new Char(SpecialKeyCode.KEY_DOWN));
        }
    }
}
