package tetris.ui.message;

import tetris.constants.GameKey;

public class GameKeyMessage extends Post<GameKey> {

    public GameKeyMessage(GameKey payload) {
        super(payload);
    }
}
