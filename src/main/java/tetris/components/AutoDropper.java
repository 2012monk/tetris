package tetris.components;

import tetris.constants.KeyCode;
import tetris.system.FrameCounter;
import tetris.window.WindowPoolManager;

public class AutoDropper implements Runnable {

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
//            WindowPoolManager.notifyKey(KeyCode.KEY_DOWN);
            FrameCounter.wait(300);
            WindowPoolManager.notifyKey(KeyCode.KEY_DOWN);
        }
    }
}
