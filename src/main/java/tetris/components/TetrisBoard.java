package tetris.components;

import java.util.List;

public class TetrisBoard extends ComponentContainer<Point> {

    public TetrisBoard(int x, int y, int width, int height, List<Point> filledPoints) {
        super(x, y, width, height, false, filledPoints);
    }

    public TetrisBoard(int x, int y, int width, int height) {
        super(x, y, width, height, false);
    }

    public void printBlock(Tetromino block) {
        update();
        block.init(this);
        block.update();
    }

    public void stackBlock(Tetromino block) {
        block.points().forEach(p -> addComponent(new Point(p.getRelativeX() + block.getRelativeX(),
            p.getRelativeY() + block.getRelativeY(), block.getColor())));
    }
}
