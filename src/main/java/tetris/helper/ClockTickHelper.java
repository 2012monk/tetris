package tetris.helper;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import tetris.system.ScheduledTaskHelper;
import tetris.ui.message.GameClock;

public class ClockTickHelper implements GameHelper {

    private static ScheduledFuture<?> task;
    private static ClockTickHelper instance;
    private final GameClock clock;

    private ClockTickHelper() {
        clock = new GameClock();
    }

    public static ClockTickHelper getInstance() {
        if (instance == null) {
            instance = new ClockTickHelper();
        }
        return instance;
    }

    @Override
    public void start() {
        clock.reset();
        startGameClock();
    }

    @Override
    public void pause() {
        stopClock();
    }

    @Override
    public void resume() {
        startGameClock();
    }

    @Override
    public void stop() {
        stopClock();
    }

    private void startGameClock() {
        stopClock();
        task = ScheduledTaskHelper.scheduleAtFixedRate(
            clock::increaseTick, 1, 1, TimeUnit.SECONDS);
    }

    private void stopClock() {
        if (task == null) {
            return;
        }
        task.cancel(true);
    }
}
