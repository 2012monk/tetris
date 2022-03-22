package tetris.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import tetris.components.Tetromino;

public enum GameKey {
    KEY_DOWN(SpecialKeyCode.KEY_DOWN, Tetromino::printDown, Tetromino::moveDown),
    KEY_UP(SpecialKeyCode.KEY_UP, Tetromino::printRotateLeft, Tetromino::rotateLeft),
    KEY_SPACE(SpecialKeyCode.KEY_SPACE, Tetromino::printRotateLeft, Tetromino::rotateLeft),
    KEY_LEFT(SpecialKeyCode.KEY_LEFT, Tetromino::printLeft, Tetromino::moveLeft),
    KEY_RIGHT(SpecialKeyCode.KEY_RIGHT, Tetromino::printRight, Tetromino::moveRight),
    KET_H('h', Tetromino::printLeft, Tetromino::moveLeft),
    KEY_L('l', Tetromino::printRight, Tetromino::moveRight),
    KEY_J('j', Tetromino::printDown, Tetromino::moveDown),
    KEY_K('k', Tetromino::printRotateLeft, Tetromino::rotateLeft);
    private static final Map<Char, GameKey> keys;

    static {
        keys = Arrays.stream(GameKey.values())
            .collect(Collectors.toMap(GameKey::getTrigger, Function.identity()));
    }

    private final Consumer<Tetromino> action;
    private final Consumer<Tetromino> simulateAction;
    private final Char trigger;

    GameKey(SpecialKeyCode key, Consumer<Tetromino> action, Consumer<Tetromino> simulateAction) {
        this.action = action;
        this.trigger = new Char(key);
        this.simulateAction = simulateAction;
    }

    GameKey(char key, Consumer<Tetromino> action, Consumer<Tetromino> simulateAction) {
        this.action = action;
        this.trigger = new Char(key);
        this.simulateAction = simulateAction;
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

    public void simulate(Tetromino block) {
        this.simulateAction.accept(block);
    }
}
