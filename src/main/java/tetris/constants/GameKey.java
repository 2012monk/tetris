package tetris.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import tetris.components.Tetromino;

public enum GameKey {
    MOVE_DOWN(SpecialKeyCode.KEY_DOWN, Tetromino::printDown),
    MOVE_UP(SpecialKeyCode.KEY_UP, Tetromino::rotate270),
    KEY_SPACE(SpecialKeyCode.KEY_SPACE, Tetromino::rotate270),
    MOVE_LEFT(SpecialKeyCode.KEY_LEFT, Tetromino::printLeft),
    MOVE_RIGHT(SpecialKeyCode.KEY_RIGHT, Tetromino::printRight);
    private static Map<SpecialKeyCode, GameKey> keys;

    static {
        keys = Arrays.stream(GameKey.values())
            .collect(Collectors.toMap(GameKey::getTrigger, Function.identity()));
    }

    private final Consumer<Tetromino> action;
    private final SpecialKeyCode trigger;

    GameKey(SpecialKeyCode key, Consumer<Tetromino> action) {
        this.action = action;
        this.trigger = key;
    }

    public static GameKey getGameKey(Char keyCode) {
        return keys.get(keyCode.getSpecialKey());
    }

    public static boolean hasKey(Char keyCode) {
        if (keyCode.isSpecialKey()) {
            return keys.containsKey(keyCode.getSpecialKey());
        }
        return false;
    }

    private SpecialKeyCode getTrigger() {
        return trigger;
    }

    public void move(Tetromino t) {
        this.action.accept(t);
    }
}
