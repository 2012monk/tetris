package tetris.components;

import java.util.LinkedList;
import java.util.List;

public class TetrisBoard extends ComponentImpl {

    private List<Point> filledPoints = new LinkedList<>();

    public void printBlock(Tetromino block) {
        block.setWindow(this.window);
        block.update();
    }

    public void stackBlock(Tetromino block) {
        filledPoints.addAll(block.points());
        block.points().forEach(p -> p.setWindow(this.window));
    }

    @Override
    public void update() {
        filledPoints.forEach(Point::update);
    }
}
