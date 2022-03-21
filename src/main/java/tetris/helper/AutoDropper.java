package tetris.helper;

import java.util.Timer;
import java.util.TimerTask;
import tetris.components.ComponentImpl;
import tetris.constants.GameKey;
import tetris.constants.GameStatus;
import tetris.message.GameKeyMessage;
import tetris.message.GameStatusMessage;
import tetris.system.Post;

public class AutoDropper extends ComponentImpl {

    private static final int DROP_RATE = 300;
    private static final Timer timer = new Timer();
    private static GameStatus status = GameStatus.END;

    public AutoDropper() {
        super(0, 0, 0, 0, false);
        subscribe(GameStatusMessage.class);
    }

    public static void shutDown() {
        timer.cancel();
    }

    private void init() {
        status = GameStatus.RUNNING;
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
            if (status == GameStatus.END) {
                shutDown();
            }
            publishMessage(new GameKeyMessage(GameKey.KEY_DOWN));
        };
    }

    @Override
    public <T extends Post<?>> void onMessage(T post) {
        if (post instanceof GameStatusMessage) {
            status = ((GameStatusMessage) post).getPayload();
            if (status == GameStatus.START) {
                init();
            }
        }
    }

    @Override
    public void update() {

    }
}
