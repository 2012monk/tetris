package tetris.components;

import tetris.console.Console;
import tetris.constants.Char;
import tetris.constants.SpecialKeyCode;
import tetris.system.MenuSelector;

public class LeaderInputBoard extends ComponentImpl {

    private static final String MESSAGE = "Game Over Enter Name";
    private StringBuilder name = new StringBuilder();


    public LeaderInputBoard() {
        this(0, 0, 20, 8);
    }

    public LeaderInputBoard(int x, int y, int width, int height) {
        super(x, y, width, height, true);
    }

    @Override
    public void handleKey(Char chr) {
        if (chr.is(SpecialKeyCode.KEY_ENTER)) {
            end();
            return;
        }
        if (chr.is(SpecialKeyCode.KEY_BACK_SPACE)) {
            erase();
            return;
        }
        if (!chr.isSpecialKey() || chr.is(SpecialKeyCode.KEY_SPACE)) {
            insert(chr.getChar());
        }
    }

    private void end() {
        // save high score
        // back to main menu
//        WindowPoolManager.shutDown();
        name = new StringBuilder();
        MenuSelector.mainMenu();
    }

    private void insert(char chr) {
        if (name.length() >= getInnerWidth() - 1) {
            return;
        }
        name.append(chr);
        update();
    }

    private void erase() {
        if (name.length() > 0) {
            name.deleteCharAt(name.length() - 1);
        }
        update();
    }

    @Override
    public void update() {
        if (!hasParent()) {
            return;
        }

        clear();
        Console.drawString(getInnerX(), getStartY(MESSAGE), MESSAGE);
        if (name.length() > 0) {
            Console.drawString(getPointerX(), getStartY(name.toString()), name.toString());
        }
        Console.drawChar(getPointerX(), getPointerY(), '_', bg, fg);
    }

    private int getStartY(String str) {
        return getInnerY() + (getInnerWidth() - str.length()) / 2;
    }

    private int getPointerY() {
        return getStartY(name.toString()) + name.length();
    }

    private int getPointerX() {
        return getInnerX() + 1;
    }
}
