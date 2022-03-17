package tetris.components;

import java.util.List;

public class TetrisBoard extends ComponentContainer {

    public TetrisBoard(int x, int y, int width, int height, List<Point> filledPoints) {
        super(x, y, width, height, false, filledPoints);
    }

    public TetrisBoard(int x, int y, int width, int height) {
        super(x, y, width, height, false);
    }

    public void printBlock(Tetromino block) {
//        clear();
        block.setParent(this);
        block.update();
    }

    public void stackBlock(Tetromino block) {
        addComponents(block.points());
    }
}
