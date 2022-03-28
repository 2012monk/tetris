package tetris.ui.system;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import tetris.Task;
import tetris.ui.console.Console;

public class TaskManager {

    private static final ExecutorService service = Executors.newSingleThreadExecutor();
    private static final BlockingDeque<Task> taskQueue = new LinkedBlockingDeque<>();
    private static Future<?> future;

    private TaskManager() {
    }

    public static void init() {
        start();
    }

    public static synchronized void shutDown() {
        if (future != null && !future.isCancelled()) {
            future.cancel(true);
        }
        service.shutdownNow();
    }

    public static void addTask(Task task) {
        taskQueue.add(task);
        start();
    }

    private static void start() {
        if (isRunning()) {
            return;
        }
        future = service.submit(mainTask());
    }

    private static Runnable mainTask() {
        return () -> {
            while (!taskQueue.isEmpty()) {
                Console.startDraw();
                taskQueue.removeFirst().action();
                Console.endDraw();
            }
        };
    }

    private static boolean isRunning() {
        return future != null && !future.isDone();
    }

    public static boolean removeTask(Task task) {
        return taskQueue.remove(task);
    }
}
