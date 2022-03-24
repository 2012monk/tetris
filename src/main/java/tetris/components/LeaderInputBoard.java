package tetris.components;

import tetris.ComponentImpl;
import tetris.console.Console;
import tetris.constants.Char;
import tetris.constants.Color;
import tetris.constants.SpecialKeyCode;
import tetris.helper.LeaderBoardManager;
import tetris.message.GameScoreMessage;
import tetris.message.Post;
import tetris.system.MenuSelector;

public class LeaderInputBoard extends ComponentImpl {

    private static final String GAME_OVER_MESSAGE = "GAME OVER";
    private static final String HELP_MESSAGE = "Enter your name";

    private static final Color GAME_OVER_COLOR = Color.MAGENTA;
    private GameScore currentScore;
    private StringBuilder name = new StringBuilder();


    public LeaderInputBoard() {
        this(0, 0, 20, 8);
    }

    public LeaderInputBoard(int x, int y, int width, int height) {
        super(x, y, width, height, true);
        subscribe(GameScoreMessage.class);
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
        int score = 0;
        if (currentScore != null) {
            score = currentScore.getScore();
        }
        LeaderBoardManager.saveScore(name.toString(), score);
        name = new StringBuilder();
        MenuSelector.leaderBoard();
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
    public <T extends Post<?>> void onMessage(T post) {
        if (post instanceof GameScoreMessage) {
            currentScore = (GameScore) post.getPayload();
        }
    }

    @Override
    public void update() {
        if (!hasParent()) {
            return;
        }
        clear();
        Console.drawString(getInnerX(), getStartY(GAME_OVER_MESSAGE), GAME_OVER_MESSAGE,
            GAME_OVER_COLOR, getBg());
        Console.drawString(getInnerX() + 1, getStartY(HELP_MESSAGE), HELP_MESSAGE);
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
        return getInnerX() + 2;
    }
}
