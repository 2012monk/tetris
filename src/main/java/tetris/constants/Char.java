package tetris.constants;

public class Char {

    private int number;

    public Char(int number) {
        this.number = number;
    }

    public Char(char chr) {
        this.number = chr;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Char aChar = (Char) o;

        return number == aChar.number;
    }

    @Override
    public int hashCode() {
        return number;
    }
}
