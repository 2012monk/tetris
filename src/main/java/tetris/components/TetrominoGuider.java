package tetris.components;

import java.util.NoSuchElementException;
import tetris.Component;
import tetris.ComponentContainer;

public class TetrominoGuider extends ComponentContainer<Tetromino> {

    private final TetrisBoardPanel board;

    public TetrominoGuider(TetrisBoardPanel board) {
        super(board.getRelativeX(), board.getRelativeY(), board.getWidth(), board.getHeight(),
            false);
        setParent(board);
        this.board = board;
    }

    public void guideBlock(Tetromino block) {
        Tetromino tmp = block.getGuideBlock();
        while (!board.isCollide(tmp)) {
            tmp.moveDown();
        }
        tmp.moveUp();
        addGuiderBlock(tmp);
        render();
    }

    private void addGuiderBlock(Tetromino block) {
        this.components.clear();
        this.components.add(block);
    }

    @Override
    public void clear() {
        this.components.forEach(Component::clear);
    }

    public Tetromino getGuideBlock() {
        return this.components.stream().findFirst().orElseThrow(NoSuchElementException::new);
    }
}

