package tetris.components;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import tetris.console.Console;
import tetris.constants.Char;
import tetris.constants.GameKey;
import tetris.constants.SpecialKeyCode;
import tetris.system.FrameCounter;

public class TetrisBoard extends ComponentContainer<Point> {

    private static final char EMPTY_SPACE = '.';
    private static final char PAUSE_KEY = 'q';
    private static final char RESTART_KEY = 'r';
    private boolean isRunning = false;
    private boolean isEnd = false;
    private Tetromino currentBlock = null;
    private TetrominoGuider guider;

    public TetrisBoard(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        this.emptySpace = EMPTY_SPACE;
        clear();
        this.guider = new TetrominoGuider(this);
    }

    public void printBlock(Tetromino block) {
        update();
        block.initBlock(this);
        block.update();
    }

    private void initBlock() {
        this.currentBlock = TetrominoRepository.getNextTetromino();
        this.currentBlock.initBlock(this);
        if (isCollide(this.currentBlock)) {
            gameOver();
            return;
        }
        guider.guideBlock(this.currentBlock);
        this.currentBlock.update();
    }

    public void drop() {
        if (!isRunning) {
            return;
        }
//        this.guider.clear();
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
        block.printDown();
        guider.update();
    }

    public void move(GameKey key) {
        if (!isRunning) {
            return;
        }
        if (key == GameKey.KEY_SPACE) {
            hardDrop();
            return;
        }
        if (key == GameKey.MOVE_DOWN) {
            drop();
            return;
        }
        if (isCollide(simulateBlock(this.currentBlock, key))) {
            return;
        }
        update();
        key.move(this.currentBlock);
        this.guider.guideBlock(currentBlock);
    }

    private void hardDrop() {
        stackBlock(guider.getGuideBlock());
        initBlock();
    }

    private void gameOver() {
        isRunning = false;
        isEnd = true;
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
        boolean isInsideParent = block.points().stream()
            .filter(p -> p.getAbsoluteX() >= getInnerX())
            .allMatch(Point::isInsideParent);
        return !isInsideParent || isOverlapped;
    }

    public void stackBlock(Tetromino block) {
        block.points().forEach(p -> addComponent(new Point(p.getRelativeX() + block.getRelativeX(),
            p.getRelativeY() + block.getRelativeY(), block.getColor())));
        update();
        popStack();
    }

    public void popStack() {
        Map<Integer, List<Point>> lines = this.components.stream()
            .collect(Collectors.groupingBy(Point::getAbsoluteX));

        List<Integer> filledLines = getFilledLines(lines);
        if (filledLines.isEmpty()) {
            return;
        }
        this.components.clear();
        lines.entrySet().stream()
            .filter(e -> !filledLines.contains(e.getKey()))
            .forEach(e -> {
                int cali =
                    filledLines.size() + Collections.binarySearch(filledLines, e.getKey()) + 1;
                e.getValue().forEach(p -> addComponent(
                    new Point(p.getRelativeX() + cali, p.getRelativeY(), p.getColor()))
                );
            });
        update();
    }

    private List<Integer> getFilledLines(Map<Integer, List<Point>> lines) {
        return lines.entrySet()
            .stream()
            .filter(e -> e.getValue().size() == getInnerWidth())
            .map(Entry::getKey)
            .sorted()
            .collect(Collectors.toList());
    }

    @Override
    public void handleKey(Char chr) {
        if (chr.is(PAUSE_KEY)) {
            pause();
            return;
        }
        if (chr.is(RESTART_KEY)) {
            restart();
            return;
        }
        if (!isRunning && chr.is(SpecialKeyCode.KEY_SPACE)) {
            start();
            return;
        }
        if (!isRunning || !GameKey.hasKey(chr) || this.currentBlock == null) {
            return;
        }
        move(GameKey.getGameKey(chr));
    }

    public void pause() {
        isRunning = false;
    }

    public void restart() {
        isEnd = false;
        this.components.clear();
        this.currentBlock = null;
        clear();
        FrameCounter.wait(1500);
        start();
    }

    public void start() {
        isRunning = true;
        if (isEnd) {
            restart();
        }
        if (this.currentBlock != null) {
            return;
        }
        initBlock();
    }

    public List<Point> points() {
        return this.components;
    }
}
