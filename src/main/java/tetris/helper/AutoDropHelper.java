package tetris.helper;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import tetris.constants.BlockMovement;
import tetris.system.MessageBroker;
import tetris.system.ScheduledTaskHelper;
import tetris.ui.message.GameKeyMessage;
import tetris.ui.message.GameStatusMessage;

public class AutoDropHelper implements GameHelper {

    private static final int DROP_RATE = 450;
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
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

    @Override
    public void start() {
        stop();
        future = ScheduledTaskHelper.scheduleAtFixedRate((task()), DROP_RATE, DROP_RATE, TIME_UNIT);
    }

    @Override
    public void stop() {
        if (future == null || future.isCancelled()) {
            return;
        }
        future.cancel(true);
    }

    @Override
    public void pause() {
        stop();
    }

    @Override
    public void resume() {
        start();
    }

    public Runnable task() {
        return () -> MessageBroker.publish(new GameKeyMessage(BlockMovement.KEY_DOWN));
    }
}
