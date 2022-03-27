package tetris.message;

import tetris.gameobject.TetrisBoard;

public class BoardUpdateMessage extends Post<TetrisBoard> {

    public BoardUpdateMessage(TetrisBoard payload) {
        super(payload);
    }
}
