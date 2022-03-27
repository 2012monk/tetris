package tetris.ui.message;

import tetris.constants.GameStatus;

public class GameStatusMessage extends Post<GameStatus> {

    public GameStatusMessage(GameStatus payload) {
        super(payload);
    }
}
