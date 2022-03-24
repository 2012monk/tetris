package tetris.components;

import tetris.ComponentImpl;
import tetris.message.NextBlockMessage;
import tetris.message.Post;

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
    public void update() {
        clear();
        if (nextBlock != null) {
            nextBlock.update();
        }
    }

    public void alignBlock(Tetromino tetromino) {
        tetromino.alignCenter();
        if (tetromino.getBlockSize() % 2 == 0) {
            tetromino.moveRight();
        }
    }

    @Override
    public <T extends Post<?>> void onMessage(T post) {
        if (!(post instanceof NextBlockMessage)) {
            return;
        }
        clear();
        Tetromino nextBlock = (Tetromino) post.getPayload();
        if (nextBlock == null) {
            clear();
            return;
        }
        nextBlock.setParent(this);
        alignBlock(nextBlock);
        this.nextBlock = nextBlock;
        update();
    }
}
