package tetris.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import tetris.components.Tetromino;

public enum GameKey {
    KEY_DOWN(SpecialKeyCode.KEY_DOWN, Tetromino::moveDown, Tetromino::moveUp),
    KEY_UP(SpecialKeyCode.KEY_UP, Tetromino::rotateLeft, Tetromino::rotateRight),
    KEY_SPACE(SpecialKeyCode.KEY_SPACE, Tetromino::rotateLeft, Tetromino::rotateRight),
    KEY_LEFT(SpecialKeyCode.KEY_LEFT, Tetromino::moveLeft, Tetromino::moveRight),
    KEY_RIGHT(SpecialKeyCode.KEY_RIGHT, Tetromino::moveRight, Tetromino::moveLeft),
    KET_H('h', Tetromino::moveLeft, Tetromino::moveRight),
    KEY_L('l', Tetromino::moveRight, Tetromino::moveLeft),
    KEY_J('j', Tetromino::moveDown, Tetromino::moveUp),
    KEY_K('k', Tetromino::rotateLeft, Tetromino::rotateRight);
    private static final Map<Char, GameKey> keys;

    static {
        keys = Arrays.stream(GameKey.values())
            .collect(Collectors.toMap(GameKey::getTrigger, Function.identity()));
    }

    private final Consumer<Tetromino> action;
    private final Consumer<Tetromino> reverseAction;
    private final Char trigger;

    GameKey(SpecialKeyCode key, Consumer<Tetromino> action, Consumer<Tetromino> reverseAction) {
        this.action = action;
        this.trigger = new Char(key);
        this.reverseAction = reverseAction;
    }

    GameKey(char key, Consumer<Tetromino> action, Consumer<Tetromino> reverseAction) {
        this.action = action;
        this.trigger = new Char(key);
        this.reverseAction = reverseAction;
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

    public void simulate(Tetromino block) {
        this.action.accept(block);
    }

    public void reverse(Tetromino block) {
        this.reverseAction.accept(block);
    }
}
