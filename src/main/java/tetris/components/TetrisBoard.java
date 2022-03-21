package tetris.components;

import static tetris.constants.GameStatus.END;
import static tetris.constants.GameStatus.PAUSE;
import static tetris.constants.GameStatus.RESTART;
import static tetris.constants.GameStatus.RUNNING;
import static tetris.constants.GameStatus.START;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import tetris.console.Console;
import tetris.constants.GameKey;
import tetris.constants.GameStatus;
import tetris.message.GameKeyMessage;
import tetris.message.GameStatusMessage;
import tetris.message.NextBlockAlert;
import tetris.message.ScoreAlert;
import tetris.repository.TetrominoRepository;
import tetris.system.Post;

public class TetrisBoard extends ComponentContainer<Point> {

    private static final char EMPTY_SPACE = '.';
    private final TetrominoGuider guider;
    private GameStatus status = END;
    private Tetromino currentBlock = null;

    public TetrisBoard(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        this.emptySpace = EMPTY_SPACE;
        clear();
        this.guider = new TetrominoGuider(this);
        subscribe(GameStatusMessage.class);
        subscribe(GameKeyMessage.class);
    }

    public void printBlock(Tetromino block) {
        update();
        block.initBlock(this);
        block.update();
    }

    private void initBlock() {
        this.currentBlock = TetrominoRepository.getNextTetromino();
        publishMessage(new NextBlockAlert(TetrominoRepository.peekNextTetromino()));
        this.currentBlock.initBlock(this);
        if (isCollide(this.currentBlock)) {
            gameOver();
            return;
        }
        guider.guideBlock(this.currentBlock);
        this.currentBlock.update();
    }

    public void drop() {
        if (status != RUNNING) {
            return;
        }
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
        if (isCollide(simulateBlock(block, GameKey.KEY_DOWN))) {
            stackBlock(block);
            initBlock();
            return;
        }
        block.printDown();
        guider.update();
    }

    public void move(GameKey key) {
        if (status != RUNNING) {
            return;
        }
        if (key == GameKey.KEY_SPACE) {
            hardDrop();
            return;
        }
        if (key == GameKey.KEY_DOWN) {
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
        status = END;
        publishMessage(new GameStatusMessage(END));
        Console.clearScreen();
        Console.drawString(Console.getScreenHeight() / 2, Console.getScreenWidth() / 2 - 5,
            "GAME OVER");
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
        int score = filledLines.size();
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
        printScore(score);
    }

    private void printScore(int score) {
        int mul = 1000;
        publishMessage(new ScoreAlert(score * mul));
    }

    private List<Integer> getFilledLines(Map<Integer, List<Point>> lines) {
        return lines.entrySet()
            .stream()
            .filter(e -> e.getValue().size() == getInnerWidth())
            .map(Entry::getKey)
            .sorted()
            .collect(Collectors.toList());
    }

    public void pause() {
        this.status = PAUSE;
    }

    public void restart() {
//        publishMessage(new GameStatusMessage(END));
        this.components.clear();
        this.currentBlock = null;
        clear();
//        Counter.waitMill(2000);
        start();
    }

    public void start() {
        if (status == END) {
            initBlock();
        }
        status = RUNNING;
        publishMessage(new GameStatusMessage(RUNNING));
    }

    @Override
    public <T extends Post<?>> void onMessage(T post) {
        if (post instanceof GameKeyMessage) {
            move(((GameKeyMessage) post).getPayload());
            return;
        }
        if (post instanceof GameStatusMessage) {
            GameStatus gameStatus = ((GameStatusMessage) post).getPayload();
            if (gameStatus == START) {
                start();
            }
            if (gameStatus == RESTART) {
                restart();
            }
            if (gameStatus == PAUSE) {
                pause();
            }
            if (gameStatus == END) {
                this.status = gameStatus;
            }
        }
    }
}
