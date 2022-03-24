package tetris.message;

import tetris.components.Tetromino;

public class NextBlockMessage extends Post<Tetromino> {

    private static final String NAME = "next block alert";

    public NextBlockMessage(Tetromino payload) {
        super(NAME, payload);
    }
}
