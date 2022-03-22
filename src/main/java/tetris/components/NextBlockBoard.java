package tetris.components;

import tetris.message.NextBlockAlert;
import tetris.system.Post;

public class NextBlockBoard extends ComponentImpl {

    private Tetromino nextBlock;

    public NextBlockBoard(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        subscribe(NextBlockAlert.class);
    }

    @Override
    public void update() {
        clear();
        if (nextBlock != null) {
            nextBlock.update();
        }
    }

    public void alignBlock(Tetromino tetromino) {
        if (tetromino.getActualWidth() == 2) {
            tetromino.moveRight();
        }
    }

    @Override
    public <T extends Post<?>> void onMessage(T post) {
        if (!(post instanceof NextBlockAlert)) {
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
