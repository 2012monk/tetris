package tetris.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tetris.ComponentImpl;
import tetris.console.Console;
import tetris.constants.Color;
import tetris.constants.Shape;
import tetris.message.CurrentBlockMessage;
import tetris.message.Post;
import tetris.repository.TetrominoRepository;

public class BlockCounter extends ComponentImpl {

    private static final String TITLE = "Block Counter";
    private static final Color COUNTER_COLOR = Color.GREEN;
    private final Map<Shape, Integer> counter = new HashMap<>();
    private final List<Tetromino> alignedBlocks = new ArrayList<>();
    private final int yAlign = 12;

    public BlockCounter(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        init();
        subscribe(CurrentBlockMessage.class);
    }

    public void increaseCount(Tetromino block) {
        counter.putIfAbsent(block.getShape(), 0);
        counter.computeIfPresent(block.getShape(), (k, v) -> v + 1);
        update();
    }

    private void init() {
        Arrays.stream(Shape.values())
            .forEach(s -> alignedBlocks.add(TetrominoRepository.getTetrominoByShape(s)));
        alignedBlocks.forEach(b -> b.setParent(this));
        alignedBlocks.forEach(b -> counter.put(b.getShape(), 0));
        for (int i = 1; i < alignedBlocks.size(); i++) {
            align(alignedBlocks.get(i - 1), alignedBlocks.get(i));
        }
        alignedBlocks.forEach(b -> {
            b.moveDown();
            b.moveDown();
            b.moveRight();
        });
    }

    private void align(Tetromino prev, Tetromino current) {
        int correction = 0;
        if (current.getShape() == Shape.I) {
            correction = -1;
        }
        for (int i = 0; i < prev.getActualHeight() + prev.getRelativeX() + correction; i++) {
            current.moveDown();
        }
    }

    private int getYAlign() {
        return getInnerY() + yAlign;
    }

    @Override
    public <T extends Post<?>> void onMessage(T post) {
        if (post instanceof CurrentBlockMessage) {
            increaseCount((Tetromino) post.getPayload());
        }
    }

    private void printTitle() {
        Console.drawString(getInnerX(), getInnerY() + (getInnerWidth() - TITLE.length()) / 2,
            TITLE);
    }

    @Override
    public void update() {
        if (!hasParent()) {
            return;
        }
        clear();
        printTitle();
        alignedBlocks.forEach(b -> {
            b.update();
            int correction = 0;
            if (b.getShape() == Shape.I) {
                correction = 1;
            }
            Console.drawString(b.getInnerX() + correction, getYAlign(),
                String.valueOf(counter.get(b.getShape())), COUNTER_COLOR, bg
            );
        });
    }
}
