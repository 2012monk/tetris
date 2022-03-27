package tetris.helper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import tetris.constants.GameKey;
import tetris.system.MessageBroker;
import tetris.ui.message.GameKeyMessage;
import tetris.ui.message.GameStatusMessage;

public class AutoDropHelper {

    private static final int DROP_RATE = 450;
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private static ScheduledExecutorService service;
    private static ScheduledFuture<?> future;
    private static AutoDropHelper instance;

    private AutoDropHelper() {
        MessageBroker.subscribe(GameStatusMessage.class, this);
    }

    public static AutoDropHelper getInstance() {
        if (instance == null) {
            instance = new AutoDropHelper();
        }
        return instance;
    }

    private ScheduledExecutorService getService() {
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

    public void stopTask() {
        if (future == null) {
            return;
        }
        future.cancel(true);
        shutDown();
    }

    public void start() {
        stopTask();
        future = getService().scheduleAtFixedRate((task()), 0, DROP_RATE, TIME_UNIT);
    }

    public Runnable task() {
        return () -> MessageBroker.publish(new GameKeyMessage(GameKey.KEY_DOWN));
    }
}
