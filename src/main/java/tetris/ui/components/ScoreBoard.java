package tetris.ui.components;

import tetris.ui.annotations.OnMessage;
import tetris.ui.message.GameScoreMessage;
import tetris.ui.message.GameStatusMessage;

public class ScoreBoard extends TextArea {

    private static final String MESSAGE = "SCORE\n";

    public ScoreBoard(int x, int y, int width, int height) {
        this(x, y, width, height, false);
    }

    public ScoreBoard(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        subscribe(GameScoreMessage.class);
        subscribe(GameStatusMessage.class);
        writeString(MESSAGE + 0);
    }

    @OnMessage
    public void onMessage(GameScoreMessage post) {
        printScore(post.getPayload().getScore());
    }

    private void printScore(int score) {
        clearString();
        writeString(MESSAGE + score);
        render();
    }
}
