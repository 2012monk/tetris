package tetris.components;

import java.util.ArrayList;
import java.util.List;
import tetris.console.Console;

public class TextArea extends ComponentImpl {

    private static final int EMPTY = 0;
    private TextAlign textAlign = TextAlign.CENTER;
    private StringBuilder stringBuilder = new StringBuilder();

    public TextArea(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
    }

    public void writeString(String str) {
        this.stringBuilder.append(str);
        update();
    }

    private void print() {
        String str = stringBuilder.toString();
        if (!str.contains("\r") && !str.contains("\n") && str.length() <= getInnerWidth()) {
            printLine(getVerticalPadding(1), str);
            return;
        }
        List<String> clippedString = clipString(str, getWidth());
        for (int i = 0; i < clippedString.size(); i++) {
            printLine(i + getVerticalPadding(clippedString.size()), clippedString.get(i));
        }
    }

    private int getVerticalPadding(int number) {
        if (textAlign == TextAlign.START || number >= getInnerHeight()) {
            return 0;
        }
        if (textAlign == TextAlign.CENTER) {
            return (getInnerHeight() - number) / 2;
        }
        if (textAlign == TextAlign.END) {
            return getInnerHeight() - number;
        }
        return 0;
    }

    private int getHorizontalPadding(String str) {
        int maxWidth = getInnerWidth();
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


    protected void clearString() {
        this.stringBuilder = new StringBuilder();
    }

    private void printLine(int x, String str) {
        if (x > getInnerHeight()) {
            return;
        }
        int startX = x + getInnerX();
        int startY = getHorizontalPadding(str) + getInnerY();
        if (str.length() > getInnerWidth()) {
            str = str.substring(0, getInnerWidth());
        }
        Console.drawString(startX, startY, str);
    }

    @Override
    public void update() {
        if (!hasParent() || stringBuilder.length() == EMPTY) {
            return;
        }
        clear();
        print();
    }

    @Override
    public void clear() {
        super.clear();
    }

    public enum TextAlign {
        START, END, CENTER
    }
}
