package tetris.components;

import java.util.List;
import tetris.console.Console;
import tetris.constants.GameKey;

public class TetrisBoard extends ComponentContainer<Point> {

    private Tetromino currentBlock = null;

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

    public void initBlock() {
        this.currentBlock = TetrominoRepository.getNextTetromino();
        this.currentBlock.init(this);
        if (isCollide(this.currentBlock)) {
            gameOver();
            return;
        }
        this.currentBlock.update();
    }

    public void drop() {
        update();
        if (this.currentBlock == null) {
            initBlock();
            return;
        }
        dropBlock(this.currentBlock);
    }

    public void dropBlock(Tetromino block) {
        if (isCollide(block)) {
            gameOver();
        }
        if (isCollide(simulateBlock(block, GameKey.MOVE_DOWN))) {
            stackBlock(block);
            initBlock();
            return;
        }
        block.moveDown();
        block.update();
    }

    public void move(GameKey key) {
        if (isCollide(simulateBlock(this.currentBlock, key))) {
            return;
        }
        update();
        key.move(this.currentBlock);
        this.currentBlock.update();
    }

    private void gameOver() {
        Console.clearScreen();
        Console.drawString(40, 40, "OVER");
    }

    public Tetromino simulateBlock(Tetromino block, GameKey key) {
        Tetromino copied = block.copy();
        key.move(copied);
        copied.setParent(this);
        return copied;
    }

    public boolean isCollide(Tetromino block) {
        boolean isOverlapped = block.points().stream()
            .filter(Point::isInsideParent)
            .anyMatch(copiedPoint -> this.components.stream()
                .anyMatch(parentPoint -> parentPoint.isOverlapped(copiedPoint)));
        // TODO 버그 가능성! 내려가지 않았을때 좌우 이동 처리해야함
//        if (block.getRelativeX() < 0) {
//            return isOverlapped;
//        }
        boolean isInsideParent = block.points().stream()
            .filter(p -> p.getAbsoluteX() >= getInnerX())
            .allMatch(Point::isInsideParent);
        return !isInsideParent || isOverlapped;
    }

    public void stackBlock(Tetromino block) {
        block.points().forEach(p -> addComponent(new Point(p.getRelativeX() + block.getRelativeX(),
            p.getRelativeY() + block.getRelativeY(), block.getColor())));
        update();
    }
}
