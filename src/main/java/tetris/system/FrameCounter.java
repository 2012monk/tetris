package tetris.system;

public class FrameCounter {


    public static void wait(int mill) {
        long start = System.currentTimeMillis();
        long current = start;
        while (current < start + mill) {
            current = System.currentTimeMillis();
        }
    }
}
