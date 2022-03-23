package tetris.helper;

import static tetris.constants.GameStatus.END;
import static tetris.constants.GameStatus.PAUSE;
import static tetris.constants.GameStatus.RESUME;
import static tetris.constants.GameStatus.START;

import java.util.Timer;
import java.util.TimerTask;
import tetris.components.ComponentImpl;
import tetris.constants.GameKey;
import tetris.constants.GameStatus;
import tetris.message.GameKeyMessage;
import tetris.message.GameStatusMessage;
import tetris.system.Post;

public class AutoDropper extends ComponentImpl {

    private static final int DROP_RATE = 450;
    private static Timer timer;
    private static GameStatus status = END;

    public AutoDropper() {
        super(0, 0, 0, 0, false);
        subscribe(GameStatusMessage.class);
    }

    public static void shutDown() {
        if (timer == null) {
            return;
        }
        timer.cancel();
        timer = null;
        status = END;
    }

    private void start() {
        shutDown();
        status = START;
        timer = new Timer();
        timer.scheduleAtFixedRate(wrap(task()), 0, DROP_RATE);
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

    @Override
    public <T extends Post<?>> void onMessage(T post) {
        if (post instanceof GameStatusMessage) {
            status = ((GameStatusMessage) post).getPayload();
            if (status == START || status == RESUME) {
                start();
                return;
            }
            if (status == END || status == PAUSE) {
                timer.cancel();
            }
        }
    }

    @Override
    public void update() {
    }
}
