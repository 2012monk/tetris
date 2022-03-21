package tetris.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import tetris.components.Tetromino;

public enum GameKey {
    KEY_DOWN(SpecialKeyCode.KEY_DOWN, Tetromino::printDown),
    KEY_UP(SpecialKeyCode.KEY_UP, Tetromino::rotate270),
    KEY_SPACE(SpecialKeyCode.KEY_SPACE, Tetromino::rotate270),
    KEY_LEFT(SpecialKeyCode.KEY_LEFT, Tetromino::printLeft),
    KEY_RIGHT(SpecialKeyCode.KEY_RIGHT, Tetromino::printRight),
    KET_H('h', Tetromino::printLeft),
    KEY_L('l', Tetromino::printRight),
    KEY_J('j', Tetromino::printDown),
    KEY_K('k', Tetromino::rotate270);
    private static final Map<Char, GameKey> keys;

    static {
        keys = Arrays.stream(GameKey.values())
            .collect(Collectors.toMap(GameKey::getTrigger, Function.identity()));
    }

    private final Consumer<Tetromino> action;
    private final Char trigger;

    GameKey(SpecialKeyCode key, Consumer<Tetromino> action) {
        this.action = action;
        this.trigger = new Char(key);
    }

    GameKey(char key, Consumer<Tetromino> action) {
        this.action = action;
        this.trigger = new Char(key);
    }

    public static GameKey getGameKey(Char keyCode) {
        return keys.get(keyCode);
    }

    public static boolean hasKey(Char keyCode) {
        return keys.containsKey(keyCode);
    }

    private Char getTrigger() {
        return trigger;
    }

    public void move(Tetromino t) {
        this.action.accept(t);
    }
}
