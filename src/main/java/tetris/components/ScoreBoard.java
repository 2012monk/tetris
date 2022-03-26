package tetris.components;

import tetris.annotations.OnMessage;
import tetris.constants.GameStatus;
import tetris.message.GameScoreMessage;
import tetris.message.GameStatusMessage;

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
        if (post != null) {
            printScore(post.getPayload().getScore());
        }
    }

    @OnMessage
    public void handleStatus(GameStatusMessage post) {
        if (post != null) {
            GameStatus status = post.getPayload();
            if (status == GameStatus.START) {
                render();
            }
        }
    }

    private void printScore(int score) {
        clearString();
        writeString(MESSAGE + score);
        render();
    }
}
