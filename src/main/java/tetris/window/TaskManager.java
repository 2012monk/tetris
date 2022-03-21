package tetris.window;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskManager implements Runnable {

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

    public static synchronized void addTask(Task task) {
        taskQueue.add(task);
    }

    public static boolean removeTask(Task task) {
        return taskQueue.remove(task);
    }

    private static void task() {
        if (taskQueue.isEmpty()) {
            return;
        }
        taskQueue.removeFirst().action();
    }

    @Override
    public void run() {
        while (isRunning) {
            task();
        }
    }
}
