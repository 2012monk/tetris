package tetris.components;

public class TetrominoGuider extends ComponentContainer<Tetromino> {

    private TetrisBoard board;

    public TetrominoGuider(TetrisBoard board) {
        super(board.getRelativeX(), board.getRelativeY(), board.getWidth(), board.getHeight(),
            false);
        setParent(board);
        this.board = board;
    }

    public void guideBlock(Tetromino block) {
        Tetromino tmp = block.getGuiderBlock();
        while (!board.isCollide(tmp)) {
            tmp.moveDown();
        }
        tmp.moveUp();
        addGuiderBlock(tmp);
        update();
    }

    private void addGuiderBlock(Tetromino block) {
        this.components.clear();
        this.components.add(block);
    }

    @Override
    public void clear() {
        this.components.forEach(Component::clear);
    }
}
