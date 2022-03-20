package tetris.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import tetris.components.Tetromino;

public enum GameKey {
    MOVE_DOWN(KeyCode.KEY_DOWN, Tetromino::moveDown),
    MOVE_UP(KeyCode.KEY_UP, Tetromino::rotate270),
    KEY_SPACE(KeyCode.KEY_SPACE, Tetromino::rotate270),
    MOVE_LEFT(KeyCode.KEY_LEFT, Tetromino::moveLeft),
    MOVE_RIGHT(KeyCode.KEY_RIGHT, Tetromino::moveRight);
    private static Map<KeyCode, GameKey> keys;

    static {
        keys = Arrays.stream(GameKey.values())
            .collect(Collectors.toMap(GameKey::getTrigger, Function.identity()));
    }

    private final Consumer<Tetromino> action;
    private final KeyCode trigger;

    GameKey(KeyCode key, Consumer<Tetromino> action) {
        this.action = action;
        this.trigger = key;
    }

    public static GameKey getGameKey(KeyCode keyCode) {
        return keys.get(keyCode);
//        return Optional.ofNullable(keys.get(keyCode));
    }

    public static boolean hasKey(KeyCode keyCode) {
        return keys.containsKey(keyCode);
    }

    private KeyCode getTrigger() {
        return trigger;
    }

    public void move(Tetromino t) {
        this.action.accept(t);
    }
}
