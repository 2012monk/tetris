package tetris.window;

import java.util.ArrayList;
import java.util.List;
import tetris.console.Console;

public class TextArea extends ComponentImpl {

    private static final int EMPTY = 0;
    private TextAlign textAlign = TextAlign.CENTER;
    private StringBuilder stringBuilder = new StringBuilder();

    public void writeString(String str) {
        this.stringBuilder.append(str);
    }

    public void print() {
        String str = stringBuilder.toString();
        if (!str.contains("\r") && !str.contains("\n") && str.length() <= getMaxWidth()) {
            printLine(getHorizontalPadding(str), 0, str);
            return;
        }
        List<String> clippedString = clipString(str, getMaxWidth());
        for (int i = 0; i < clippedString.size(); i++) {
            printLine(getHorizontalPadding(clippedString.get(i)), i, clippedString.get(i));
        }
    }

    private int getHorizontalPadding(String str) {
        int maxWidth = getMaxWidth();
        if (textAlign == TextAlign.START || str.length() >= maxWidth) {
            return 0;
        }
        if (textAlign == TextAlign.CENTER) {
            return (maxWidth - str.length()) / 2;
        }
        if (textAlign == TextAlign.END) {
            return maxWidth - str.length();
        }
        return 0;
    }

    private List<String> clipString(String str, int maxLength) {
        List<String> listString = new ArrayList<>();

        String[] brokeString = str.replaceAll("\r", "").split("\n");
        for (String s : brokeString) {
            if (s.length() <= maxLength) {
                listString.add(s);
                continue;
            }
            listString.addAll(splitByLength(str, maxLength));
        }
        return listString;
    }

    private List<String> splitByLength(String str, int maxLength) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < str.length(); i += maxLength) {
            result.add(str.substring(i, maxLength));
        }
        return result;
    }

    private void printLine(int x, int y, String str) {
        int startX = x + getCurrentX();
        int startY = y + getCurrentY();
        if (str.length() > x + getMaxWidth()) {
            str = str.substring(0, x + getMaxWidth());
        }

        Console.drawString(startX, startY, str);
    }

    @Override
    public void update() {
        if (stringBuilder.length() == EMPTY) {
            return;
        }
        print();
    }

    public enum TextAlign {
        START, END, CENTER
    }
}
