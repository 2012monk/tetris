package tetris.message;

import tetris.components.Tetromino;
import tetris.system.Post;

public class CurrentBlockMessage extends Post<Tetromino> {

    public CurrentBlockMessage(Tetromino payload) {
        super(payload);
    }
}
