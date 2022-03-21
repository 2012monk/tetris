package tetris.components;

import tetris.message.ScoreAlert;
import tetris.system.Post;

public class ScoreBoard extends TextArea {

    private static final String MESSAGE = "SCORE\n";
    private int score = 0;

    public ScoreBoard(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        subscribe(ScoreAlert.class);
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
        if (!(post instanceof ScoreAlert)) {
            return;
        }
        updateScore(((ScoreAlert) post).getPayload());
    }
}
