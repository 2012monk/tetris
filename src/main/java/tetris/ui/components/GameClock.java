package tetris.ui.components;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import tetris.constants.GameStatus;
import tetris.system.TaskManager;
import tetris.ui.ComponentImpl;
import tetris.ui.annotations.OnMessage;
import tetris.ui.console.Console;
import tetris.ui.message.GameStatusMessage;

public class GameClock extends ComponentImpl {

    private ScheduledExecutorService service;
    private int tick = 0;

    public GameClock(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        subscribe(GameStatusMessage.class);
    }

    @OnMessage
    public void onMessage(GameStatusMessage post) {
        GameStatus status = post.getPayload();
        if (status == GameStatus.END || status == GameStatus.PAUSE) {
            stop();
        }
        if (status == GameStatus.START) {
            initClock();
        }
        if (status == GameStatus.START || status == GameStatus.RESUME) {
            start();
        }
    }

    private void stop() {
        if (service == null) {
            return;
        }
        service.shutdown();
        service = null;
    }

    private void initClock() {
        this.tick = 0;
    }

    private void start() {
        if (service == null) {
            service = Executors.newSingleThreadScheduledExecutor();
        }
        service.scheduleAtFixedRate(task(), 0, 1, TimeUnit.SECONDS);
    }

    private void increaseTick() {
        this.tick += 1;
    }

    private TimerTask task() {
        return new TimerTask() {
            @Override
            public void run() {
                TaskManager.addTask(() -> {
                    increaseTick();
                    render();
                });
            }
        };
    }

    private String getTick() {
        int sec = tick % 60;
        int minute = (tick / 60) % 60;
        return String.format("%02d", minute) + ":" + String.format("%02d", sec);
    }

    @Override
    public void render() {
        if (!hasParent()) {
            return;
        }
        clear();
        String strTick = getTick();
        Console.drawString(getInnerX(),
            getInnerWidth() / 2 - strTick.length() / 2 + getInnerY(),
            strTick);
    }
}
