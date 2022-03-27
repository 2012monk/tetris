package tetris.ui.components;

import java.util.ArrayList;
import java.util.List;
import tetris.ui.console.Console;

public class HelpMessage extends TextArea {

    private static final List<String[]> keyDescription = new ArrayList<String[]>() {{
        add(new String[]{"pause", "p"});
        add(new String[]{"restart", "r"});
        add(new String[]{"quit", "q"});
        add(new String[]{"down", "↓ j"});
        add(new String[]{"left", "← h"});
        add(new String[]{"right", "→ l"});
        add(new String[]{"rotate", "↑"});
        add(new String[]{"hard drop", "space"});
        add(new String[]{"mute", "m"});
    }};

    public HelpMessage(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        setHorizontalAlign(TextAlign.START);
    }

    @Override
    public void render() {
        if (!hasParent()) {
            return;
        }
        clear();
        print();
    }

    private void print() {
        int leftPadding = getLeftPadding();
        int rightPadding = getRightPadding();
        keyDescription.forEach(
            v -> {
                Console.drawString(getInnerX() + keyDescription.indexOf(v),
                    getInnerY() + leftPadding, v[0], fg, bg);
                Console.drawString(getInnerX() + keyDescription.indexOf(v),
                    getInnerY() + rightPadding, v[1], fg, bg);
            }
        );
    }

    private int getLeftPadding() {
        int width = getInnerWidth() / 4;
        int leftAlign = keyDescription
            .stream()
            .mapToInt(s -> s[0].length())
            .max().orElse(0) / 2;
        return width - leftAlign + 1;
    }

    private int getRightPadding() {
        int width = getInnerWidth() / 4;
        int rightAlign = keyDescription
            .stream()
            .mapToInt(s -> s[1].length())
            .max().orElse(0) / 2;
        return width - rightAlign + getInnerWidth() / 2 + 2;
    }
}
