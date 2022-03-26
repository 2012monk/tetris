package tetris.components;

import java.util.ArrayList;
import java.util.List;
import tetris.ComponentImpl;
import tetris.console.Console;

public class TextArea extends ComponentImpl {

    private static final int EMPTY = 0;
    private TextAlign verticalAlign = TextAlign.START;
    private TextAlign horizontalAlign = TextAlign.CENTER;
    private StringBuilder stringBuilder = new StringBuilder();

    public TextArea(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
    }

    public void writeString(String str) {
        this.stringBuilder.append(str);
    }

    @Override
    public void render() {
        if (!hasParent() || stringBuilder.length() == EMPTY) {
            return;
        }
        clear();
        print();
    }

    private void print() {
        String str = stringBuilder.toString();
        printClippedString(clipString(str, getInnerWidth()));
    }

    private void printClippedString(List<String> clippedString) {
        for (int i = 0; i < clippedString.size(); i++) {
            int x = i + getVerticalPadding(clippedString.size());
            int y = getHorizontalPadding(clippedString.get(i));
            printLine(x, y, clippedString.get(i));
        }
    }


    private List<String> clipString(String str, int maxLength) {
        List<String> listString = new ArrayList<>();
        String[] brokeString = str.replaceAll("\r", "").split("\n");
        for (String s : brokeString) {
            if (s.length() < maxLength) {
                listString.add(s);
                continue;
            }
            listString.addAll(splitByLength(str, maxLength));
        }
        return listString;
    }

    private List<String> splitByLength(String str, int maxLength) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < str.length() - maxLength - 1; i += maxLength) {
            result.add(str.substring(i, i + maxLength));
        }
        return result;
    }


    protected void clearString() {
        this.stringBuilder = new StringBuilder();
    }

    private void printLine(int x, int y, String str) {
        if (!isInsideBox(x, y, str)) {
            return;
        }
        Console.drawString(x + getInnerX(), y + getInnerY(), str);
    }

    public boolean isInsideBox(int x, int y, String str) {
        return true;
//        return isInsideSpace(x, y) && isInsideSpace(x, y + str.length() - 1);
    }

    private int getVerticalPadding(int number) {
        if (verticalAlign == TextAlign.START || number >= getInnerHeight()) {
            return 0;
        }
        if (verticalAlign == TextAlign.CENTER) {
            return Math.max(0, (getInnerHeight() - number) / 2);
        }
        if (verticalAlign == TextAlign.END) {
            return Math.max(0, (getInnerHeight() - number));
        }
        return 0;
    }

    private int getHorizontalPadding(String str) {
        int maxWidth = getInnerWidth();
        if (horizontalAlign == TextAlign.START || str.length() >= maxWidth) {
            return 0;
        }
        if (horizontalAlign == TextAlign.CENTER) {
            return Math.max(0, (maxWidth - str.length()) / 2);
        }
        if (horizontalAlign == TextAlign.END) {
            return Math.max(0, maxWidth - str.length());
        }
        return 0;
    }

    protected void setVerticalAlign(TextAlign align) {
        this.verticalAlign = align;
    }

    protected void setHorizontalAlign(TextAlign align) {
        this.horizontalAlign = align;
    }


    public enum TextAlign {
        START, END, CENTER
    }
}
