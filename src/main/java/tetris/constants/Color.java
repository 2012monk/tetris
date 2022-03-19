package tetris.constants;

public enum Color {

    BLACK(0),
    RED(1),
    GREEN(2),
    YELLOW(3),
    BLUE(4),
    MAGENTA(5),
    CYAN(6),
    WHITE(7);

    private final int number;

    Color(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public int countOfColors() {
        return values().length;
    }
}
