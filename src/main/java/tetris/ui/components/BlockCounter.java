package tetris.ui.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import tetris.constants.TetrominoShape;
import tetris.gameobject.Tetromino;
import tetris.repository.TetrominoRepository;
import tetris.ui.annotations.OnMessage;
import tetris.ui.console.Console;
import tetris.ui.constants.Color;
import tetris.ui.message.CurrentBlockMessage;

public class BlockCounter extends MatrixBoard {

    private static final String TITLE = "Block Counter";
    private static final Color COUNTER_COLOR = Color.GREEN;
    private final Map<TetrominoShape, Integer> counter = new HashMap<>();
    private final Map<TetrominoShape, Integer> position = new HashMap<>();
    private final int Y_ALIGN = 12;

    public BlockCounter(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        init();
        subscribe(CurrentBlockMessage.class);
    }

    private void init() {
        List<Tetromino> alignedBlocks = new ArrayList<>();
        Arrays.stream(TetrominoShape.values())
            .forEach(s -> alignedBlocks.add(TetrominoRepository.getTetrominoByShape(s)));
        for (int i = 1; i < alignedBlocks.size(); i++) {
            align(alignedBlocks.get(i - 1), alignedBlocks.get(i));
        }
        alignedBlocks.forEach(b -> b.move(3, 1));
        alignedBlocks.forEach(b -> counter.put(b.getShape(), 0));
        alignedBlocks.forEach(b -> position.put(b.getShape(), b.getX()));
        position.computeIfPresent(TetrominoShape.I, (k, v) -> v + 1);
        updateCurrentState(alignedBlocks
            .stream()
            .flatMap(b -> b.getCalculatedCells().stream())
            .collect(Collectors.toList()));
    }

    private void align(Tetromino prev, Tetromino current) {
        int correction = 0;
        if (current.getShape() == TetrominoShape.I) {
            correction = -1;
        }
        current.move(prev.getHeight() + prev.getX() + correction, 0);
    }

    @OnMessage
    public void onMessage(CurrentBlockMessage post) {
        TetrominoShape shape = post.getPayload().getShape();
        counter.putIfAbsent(shape, 0);
        counter.computeIfPresent(shape, (k, v) -> v + 1);
        render();
    }

    private void printTitle() {
        Console.drawString(getInnerX(), getInnerY() + (getInnerWidth() - TITLE.length()) / 2,
            TITLE);
    }

    @Override
    public void render() {
        super.render();
        if (!hasParent()) {
            return;
        }
        printTitle();
        position.keySet()
            .forEach(this::printNumber);
    }

    private void printNumber(TetrominoShape shape) {
        int pos = position.get(shape);
        int count = counter.get(shape);
        Console.drawString(pos + getInnerX(), Y_ALIGN + getInnerY(), String.valueOf(count),
            COUNTER_COLOR, bg);
    }
}
