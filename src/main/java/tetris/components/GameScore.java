package tetris.components;

public class GameScore {

    private int score = 0;

    public int getScore() {
        return score;
    }

    public GameScore updateScore(int lineCount) {
        this.score += calculateScore(lineCount);
        return this;
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
