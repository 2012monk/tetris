package tetris.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import tetris.ComponentContainer;
import tetris.Spatial;
import tetris.SpatialImpl;
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

public class Tetromino extends ComponentContainer<PointView> {

    private static final int DEFAULT_BLOCK_SIZE = 3;
    private static final int HORIZONTAL_BASIS = 1;
    private static final int VERTICAL_BASIS = 1;
    private static final char DEFAULT_CELL = ' ';
    private static final char GUIDER_CELL = '.';
    private final List<PointView> originalPoints = Collections.synchronizedList(new ArrayList<>());
    private final List<Cell> points = new ArrayList<>();
    private final char cell;
    private final int blockSize;
    private final Shape shape;
    private TetrominoPosition position;
    private boolean initialized = false;

    private Tetromino(int x, int y, int blockSize, Color bg, Shape shape) {
        super(x, y, blockSize * 2, blockSize, false);
        setBg(bg);
        this.shape = shape;
        this.blockSize = blockSize;
        this.cell = DEFAULT_CELL;
        this.position = PositionRepository.getInitialPosition();
    }

    private Tetromino(List<PointView> points, Color fg, Color bg, Shape shape, int x, int y,
        int blockSize, char cell, Spatial parent, TetrominoPosition position) {
        super(x, y, blockSize * 2, blockSize, false);
        this.shape = shape;
        this.blockSize = blockSize;
        this.cell = cell;
        this.position = position;
        setFg(fg);
        setBg(bg);
        setParent(parent);
        points.forEach(p -> addPoint(p.getRelativeX(), p.getRelativeY()));
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

    private void rotateLeft(PointView point) {
        int rotatedY = point.getRelativeX();
        int rotatedX = blockSize - 1 - point.getRelativeY();
        addPoint(rotatedX, rotatedY);
    }

    private void rotateRight(PointView point) {
        int rotatedX = point.getRelativeY();
        int rotatedY = blockSize - 1 - point.getRelativeX();
        addPoint(rotatedX, rotatedY);
    }

    private List<PointView> clearPoints() {
        List<PointView> tmp = new ArrayList<>(this.originalPoints);
        this.originalPoints.clear();
        this.components.clear();
        return tmp;
    }


    public void moveRight() {
        this.y += HORIZONTAL_BASIS;
//        this.y -= this.y % HORIZONTAL_BASIS;
    }

    public void moveLeft() {
        this.y -= HORIZONTAL_BASIS;
//        this.y -= this.y % HORIZONTAL_BASIS;
    }

    public void moveDown() {
        this.x += VERTICAL_BASIS;
    }

    public void moveUp() {
        this.x -= VERTICAL_BASIS;
    }

    public void addPoint(int x, int y) {
        this.originalPoints.add(new PointView(x, y, fg, bg, cell));
        for (int i = 0; i < HORIZONTAL_BASIS; i++) {
            addComponent(new PointView(x, y * HORIZONTAL_BASIS + i, fg, bg, cell));
        }
    }

    public void initBlock(Spatial spatial) {
        if (initialized && spatial.equals(this.parent)) {
            return;
        }
        setParent(spatial);
        initialized = true;
        moveToStartingPoint(spatial.getInnerWidth());
    }

    public void moveToStartingPosition(TetrisBoard board) {
        moveToStartingPoint(board.getWidth() * 2);
        board.collisionTest(this);
    }

    private void moveToStartingPoint(int maxWidth) {
        this.x = -components.stream()
            .mapToInt(SpatialImpl::getRelativeX)
            .min()
            .orElseThrow(NoSuchElementException::new);
        alignCenter(maxWidth);
    }

    public void alignCenter(int maxWidth) {
        this.y = (maxWidth / 2 - blockSize) / 2;
    }

    public Tetromino copy() {
        return new Tetromino(originalPoints, fg, bg, shape, getRelativeX(), getRelativeY(),
            blockSize, cell, getParent(), position);
    }

    public Tetromino getGuideBlock() {
        return new Tetromino(originalPoints, fg, bg, shape, getRelativeX(),
            getRelativeY(),
            blockSize, GUIDER_CELL, getParent(), position);
    }

    @Override
    public void clear() {
        this.components.forEach(PointView::clear);
    }

    public int getActualWidth() {
        int min = this.components.stream().mapToInt(SpatialImpl::getRelativeY).min().orElse(0);
        int max = this.components.stream().mapToInt(SpatialImpl::getRelativeY).max().orElse(0);
        return max - min + 1;
    }

    public int getActualHeight() {
        int min = this.components.stream().mapToInt(SpatialImpl::getRelativeX).min().orElse(0);
        int max = this.components.stream().mapToInt(SpatialImpl::getRelativeX).max().orElse(0);
        return max - min + 1;
    }

    public List<Cell> getCalculatedCells() {
        return this.originalPoints.stream()
            .map(p -> new Cell(getRelativeX() + p.getRelativeX(),
                getRelativeY() + p.getRelativeY(),
                getColor()))
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
        return this.bg;
    }


    public TetrominoPosition getPosition() {
        return this.position;
    }

    public int getBlockSize() {
        return blockSize;
    }

}
