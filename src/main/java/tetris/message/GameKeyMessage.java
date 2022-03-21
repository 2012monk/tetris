package tetris.message;

import tetris.constants.GameKey;
import tetris.system.Post;

public class GameKeyMessage extends Post<GameKey> {

    public GameKeyMessage(GameKey payload) {
        super(payload);
    }
}
