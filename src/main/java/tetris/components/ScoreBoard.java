package tetris.components;

import tetris.constants.GameStatus;
import tetris.message.GameStatusMessage;
import tetris.message.ScoreAlert;
import tetris.system.Post;

public class ScoreBoard extends TextArea {

    private static final String MESSAGE = "SCORE\n";
    private int score = 0;

    public ScoreBoard(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        subscribe(ScoreAlert.class);
        subscribe(GameStatusMessage.class);
        printScore();
    }

    private void printScore() {
        clearString();
        writeString(MESSAGE + this.score);
    }

    private void updateScore(int score) {
        this.score += score;
        printScore();
    }

    @Override
    public <T extends Post<?>> void onMessage(T post) {
        if (post instanceof ScoreAlert) {
            updateScore(((ScoreAlert) post).getPayload());
            return;
        }
        if (post instanceof GameStatusMessage) {
            GameStatus status = (GameStatus) post.getPayload();
            if (status == GameStatus.PAUSE || status == GameStatus.RESUME) {
                return;
            }
            initScore();
        }
    }

    private void initScore() {
        this.score = 0;
        printScore();
    }
}
