package tetris.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum KeyCode {
    KEY_LEFT(260),
    KEY_RIGHT(261),
    KEY_DOWN(258),
    KEY_UP(259),
    KEY_SPACE(32),
    KEY_ESC(27);

    private static final Map<Integer, KeyCode> codes;

    static {
        codes = Arrays.stream(KeyCode.values())
            .collect(Collectors.toMap(KeyCode::getNumber, Function.identity()));
    }

    private final int number;

    KeyCode(int number) {
        this.number = number;
    }

    public static KeyCode getKeyCode(int number) {
        return codes.get(number);
    }

    public int getNumber() {
        return this.number;
    }
}
