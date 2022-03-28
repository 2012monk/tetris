package tetris.helper;

import static tetris.constants.GameStatus.END;
import static tetris.constants.GameStatus.PAUSE;
import static tetris.constants.GameStatus.RESTART;
import static tetris.constants.GameStatus.RESUME;
import static tetris.constants.GameStatus.START;

import java.util.HashMap;
import java.util.Map;
import tetris.constants.AudioStatus;
import tetris.constants.BlockMovement;
import tetris.constants.GameStatus;
import tetris.system.MessageBroker;
import tetris.ui.annotations.OnMessage;
import tetris.ui.constants.Char;
import tetris.ui.constants.SpecialKeyCode;
import tetris.ui.message.AudioMessage;
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
        gameStatusOrder.put(new Char('p'), new GameStatusMessage(PAUSE));
        gameStatusOrder.put(new Char('s'), new GameStatusMessage(START));
        gameStatusOrder.put(new Char('r'), new GameStatusMessage(RESTART));
        MessageBroker.subscribe(GameStatusMessage.class, this);
    }

    @OnMessage
    public void onMessage(GameStatusMessage post) {
        this.status = post.getPayload();
    }

    public void handleKey(Char chr) {
        if (chr.is('m')) {
            publishMessage(new AudioMessage(AudioStatus.MUTE));
            return;
        }
        if (isMoveBlocked()) {
            statusOrder(chr);
            return;
        }
        if (isStatusOrder(chr)) {
            statusOrder(chr);
            return;
        }
        if (isGameKey(chr)) {
            publishMessage(new GameKeyMessage(BlockMovement.getGameKey(chr)));
        }
    }

    private boolean isStatusOrder(Char chr) {
        if (isPaused()) {
            return true;
        }
        boolean isHasKey = gameStatusOrder.containsKey(chr);
        if (isHasKey) {
            return gameStatusOrder.get(chr).getPayload() != status;
        }
        return false;
    }

    private boolean isGameKey(Char chr) {
        return BlockMovement.hasKey(chr);
    }

    private boolean isMoveBlocked() {
        return status != RESUME && status != START;
    }

    private boolean isPaused() {
        return status == PAUSE;
    }

    private void statusOrder(Char chr) {
        if (isPaused()) {
            publishMessage(new GameStatusMessage(RESUME));
            return;
        }
        GameStatusMessage message = gameStatusOrder.get(chr);
        if (message.getPayload() == RESTART) {
            restart();
            return;
        }
        publishMessage(message);
        if (message.getPayload() == END) {
            publishMessage(new MenuSelectedMessage("mainMenu"));
        }
    }

    private void restart() {
        publishMessage(new GameStatusMessage(END));
        publishMessage(new GameStatusMessage(START));
    }

    private void publishMessage(Post<?> msg) {
        MessageBroker.publish(msg);
    }

}
