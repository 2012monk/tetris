package tetris.message;

import tetris.constants.GameStatus;
import tetris.system.Post;

public class GameStatusMessage extends Post<GameStatus> {

    public GameStatusMessage(GameStatus payload) {
        super(payload);
    }
}
