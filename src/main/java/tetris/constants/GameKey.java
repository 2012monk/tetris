package tetris.constants;

import java.util.function.Consumer;
import tetris.components.Tetromino;

public enum GameKey {
    MOVE_DOWN(Tetromino::moveDown),
    MOVE_UP(Tetromino::rotate270),
    KEY_SPACE(Tetromino::rotate270),
    MOVE_LEFT(Tetromino::moveLeft),
    MOVE_RIGHT(Tetromino::moveRight);
    private final Consumer<Tetromino> action;

    GameKey(Consumer<Tetromino> action) {
        this.action = action;
    }

    public void move(Tetromino t) {
        this.action.accept(t);
    }
}
