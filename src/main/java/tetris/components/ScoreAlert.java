package tetris.components;

import tetris.system.Post;

public class ScoreAlert extends Post<Integer> {

    private static final String NAME = "tetris score alert";

    public ScoreAlert(int score) {
        super(NAME, score);
    }
}
