package tetris.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SpecialKeyCode {
    KEY_LEFT(260),
    KEY_RIGHT(261),
    KEY_DOWN(258),
    KEY_UP(259),
    KEY_SPACE(32),
    KEY_ESC(27),
    KEY_ENTER(10);

    private static final Map<Integer, SpecialKeyCode> codes;

    static {
        codes = Arrays.stream(SpecialKeyCode.values())
            .collect(Collectors.toMap(SpecialKeyCode::getNumber, Function.identity()));
    }

    private final int number;

    SpecialKeyCode(int number) {
        this.number = number;
    }

    public static SpecialKeyCode getKeyCode(int number) {
        return codes.get(number);
    }

    public static boolean hasKey(int number) {
        return codes.containsKey(number);
    }

    public int getNumber() {
        return this.number;
    }
}
