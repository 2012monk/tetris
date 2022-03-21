package tetris.components;

import tetris.system.MessageBroker;
import tetris.system.Post;

public class ScoreBoard extends TextArea {

    private int score = 0;

    public ScoreBoard(int x, int y, int width, int height) {
        super(x, y, width, height, false);
        MessageBroker.subscribe(ScoreAlert.class, this);
        writeString("score\n" + this.score);
    }

    private void updateScore(int score) {
        this.score += score;
        clearString();
        writeString("score\n" + this.score);
    }

    @Override
    public <T extends Post<?>> void onMessage(T post) {
        if (!(post instanceof ScoreAlert)) {
            return;
        }
        updateScore(((ScoreAlert) post).getPayload());
    }
}
