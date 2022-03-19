package tetris.components;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import tetris.constants.Shape;

public class TetrominoRepository {

    private static final Map<Shape, Tetromino> tetrominos = new HashMap<>();
    private static Iterator<Tetromino> pool;

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
        if (pool == null || !pool.hasNext()) {
            pool = tetrominos.values().iterator();
        }
        return pool.next().copy();
    }
}
