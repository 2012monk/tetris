package tetris.components;

import static tetris.constants.GameKey.KEY_DOWN;
import static tetris.constants.GameKey.KEY_K;
import static tetris.constants.GameKey.KEY_SPACE;
import static tetris.constants.GameKey.KEY_UP;
import static tetris.constants.GameStatus.END;
import static tetris.constants.GameStatus.PAUSE;
import static tetris.constants.GameStatus.RESTART;
import static tetris.constants.GameStatus.RESUME;
import static tetris.constants.GameStatus.START;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import tetris.ComponentContainer;
import tetris.annotations.OnMessage;
import tetris.constants.GameKey;
import tetris.constants.GameStatus;
import tetris.helper.TetrominoController;
import tetris.message.CurrentBlockMessage;
import tetris.message.GameKeyMessage;
import tetris.message.GameScoreMessage;
import tetris.message.GameStatusMessage;
import tetris.message.MenuSelectedMessage;
import tetris.message.NextBlockMessage;
import tetris.repository.TetrominoRepository;

public class TetrisBoardPanel extends ComponentContainer<Point> {

    private static final char EMPTY_SPACE = '.';
    private static final char BLOCK_SPACE = ' ';
    private final TetrominoGuider guider;
    private final GameScore score = new GameScore();
    private final TetrominoController controller;
    private final List<Point> points = new ArrayList<>();
    private GameStatus status = END;
    private Tetromino currentBlock = null;

    public TetrisBoardPanel(int x, int y, int width, int height) {
        super(x, y, width, height, true);
        this.emptySpace = EMPTY_SPACE;
        this.guider = new TetrominoGuider(this);
        this.controller = new TetrominoController(this);
        subscribe(GameStatusMessage.class);
        subscribe(GameKeyMessage.class);
    }

    @OnMessage
    public void gameStatusHandle(GameStatusMessage post) {
        GameStatus gameStatus = post.getPayload();
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
            endGame();
        }
        if (gameStatus == RESUME) {
            this.status = START;
        }
    }

    @OnMessage
    public void gameKeyHandle(GameKeyMessage post) {
        if (post != null) {
            move(post.getPayload());
        }
    }

    private void endGame() {
        clearBoard();
        this.currentBlock = null;
        this.status = END;
    }

    private void start() {
        if (status == END) {
            initBlock();
        }
        score.resetScore();
        status = START;
    }

    private void clearBoard() {
        components.clear();
    }

    private void pause() {
        this.status = PAUSE;
    }

    private void restart() {
        publishMessage(new GameStatusMessage(END));
        this.components.clear();
        this.currentBlock = null;
        clear();
        publishMessage(new GameStatusMessage(START));
    }

    private void move(GameKey key) {
        if (status != START) {
            return;
        }
        if (key == KEY_SPACE) {
            hardDrop();
            return;
        }
        if (key == KEY_DOWN) {
            drop();
            return;
        }
        if (key == KEY_K || key == KEY_UP) {
            rotate(key);
            return;
        }
        if (isCollide(simulateBlock(this.currentBlock, key))) {
            return;
        }
        render();
        key.move(this.currentBlock);
        this.guider.guideBlock(currentBlock);
    }

    private void rotate(GameKey key) {
        render();
        this.currentBlock = controller.rotateLeft(currentBlock);
        currentBlock.render();
        guider.guideBlock(currentBlock);
    }

    private void drop() {
        render();
        if (isCollide(currentBlock)) {
            gameOver();
            return;
        }
        if (isCollide(simulateBlock(currentBlock, KEY_DOWN))) {
            stackBlock(currentBlock);
            initBlock();
            return;
        }
        currentBlock.printDown();
        guider.render();
    }

    private void hardDrop() {
        stackBlock(guider.getGuideBlock());
        initBlock();
    }


    private Tetromino simulateBlock(Tetromino block, GameKey key) {
        return block.simulate(key);
    }

    public boolean isCollide(Tetromino block) {
        boolean isOverlapped = block.points().stream()
            .filter(Point::isInsideParent)
            .anyMatch(copiedPoint -> this.components.stream()
                .anyMatch(parentPoint -> parentPoint.isOverlapped(copiedPoint)));
        boolean isInsideParent = block.points().stream()
            .filter(p -> block.getRelativeX() + p.getRelativeX() >= 0)
            .allMatch(Point::isInsideParent);
        return !isInsideParent || isOverlapped;
    }

    private void stackBlock(Tetromino block) {
        block.points().forEach(p -> addComponent(new Point(p.getInnerX() - getInnerX(),
            p.getInnerY() - getInnerY(), block.getColor())));
        popStack();
    }

    private void popStack() {
        Map<Integer, List<Point>> lines = this.components.stream()
            .collect(Collectors.groupingBy(Point::getAbsoluteX));

        List<Integer> filledLines = getFilledLines(lines);
        int lineCount = filledLines.size();
        if (filledLines.isEmpty()) {
            render();
            return;
        }
        clearBoard();
        lines.entrySet().stream()
            .filter(e -> !filledLines.contains(e.getKey()))
            .forEach(e -> {
                int cali =
                    filledLines.size() + Collections.binarySearch(filledLines, e.getKey()) + 1;
                e.getValue().forEach(p -> addComponent(
                    new Point(p.getRelativeX() + cali, p.getRelativeY(), p.getColor()))
                );
            });
        render();
        sendPopLineCount(lineCount);
    }

    private void sendPopLineCount(int lineCount) {
        publishMessage(new GameScoreMessage(this.score.updateScore(lineCount)));
    }

    private List<Integer> getFilledLines(Map<Integer, List<Point>> lines) {
        return lines.entrySet()
            .stream()
            .filter(e -> e.getValue().size() == getInnerWidth())
            .map(Entry::getKey)
            .sorted()
            .collect(Collectors.toList());
    }

    private void gameOver() {
        status = END;
        publishMessage(new GameStatusMessage(END));
        publishMessage(new MenuSelectedMessage("leaderBoardInputMenu"));
//        MenuSelector.leaderBoardInput();
    }

    private void initBlock() {
        this.currentBlock = TetrominoRepository.getNextTetromino();
        publishMessage(new CurrentBlockMessage(currentBlock));
        publishMessage(new NextBlockMessage(TetrominoRepository.peekNextTetromino()));
        this.currentBlock.initBlock(this);
        guider.guideBlock(this.currentBlock);
        this.currentBlock.render();
    }
}
