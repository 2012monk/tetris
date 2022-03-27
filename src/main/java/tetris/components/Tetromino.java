package tetris.components;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import tetris.constants.Color;
import tetris.constants.GameKey;
import tetris.constants.Shape;
import tetris.constants.TetrominoPosition;
import tetris.constants.WallKickData;
import tetris.exception.BlockCollideException;
import tetris.exception.EndOfMoveException;
import tetris.exception.OutOfDataException;
import tetris.gameobject.Cell;
import tetris.gameobject.TetrisBoard;
import tetris.repository.PositionRepository;
import tetris.repository.WallKickDataRepository;

public class Tetromino {

    private static final int DEFAULT_BLOCK_SIZE = 3;
    private static final int BASIS = 1;
    private static final char DEFAULT_CELL = ' ';
    private static final char GUIDER_CELL = '.';
    private final List<Cell> points = new ArrayList<>();
    private final char space;
    private final int blockSize;
    private final Shape shape;
    private final Color color;
    private TetrominoPosition position;
    private int x;
    private int y;

    public Tetromino(char space, int blockSize, Shape shape, TetrominoPosition position, int x,
        int y, Color color, List<Cell> cells) {
        this.space = space;
        this.blockSize = blockSize;
        this.shape = shape;
        this.position = position;
        this.x = x;
        this.y = y;
        this.color = color;
        cells.forEach(c -> addCell(c.getX(), c.getY()));
    }

    private Tetromino(int x, int y, int blockSize, Color bg, Shape shape) {
        this.x = x;
        this.y = y;
        this.color = bg;
        this.shape = shape;
        this.blockSize = blockSize;
        this.space = DEFAULT_CELL;
        this.position = PositionRepository.getInitialPosition();
    }

    public Tetromino(Color bg, Shape shape, int blockSize) {
        this(0, 0, blockSize, bg, shape);
    }

    public Tetromino(Color bg, Shape shape) {
        this(bg, shape, DEFAULT_BLOCK_SIZE);
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

    public Tetromino deepCopy() {
        return new Tetromino(space, blockSize, shape, position, x, y, color, points);
    }

    public Tetromino getGuideBlock() {
        return new Tetromino(GUIDER_CELL, blockSize, shape, position, x, y, color, points);
    }

    public int getWidth() {
        int min = this.points.stream().mapToInt(Cell::getY).min().orElse(0);
        int max = this.points.stream().mapToInt(Cell::getY).max().orElse(0);
        return max - min + 1;
    }

    public int getHeight() {
        int min = this.points.stream().mapToInt(Cell::getX).min().orElse(0);
        int max = this.points.stream().mapToInt(Cell::getX).max().orElse(0);
        return max - min + 1;
    }

    public List<Cell> getCalculatedCells() {
        return this.points.stream()
            .map(p -> new Cell(x + p.getX(), y + p.getY(), getColor(), space))
            .collect(Collectors.toList());
    }

    public void move(TetrisBoard board, GameKey key) throws EndOfMoveException {
        if (key == GameKey.KEY_UP) {
            rotateLeft(board);
            return;
        }
        if (key == GameKey.KEY_SPACE) {
            hardDrop(board);
            throw new EndOfMoveException();
        }
        key.simulate(this);
        try {
            board.collisionTest(this);
        } catch (BlockCollideException e) {
            key.reverse(this);
            if (key == GameKey.KEY_DOWN) {
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
        try {
            moveDown();
            board.collisionTest(this);
            hardDrop(board);
        } catch (BlockCollideException e) {
            moveUp();
        }
    }

    public Shape getShape() {
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
