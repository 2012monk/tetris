package tetris.message;

import tetris.components.GameScore;

public class GameScoreMessage extends Post<GameScore> {

    private static final String NAME = "tetris score alert";

    public GameScoreMessage(GameScore payload) {
        super(NAME, payload);
    }
}
