package tetris.console;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import tetris.window.Rectangle;

public class Console {

    private static final String LIBRARY_NAME = "libConsole";
    private static final String ERR_NO_LIBRARY = "no library";

    static {
        System.load(getLibraryPath());
        init();
    }

    public static String getLibraryPath() {
        try {
            String currentDir = new File(new File(".").getAbsolutePath()).getCanonicalPath();
            File file = new File(currentDir, "lib");
            if (file.listFiles() == null) {
                throw new RuntimeException(ERR_NO_LIBRARY);
            }
            File library = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(f -> f.getName().startsWith(LIBRARY_NAME))
                .findAny()
                .orElseThrow(() -> new RuntimeException(ERR_NO_LIBRARY));
            return library.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(ERR_NO_LIBRARY);
        }
    }

    public static void test() {
        drawChar(0, 0, 'a');
        drawChar(1, 0, 'b');
        drawChar(2, 0, 'c');
        drawChar(3, 0, 'd');
    }

    public static void addCell(int x, int y) {
        drawChar(x, y, ' ');
    }

    public static void drawBorder(Rectangle rectangle) {
        drawBorder(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public static void clearArea(Rectangle rectangle) {
        clearArea(rectangle.getX() + 1, rectangle.getY() + 1, rectangle.getWidth() - 1, rectangle.getHeight() - 1);
    }

    private static native int getScreenWidth();

    private static native int getScreenHeight();

    public static native int readBytes();

    public static native void drawChar(int x, int y, char chr);

    private static native void drawBorder(int x, int y, int width, int height);

    public static native void drawString(int x, int y, String text);

    private static native void clearArea(int x, int y, int width, int height);

    private static native void printString(int x, int y, int length, String text);

    private static native void clearLine(int x, int y);

    private static native void clearBox(int x, int y);

    private static native void clearScreen();

    private static native void init();

    public static native void shutdown();

    public static native void refresh();

}
