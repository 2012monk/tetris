package tetris.ui.message;

import tetris.gameobject.Tetromino;

public class CurrentBlockMessage extends Post<Tetromino> {

    public CurrentBlockMessage(Tetromino payload) {
        super(payload);
    }
}
