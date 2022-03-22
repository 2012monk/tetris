package tetris.constants;

public class TetrominoPosition {

    private TetrominoStatus status;
    private TetrominoPosition rightPosition;
    private TetrominoPosition leftPosition;

    public TetrominoPosition(TetrominoStatus status) {
        this.status = status;
    }

    public TetrominoPosition getLeftPosition() {
        return leftPosition;
    }

    public TetrominoStatus getStatus() {
        return status;
    }

    public TetrominoPosition getRightPosition() {
        return rightPosition;
    }

    public void setRightPosition(TetrominoPosition rightPosition) {
        this.rightPosition = rightPosition;
    }

    public void setLeftPosition(TetrominoPosition leftPosition) {
        this.leftPosition = leftPosition;
    }
}
