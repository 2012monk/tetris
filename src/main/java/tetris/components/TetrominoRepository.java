package tetris.components;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import tetris.constants.Shape;

public class TetrominoRepository {

    private static final Map<Shape, Tetromino> tetrominos = new HashMap<>();
    private static final ArrayDeque<Tetromino> pool = new ArrayDeque<>();

    public static void addTetromino(Tetromino tetromino) {
        tetrominos.put(tetromino.getShape(), tetromino);
    }

    public static Tetromino getTetrominoByShape(Shape shape) {
        if (!tetrominos.containsKey(shape)) {
            throw new IllegalArgumentException();
        }
        return tetrominos.get(shape).copy();
    }

    public static Tetromino getNextTetromino() {
        if (pool.isEmpty()) {
            initPool();
        }
        return pool.removeFirst().copy();
    }

    private static void initPool() {
        List<Tetromino> tmp = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tmp.addAll(tetrominos.values()
                .stream()
                .map(Tetromino::copy)
                .collect(Collectors.toList()));
            Collections.shuffle(tmp);
        }
        Collections.shuffle(tmp);
        pool.addAll(tmp);
    }

    public static Tetromino peekNextTetromino() {
        if (pool.isEmpty()) {
            initPool();
        }
        return pool.getFirst().copy();
    }
}
