package tetris.system;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledTaskHelper {

    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long delay, long period,
        TimeUnit unit) {
        return service.scheduleAtFixedRate(r, delay, period, unit);
    }

    public static void shutDown() {
        service.shutdownNow();
    }
}
