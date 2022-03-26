package tetris.components;

import tetris.ComponentImpl;
import tetris.annotations.OnMessage;
import tetris.message.NextBlockMessage;

public class NextBlockBoard extends ComponentImpl {

    private Tetromino nextBlock;

    public NextBlockBoard(int x, int y, int width, int height) {
        this(x, y, width, height, false);
    }

    public NextBlockBoard(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        subscribe(NextBlockMessage.class);
    }

    @Override
    public void render() {
        clear();
        if (nextBlock != null) {
            nextBlock.render();
        }
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
        nextBlock.setParent(this);
        alignBlock(nextBlock);
        this.nextBlock = nextBlock;
        render();
    }
}
