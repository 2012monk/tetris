package tetris.console;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import tetris.Spatial;
import tetris.constants.Color;

public class Console {

    private static final String LIBRARY_NAME = "libConsole";
    private static final String ERR_NO_LIBRARY = "no library";
    private static final Color DEFAULT_FG = Color.WHITE;
    private static final Color DEFAULT_BG = Color.BLACK;
    private static final char DEFAULT_CLEAR_UNIT = ' ';

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

    public static void drawBorder(Spatial space) {
        drawBorder(space.getAbsoluteX(), space.getAbsoluteY(), space.getWidth(), space.getHeight(),
            space.getFg().getNumber(), space.getBg().getNumber()
        );
    }

    public static void clearArea(Spatial space) {
        clearArea(space.getInnerX(), space.getInnerY(),
            space.getInnerWidth(), space.getInnerHeight(), DEFAULT_CLEAR_UNIT,
            space.getFg(), space.getBg());
    }

    public static void clearArea(int x, int y, int width, int height, char chr, Color fg,
        Color bg) {
        clearArea(x, y, width, height, chr, fg.getNumber(), bg.getNumber());
    }

    public static void setForeGroundColor(Color color) {

    }

    public static void setBackGroundColor(Color color) {

    }

    public static void setColorPaint(Color foreGround, Color backGround) {

    }

    public static void drawChar(int x, int y, char chr, Color fg, Color bg) {
        drawChar(x, y, chr, fg.getNumber(), bg.getNumber());
    }

    public static void drawChar(int x, int y, char chr) {
        drawChar(x, y, chr, DEFAULT_FG, DEFAULT_BG);
    }

    public static void drawString(int x, int y, String text) {
        drawString(x, y, text, DEFAULT_FG.getNumber(), DEFAULT_BG.getNumber());
    }

    public static void drawString(int x, int y, String str, Color fg, Color bg) {
        drawString(x, y, str, fg.getNumber(), bg.getNumber());
    }

    public static void initConsole() {
        System.load(getLibraryPath());
        init();
    }

    private static native int setColorPair(int foreGround, int backGround);

    public static native int getScreenWidth();

    public static native int getScreenHeight();

    public static synchronized native int readBytes();

    private static native void drawChar(int x, int y, char chr, int fg, int bg);

    private static native void drawBorder(int x, int y, int width, int height, int fg,
        int bg);

    private static native void drawString(int x, int y, String text, int fg, int bg);

    private static native void clearArea(int x, int y, int width, int height, char chr, int fg,
        int bg);

    public static native void startDraw();

    public static native void endDraw();

    public static native void clearLine(int x);

    public static native void clearScreen();

    private static native void init();

    public static native void shutdown();

    public static native void refresh();
}
