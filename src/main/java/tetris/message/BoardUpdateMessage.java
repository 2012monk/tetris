package tetris.message;

import tetris.model.TetrisBoard;

public class BoardUpdateMessage extends Post<TetrisBoard> {

    public BoardUpdateMessage(TetrisBoard payload) {
        super(payload);
    }
}
