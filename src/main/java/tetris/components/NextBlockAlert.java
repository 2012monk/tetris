package tetris.components;

import tetris.system.Post;

public class NextBlockAlert extends Post<Tetromino> {

    private static final String NAME = "next block alert";

    public NextBlockAlert(Tetromino payload) {
        super(NAME, payload);
    }
}
