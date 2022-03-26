package tetris.components;

import tetris.message.GameScoreMessage;
import tetris.system.MessageBroker;

public class GameScore {

    private int score = 0;

    public int getScore() {
        return score;
    }

    public void updateScore(int lineCount) {
        if (lineCount == 0) {
            return;
        }
        this.score += calculateScore(lineCount);
        MessageBroker.publish(new GameScoreMessage(this));
    }

    public void resetScore() {
        this.score = 0;
    }

    private int calculateScore(int lineCount) {
        int mul = 1000;
        if (lineCount > 2) {
            mul += 500;
        }
        if (lineCount > 3) {
            mul += 1000;
        }
        return lineCount * mul;
    }
}
