package tetris.helper;

import static tetris.constants.GameStatus.END;
import static tetris.constants.GameStatus.PAUSE;
import static tetris.constants.GameStatus.RESTART;
import static tetris.constants.GameStatus.RESUME;
import static tetris.constants.GameStatus.START;

import java.util.HashMap;
import java.util.Map;
import tetris.constants.GameKey;
import tetris.constants.GameStatus;
import tetris.system.MessageBroker;
import tetris.ui.annotations.OnMessage;
import tetris.ui.constants.Char;
import tetris.ui.constants.SpecialKeyCode;
import tetris.ui.message.GameKeyMessage;
import tetris.ui.message.GameStatusMessage;
import tetris.ui.message.MenuSelectedMessage;
import tetris.ui.message.Post;

public class GameKeyHandler {

    private final Map<Char, GameStatusMessage> gameStatusOrder = new HashMap<>();
    private GameStatus status = END;

    public GameKeyHandler() {
        gameStatusOrder.put(new Char('q'), new GameStatusMessage(END));
        gameStatusOrder.put(new Char(SpecialKeyCode.KEY_ESC), new GameStatusMessage(END));
        gameStatusOrder.put(new Char(SpecialKeyCode.KEY_SPACE), new GameStatusMessage(START));
        gameStatusOrder.put(new Char('p'), new GameStatusMessage(PAUSE));
        gameStatusOrder.put(new Char('s'), new GameStatusMessage(START));
        gameStatusOrder.put(new Char('r'), new GameStatusMessage(RESTART));
        MessageBroker.subscribe(GameStatusMessage.class, this);
    }

    @OnMessage
    public void onMessage(GameStatusMessage post) {
        this.status = post.getPayload();
        if (status == RESUME) {
            status = START;
        }
    }

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
        if (!gameStatusOrder.containsKey(chr)) {
            return;
        }
        GameStatusMessage message = gameStatusOrder.get(chr);
        publishMessage(message);
        if (message.getPayload() == END) {
            publishMessage(new MenuSelectedMessage("mainMenu"));
        }
    }

    private void publishMessage(Post<?> msg) {
        MessageBroker.publish(msg);
    }

}
