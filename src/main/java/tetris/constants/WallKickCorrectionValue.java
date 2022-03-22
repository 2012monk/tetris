package tetris.constants;

import tetris.components.Tetromino;
import tetris.window.Task;

public class WallKickCorrectionValue {

    private final int XCorrectionValue;
    private final int YCorrectionValue;

    public WallKickCorrectionValue(int xCorrectionValue, int yCorrectionValue) {
        XCorrectionValue = xCorrectionValue;
        YCorrectionValue = yCorrectionValue;
    }

    public void correctPosition(Tetromino block) {
        verticalMove(block, XCorrectionValue);
        horizontalMove(block, YCorrectionValue);
    }

    public void reversePosition(Tetromino block) {
        verticalMove(block, -XCorrectionValue);
        horizontalMove(block, -YCorrectionValue);
    }

    private void verticalMove(Tetromino block, int correctionValue) {
        if (correctionValue < 0) {
            move(block::moveUp, Math.abs(correctionValue));
            return;
        }
        move(block::moveDown, correctionValue);
    }

    private void horizontalMove(Tetromino block, int correctionValue) {
        if (correctionValue < 0) {
            move(block::moveLeft, Math.abs(correctionValue));
            return;
        }
        move(block::moveRight, correctionValue);
    }

    private void move(Task task, int value) {
        for (int i = 0; i < value; i++) {
            task.action();
        }
    }
}
