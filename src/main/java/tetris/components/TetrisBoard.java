package tetris.components;

import java.util.List;
import tetris.window.Rectangle;

public class TetrisBoard extends ComponentContainer {

    public TetrisBoard(Rectangle space) {
        super(space);
    }

    public TetrisBoard(int x, int y, int width, int height, List<Point> filledPoints) {
        this(x, y, width, height);
        this.components.addAll(filledPoints);
    }

    public TetrisBoard(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void printBlock(Tetromino block) {
        clear();
        block.setSpace(this.space);
        block.update();
    }

    public void stackBlock(Tetromino block) {
        components.addAll(block.points());
        block.points().forEach(p -> p.setSpace(this.space));
    }
}
