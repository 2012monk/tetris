package tetris.helper;

import tetris.components.TetrisBoard;
import tetris.components.Tetromino;
import tetris.constants.WallKickData;
import tetris.repository.WallKickDataRepository;

public class TetrominoController {

    private final TetrisBoard board;

    public TetrominoController(TetrisBoard board) {
        this.board = board;
    }

    public Tetromino rotateLeft(Tetromino block) {
        Tetromino copied = block.copy();
        copied.rotateLeft();
        if (board.isCollide(copied)) {
            return block;
        }
        return rotate(copied, WallKickDataRepository.getLeftRotateData(block));
    }

    public Tetromino rotateRight(Tetromino block) {
        Tetromino copied = block.copy();
        copied.rotateRight();
        if (board.isCollide(copied)) {
            return block;
        }
        return rotate(copied, WallKickDataRepository.getRightRotateData(block));
    }

    private Tetromino rotate(Tetromino copied, WallKickData data) {
        if (!board.isCollide(copied) || !data.hasNext()) {
            return copied;
        }
        data.correctPosition(copied);
        if (board.isCollide(copied)) {
            data.reversePosition(copied);
        }
        return rotate(copied, data.next());
    }
}
