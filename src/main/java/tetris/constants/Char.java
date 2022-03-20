package tetris.constants;

public class Char {

    private int number;

    public Char(int number) {
        this.number = number;
    }

    public Char(SpecialKeyCode key) {
        this.number = key.getNumber();
    }

    public char getChar() {
        return (char) number;
    }

    public int getNumber() {
        return number;
    }

    public boolean isSpecialKey() {
        return SpecialKeyCode.hasKey(number);
    }

    public SpecialKeyCode getSpecialKey() {
        if (SpecialKeyCode.hasKey(number)) {
            return SpecialKeyCode.getKeyCode(number);
        }
        throw new IllegalArgumentException();
    }

    public boolean is(SpecialKeyCode key) {
        return key.getNumber() == number;
    }

    public boolean is(char s) {
        return s == getChar();
    }
}
