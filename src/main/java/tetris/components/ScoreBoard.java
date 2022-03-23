package tetris.components;

import tetris.constants.GameStatus;
import tetris.message.GameStatusMessage;
import tetris.message.ScoreAlert;
import tetris.system.Post;

public class ScoreBoard extends TextArea {

    private static final String MESSAGE = "SCORE\n";
    private int score = 0;

    public ScoreBoard(int x, int y, int width, int height) {
        this(x, y, width, height, false);
    }

    public ScoreBoard(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        subscribe(ScoreAlert.class);
        subscribe(GameStatusMessage.class);
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

    private void printScore() {
        clearString();
        writeString(MESSAGE + this.score);
        update();
    }

    private void updateScore(int score) {
        this.score += score;
        printScore();
    }

    private void initScore() {
        this.score = 0;
        printScore();
    }
}
