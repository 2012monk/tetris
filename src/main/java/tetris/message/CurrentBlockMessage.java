package tetris.message;

import tetris.components.Tetromino;

public class CurrentBlockMessage extends Post<Tetromino> {

    public CurrentBlockMessage(Tetromino payload) {
        super(payload);
    }
}
