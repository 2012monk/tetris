package tetris.message;

import tetris.components.Tetromino;
import tetris.system.Post;

public class NextBlockMessage extends Post<Tetromino> {

    private static final String NAME = "next block alert";

    public NextBlockMessage(Tetromino payload) {
        super(NAME, payload);
    }
}
