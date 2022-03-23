package tetris.helper;

import static tetris.constants.GameStatus.END;
import static tetris.constants.GameStatus.PAUSE;
import static tetris.constants.GameStatus.RESTART;
import static tetris.constants.GameStatus.RESUME;
import static tetris.constants.GameStatus.START;

import java.util.HashMap;
import java.util.Map;
import tetris.components.ComponentImpl;
import tetris.constants.Char;
import tetris.constants.GameKey;
import tetris.constants.GameStatus;
import tetris.constants.SpecialKeyCode;
import tetris.message.GameKeyMessage;
import tetris.message.GameStatusMessage;
import tetris.system.Post;
import tetris.window.WindowPoolManager;

public class GameKeyListener extends ComponentImpl {

    private final Map<Char, GameStatusMessage> gameStatusOrder = new HashMap<>();
    private GameStatus status = END;

    public GameKeyListener() {
        super(0, 0, 0, 0, false);
        gameStatusOrder.put(new Char('q'), new GameStatusMessage(END));
        gameStatusOrder.put(new Char(SpecialKeyCode.KEY_SPACE), new GameStatusMessage(START));
        gameStatusOrder.put(new Char('p'), new GameStatusMessage(PAUSE));
        gameStatusOrder.put(new Char('s'), new GameStatusMessage(START));
        gameStatusOrder.put(new Char('r'), new GameStatusMessage(RESTART));
        subscribe(GameStatusMessage.class);
    }

    @Override
    public void update() {
    }

    @Override
    public <T extends Post<?>> void onMessage(T post) {
        if (!(post instanceof GameStatusMessage)) {
            return;
        }
        this.status = ((GameStatusMessage) post).getPayload();
        if (status == RESUME) {
            status = START;
        }
    }

    @Override
    public void handleKey(Char chr) {
        if (status != START) {
            statusOrder(chr);
            return;
        }
        if (GameKey.hasKey(chr)) {
            publishMessage(new GameKeyMessage(GameKey.getGameKey(chr)));
        } else {
            statusOrder(chr);
        }
    }

    private void statusOrder(Char chr) {
        if (this.status == PAUSE && chr.is(SpecialKeyCode.KEY_SPACE)) {
            publishMessage(new GameStatusMessage(RESUME));
            return;
        }
        if (gameStatusOrder.containsKey(chr)) {
            publishMessage(gameStatusOrder.get(chr));
        }
        if (status == END) {
            WindowPoolManager.shutDown();
        }
    }
}
