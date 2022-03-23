package tetris.components;

import tetris.constants.GameStatus;
import tetris.message.GameScoreMessage;
import tetris.message.GameStatusMessage;
import tetris.system.Post;

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

    @Override
    public <T extends Post<?>> void onMessage(T post) {
        if (post instanceof GameScoreMessage) {
            printScore(((GameScoreMessage) post).getPayload().getScore());
            return;
        }
        if (post instanceof GameStatusMessage) {
            GameStatus status = (GameStatus) post.getPayload();
            if (status == GameStatus.PAUSE || status == GameStatus.RESUME) {
                return;
            }
            update();
        }
    }

    private void printScore(int score) {
        clearString();
        writeString(MESSAGE + score);
        update();
    }
}
