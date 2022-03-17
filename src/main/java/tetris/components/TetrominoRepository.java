package tetris.components;

import java.util.HashMap;
import java.util.Map;

public class TetrominoRepository {

    private static final Map<Shape, Tetromino> tetrominos = new HashMap<>();

    public static void addTetromino(Tetromino tetromino) {
        tetrominos.put(tetromino.getShape(), tetromino);
    }

    public static Tetromino getTetrominoByShape(Shape shape) {
        if (!tetrominos.containsKey(shape)) {
            throw new IllegalArgumentException();
        }
        return tetrominos.get(shape).copy();
    }
}
