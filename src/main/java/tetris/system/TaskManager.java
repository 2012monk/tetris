package tetris.system;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import tetris.Task;
import tetris.ui.console.Console;

public class TaskManager implements Runnable {

    private static final long DEFAULT_TIMEOUT = 100;
    private static final BlockingDeque<Task> taskQueue = new LinkedBlockingDeque<>();
    private static TaskManager instance;
    private static Thread thread;
    private static boolean isRunning;

    private TaskManager() {
    }

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public static synchronized void init() {
        isRunning = true;
        if (thread == null) {
            thread = new Thread(getInstance());
            thread.start();
        }
    }

    public static synchronized void shutDown() {
        isRunning = false;
        taskQueue.clear();
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    public static void addTask(Task task) {
        taskQueue.add(task);
        thread.interrupt();
    }

    public static boolean removeTask(Task task) {
        return taskQueue.remove(task);
    }

    @Override
    public void run() {
        while (isRunning) {
            if (taskQueue.isEmpty()) {
                try {
                    Thread.sleep(DEFAULT_TIMEOUT);
                } catch (InterruptedException ignore) {
                }
                continue;
            }
            Console.startDraw();
            taskQueue.removeFirst().action();
            Console.endDraw();
        }
    }
}
