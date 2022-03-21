package tetris.components;

import tetris.system.MessageBroker;
import tetris.system.Post;

public class NextBlockBoard extends ComponentImpl {

    public NextBlockBoard(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        MessageBroker.subscribe(NextBlockAlert.class, this);
    }

    @Override
    public void update() {
        clear();
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
        nextBlock.update();
    }
}
