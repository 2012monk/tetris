package tetris.helper;

public class Counter {

    public static void waitMill(int mill) {
        long start = System.currentTimeMillis();
        long current = start;
        while (current < start + mill) {
            current = System.currentTimeMillis();
        }
    }
}
