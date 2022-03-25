package tetris.helper;

import static tetris.constants.GameStatus.END;
import static tetris.constants.GameStatus.PAUSE;
import static tetris.constants.GameStatus.RESUME;
import static tetris.constants.GameStatus.START;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import tetris.ComponentImpl;
import tetris.annotations.OnMessage;
import tetris.constants.GameKey;
import tetris.constants.GameStatus;
import tetris.message.GameKeyMessage;
import tetris.message.GameStatusMessage;

public class AutoDropper extends ComponentImpl {

    private static final int DROP_RATE = 450;
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private static ScheduledExecutorService service;
    private static GameStatus status = END;

    public AutoDropper() {
        super(0, 0, 0, 0, false);
        subscribe(GameStatusMessage.class);
    }

    public static void shutDown() {
        if (service == null) {
            return;
        }
        service.shutdown();
        service = null;
        status = END;
    }

    private void start() {
        shutDown();
        status = START;
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(wrap(task()), 0, DROP_RATE, TIME_UNIT);
    }

    public TimerTask wrap(Runnable r) {
        return new TimerTask() {
            @Override
            public void run() {
                r.run();
            }
        };
    }

    public Runnable task() {
        return () -> {
            if (status != START) {
                shutDown();
            }
            publishMessage(new GameKeyMessage(GameKey.KEY_DOWN));
        };
    }

    @OnMessage
    public void onMessage(GameStatusMessage post) {
        status = post.getPayload();
        if (status == START || status == RESUME) {
            start();
            return;
        }
        if (status == END || status == PAUSE) {
            shutDown();
        }
    }

    @Override
    public void update() {
    }
}
