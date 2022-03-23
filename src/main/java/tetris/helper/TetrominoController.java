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
        copied = rotate(copied, WallKickDataRepository.getLeftRotateData(block));
        if (board.isCollide(copied)) {
            return block;
        }
        return copied;
    }

    public Tetromino rotateRight(Tetromino block) {
        Tetromino copied = block.copy();
        copied.rotateRight();
        copied = rotate(copied, WallKickDataRepository.getLeftRotateData(block));
        if (board.isCollide(copied)) {
            return block;
        }
        return copied;
    }

    private Tetromino rotate(Tetromino block, WallKickData data) {
        if (!board.isCollide(block) || !data.hasNext()) {
            return block;
        }
        data.correctPosition(block);
        if (board.isCollide(block)) {
            data.reversePosition(block);
        }
        return rotate(block, data.next());
    }
}
