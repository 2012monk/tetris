package tetris.components;

import tetris.annotations.OnMessage;
import tetris.message.NextBlockMessage;

public class NextBlockBoard extends MatrixBoard {

    public NextBlockBoard(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        subscribe(NextBlockMessage.class);
    }

    public void alignBlock(Tetromino tetromino) {
        tetromino.alignCenter(getInnerWidth());
        if (tetromino.getBlockSize() % 2 == 0) {
            tetromino.moveRight();
        }
    }

    @OnMessage
    public void onMessage(NextBlockMessage post) {
        clear();
        Tetromino nextBlock = post.getPayload();
        if (nextBlock == null) {
            clear();
            return;
        }
        alignBlock(nextBlock);
        updateCurrentState(nextBlock.getCalculatedCells());
        render();
    }
}
