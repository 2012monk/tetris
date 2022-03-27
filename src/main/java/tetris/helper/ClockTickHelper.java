package tetris.helper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import tetris.system.MessageBroker;
import tetris.ui.message.ClockTickMessage;

public class ClockTickHelper {

    private static ScheduledFuture<?> task;

    private static ClockTickHelper instance;
    private ScheduledExecutorService service;

    public static ClockTickHelper getInstance() {
        if (instance == null) {
            instance = new ClockTickHelper();
        }
        return instance;
    }

    public ScheduledExecutorService getService() {
        if (service == null) {
            service = Executors.newSingleThreadScheduledExecutor();
        }
        return service;
    }

    private void shutDown() {
        if (service == null) {
            return;
        }
        service.shutdownNow();
        service = null;
    }

    public void startGameClock() {
        stopClock();
        task = getService().scheduleAtFixedRate(
            () -> MessageBroker.publish(new ClockTickMessage()), 0, 1, TimeUnit.SECONDS);
    }

    public void stopClock() {
        if (task == null) {
            return;
        }
        task.cancel(true);
        shutDown();
    }
}
