package tetris.controller;

import tetris.components.GameScore;
import tetris.components.Tetromino;
import tetris.constants.GameKey;
import tetris.exception.BlockCollideException;
import tetris.exception.EndOfGameException;
import tetris.exception.EndOfMoveException;
import tetris.message.BoardUpdateMessage;
import tetris.message.CurrentBlockMessage;
import tetris.message.NextBlockMessage;
import tetris.message.Post;
import tetris.gameobject.TetrisBoard;
import tetris.repository.TetrominoRepository;
import tetris.system.MessageBroker;

public class TetrisGameController {

    private final TetrisBoard board;
    private Tetromino currentBlock;
    private Tetromino guideBlock;
    private final GameScore score;

    public TetrisGameController(TetrisBoard board) {
        this.board = board;
        this.score = new GameScore();
    }

    public void resetGame() {
        score.resetScore();
        board.resetBoard();
        try {
            initBlock();
        } catch (EndOfGameException ignore) {
        }
    }

    public void move(GameKey key) throws EndOfGameException {
        try {
            currentBlock.move(board, key);
            resetGuideBlock();
            updateState();
        } catch (EndOfMoveException e) {
            calculateScore();
            initBlock();
        }
    }

    private void updateState() {
        board.updateTetromino(currentBlock);
        board.updateGuideTetromino(guideBlock);
        MessageBroker.publish(new BoardUpdateMessage(board));
    }

    private void initBlock() throws EndOfGameException {
        try {
            currentBlock = TetrominoRepository.getNextTetromino();
            currentBlock.moveToStartingPosition(board);
            resetGuideBlock();
            updateState();
        } catch (BlockCollideException e) {
            throw new EndOfGameException();
        }
        publishMessage(new CurrentBlockMessage(currentBlock));
        publishMessage(new NextBlockMessage(TetrominoRepository.peekNextTetromino()));
    }

    private void publishMessage(Post<?> message) {
        MessageBroker.publish(message);
    }

    private void calculateScore() {
        int deletedRowCount = board.stackTetromino(currentBlock);
        score.updateScore(deletedRowCount);
    }

    private void resetGuideBlock() {
        guideBlock = currentBlock.getGuideBlock();
        guideBlock.hardDrop(board);
    }
}
