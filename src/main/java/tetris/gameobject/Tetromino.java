package tetris.gameobject;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import tetris.constants.BlockMovement;
import tetris.constants.TetrominoPosition;
import tetris.constants.TetrominoShape;
import tetris.constants.WallKickData;
import tetris.exception.BlockCollideException;
import tetris.exception.EndOfMoveException;
import tetris.exception.OutOfDataException;
import tetris.repository.PositionRepository;
import tetris.repository.WallKickDataRepository;
import tetris.ui.constants.Color;

public class Tetromino {

    private static final int DEFAULT_BLOCK_SIZE = 3;
    private static final int BASIS = 1;
    private static final char DEFAULT_CELL = ' ';
    private static final char GUIDER_CELL = '.';
    private final List<Cell> points = new ArrayList<>();
    private final char space;
    private final int blockSize;
    private final TetrominoShape shape;
    private final Color color;
    private TetrominoPosition position;
    private int x;
    private int y;

    public Tetromino(Color color, TetrominoShape shape, int blockSize) {
        this(0, 0, blockSize, color, shape);
    }

    public Tetromino(Color color, TetrominoShape shape) {
        this(color, shape, DEFAULT_BLOCK_SIZE);
    }

    public Tetromino(char space, int blockSize, TetrominoShape shape, TetrominoPosition position,
        int x, int y, Color color, List<Cell> cells) {
        this(x, y, blockSize, space, color, shape, position);
        cells.forEach(c -> addCell(c.getX(), c.getY()));
    }

    private Tetromino(int x, int y, int blockSize, Color bg, TetrominoShape shape) {
        this(x, y, blockSize, DEFAULT_CELL, bg, shape, PositionRepository.getInitialPosition());
    }

    private Tetromino(int x, int y, int blockSize, char space, Color color, TetrominoShape shape,
        TetrominoPosition position) {
        this.space = space;
        this.blockSize = blockSize;
        this.shape = shape;
        this.position = position;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /*
     * 270 degree rotate x,y => -y + centerCorrection, x
     */
    public void rotateLeft() {
        clearPoints().forEach(this::rotateLeft);
        this.position = position.getLeftPosition();
    }

    /**
     * 90 degree rotate
     */
    public void rotateRight() {
        clearPoints().forEach(this::rotateRight);
        this.position = position.getRightPosition();
    }

    private void rotateLeft(Cell point) {
        int rotatedY = point.getX();
        int rotatedX = blockSize - 1 - point.getY();
        addCell(rotatedX, rotatedY);
    }

    private void rotateRight(Cell point) {
        int rotatedX = point.getY();
        int rotatedY = blockSize - 1 - point.getX();
        addCell(rotatedX, rotatedY);
    }

    private List<Cell> clearPoints() {
        List<Cell> tmp = new ArrayList<>(this.points);
        this.points.clear();
        return tmp;
    }

    public void moveRight() {
        move(0, BASIS);
    }

    public void moveLeft() {
        move(0, -BASIS);
    }

    public void moveDown() {
        move(BASIS, 0);
    }

    public void moveUp() {
        move(-BASIS, 0);
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void addCell(int x, int y) {
        this.points.add(new Cell(x, y, getColor(), space));
    }

    public void moveToStartingPosition(TetrisBoard board) {
        moveToStartingPoint(board.getWidth() * 2);
        board.collisionTest(this);
    }

    private void moveToStartingPoint(int maxWidth) {
        this.x = -this.points.stream()
            .mapToInt(Cell::getX)
            .min()
            .orElseThrow(NoSuchElementException::new);
        alignCenter(maxWidth);
    }

    public void alignCenter(int maxWidth) {
        this.y = (maxWidth / 2 - blockSize) / 2;
    }

    private int calculateSize(Supplier<IntStream> streamSupplier) {
        if (points.isEmpty()) {
            return 0;
        }
        int min = streamSupplier.get().min().orElse(0);
        int max = streamSupplier.get().max().orElse(0);
        return max - min + 1;
    }

    public List<Cell> getCalculatedCells() {
        return this.points.stream()
            .map(p -> new Cell(x + p.getX(), y + p.getY(), getColor(), space))
            .collect(Collectors.toList());
    }

    public void move(TetrisBoard board, BlockMovement key) throws EndOfMoveException {
        if (key == BlockMovement.KEY_UP) {
            rotateLeft(board);
            return;
        }
        if (key == BlockMovement.KEY_SPACE) {
            hardDrop(board);
            throw new EndOfMoveException();
        }
        moveCoordinate(board, key);
    }

    private void moveCoordinate(TetrisBoard board, BlockMovement key) throws EndOfMoveException {
        key.tarnsform(this);
        try {
            board.collisionTest(this);
        } catch (BlockCollideException e) {
            key.reverse(this);
            if (key == BlockMovement.KEY_DOWN) {
                throw new EndOfMoveException();
            }
        }
    }

    public void rotateLeft(TetrisBoard board) {
        rotateLeft();
        try {
            rotate(this, board, WallKickDataRepository.getLeftRotateData(this));
        } catch (OutOfDataException e) {
            rotateRight();
        }
    }

    private void rotate(Tetromino block, TetrisBoard board, WallKickData data) {
        try {
            data.correctPosition(block);
            board.collisionTest(block);
        } catch (BlockCollideException e) {
            data.reversePosition(block);
            rotate(block, board, data.next());
        }
    }

    public void hardDrop(TetrisBoard board) {
        int limit = board.getHeight();
        while (!isCollide(board) && limit > 0) {
            moveDown();
            limit--;
        }
        moveUp();
    }

    private boolean isCollide(TetrisBoard board) {
        try {
            board.collisionTest(this);
            return true;
        } catch (BlockCollideException e) {
            return false;
        }
    }

    public Tetromino deepCopy() {
        return new Tetromino(space, blockSize, shape, position, x, y, color, points);
    }

    public Tetromino getGuideBlock() {
        return new Tetromino(GUIDER_CELL, blockSize, shape, position, x, y, color, points);
    }

    public int getWidth() {
        return calculateSize(() -> points.stream().mapToInt(Cell::getY));
    }

    public int getHeight() {
        return calculateSize(() -> points.stream().mapToInt(Cell::getX));
    }

    public TetrominoShape getShape() {
        return shape;
    }

    public Color getColor() {
        return this.color;
    }

    public TetrominoPosition getPosition() {
        return this.position;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
