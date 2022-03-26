package tetris.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import tetris.ComponentContainer;
import tetris.Spatial;
import tetris.SpatialImpl;
import tetris.constants.Color;
import tetris.constants.GameKey;
import tetris.constants.Shape;
import tetris.constants.TetrominoPosition;
import tetris.repository.PositionRepository;

public class Tetromino extends ComponentContainer<Point> {

    private static final int DEFAULT_BLOCK_SIZE = 3;
    private static final int HORIZONTAL_BASIS = 2;
    private static final int VERTICAL_BASIS = 1;
    private static final char DEFAULT_CELL = ' ';
    private static final char GUIDER_CELL = '.';
    private final List<Point> originalPoints = Collections.synchronizedList(new ArrayList<>());
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

    private Tetromino(List<Point> points, Color fg, Color bg, Shape shape, int x, int y,
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

    private Tetromino(Spatial parent, List<Point> points, Color bg, Shape shape, int x, int y,
        int blockSize, TetrominoPosition position) {
        this(x, y, blockSize, bg, shape);
        this.position = position;
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

    public void printRotateLeft() {
        rotateLeft();
        updateInParent();
    }

    /**
     * 90 degree rotate
     */
    public void rotateRight() {
        clearPoints().forEach(this::rotateRight);
        this.position = position.getRightPosition();
    }

    public void printRotateRight() {
        rotateRight();
        updateInParent();
    }

    private void rotateLeft(Point point) {
        int rotatedY = point.getRelativeX();
        int rotatedX = blockSize - 1 - point.getRelativeY();
        addPoint(rotatedX, rotatedY);
    }

    private void rotateRight(Point point) {
        int rotatedX = point.getRelativeY();
        int rotatedY = blockSize - 1 - point.getRelativeX();
        addPoint(rotatedX, rotatedY);
    }

    private List<Point> clearPoints() {
        List<Point> tmp = new ArrayList<>(this.originalPoints);
        this.originalPoints.clear();
        this.components.clear();
        return tmp;
    }

    public void printDown() {
        moveDown();
        updateInParent();
    }

    public void printLeft() {
        moveLeft();
        updateInParent();
    }


    public void printRight() {
        moveRight();
        updateInParent();
    }

    public void moveRight() {
        this.y += HORIZONTAL_BASIS;
        this.y -= this.y % HORIZONTAL_BASIS;
    }

    public void moveLeft() {
        this.y -= HORIZONTAL_BASIS;
        this.y -= this.y % HORIZONTAL_BASIS;
    }

    public void moveDown() {
        this.x += VERTICAL_BASIS;
    }

    public void moveUp() {
        this.x -= VERTICAL_BASIS;
    }

    public void addPoint(int x, int y) {
        this.originalPoints.add(new Point(x, y, fg, bg, cell));
        for (int i = 0; i < HORIZONTAL_BASIS; i++) {
            addComponent(new Point(x, y * HORIZONTAL_BASIS + i, fg, bg, cell));
        }
    }

    public void initBlock(Spatial spatial) {
        if (initialized && spatial.equals(this.parent)) {
            return;
        }
        setParent(spatial);
        initialized = true;
        moveToStartingPoint();
    }

    private void moveToStartingPoint() {
        this.x = -components.stream()
            .mapToInt(SpatialImpl::getRelativeX)
            .max()
            .orElseThrow(NoSuchElementException::new);
        alignCenter();
    }

    public void alignCenter() {
        this.y = (parent.getInnerWidth() - getActualWidth()) / 2;
        this.y -= this.y % HORIZONTAL_BASIS;
    }

    public Tetromino copy() {
        return new Tetromino(getParent(), originalPoints, bg, shape, getRelativeX(), getRelativeY(),
            blockSize, position);
    }

    public Tetromino getGuideBlock() {
        return new Tetromino(originalPoints, fg, bg, shape, getRelativeX(),
            getRelativeY(),
            blockSize, GUIDER_CELL, getParent(), position);
    }

    @Override
    public void clear() {
        this.components.forEach(Point::clear);
    }

    public void updateInParent() {
        if (hasParent()) {
            render();
        }
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

    public List<Point> points() {
        return this.components;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return this.bg;
    }

    public Tetromino simulate(GameKey key) {
        Tetromino copied = copy();
        key.simulate(copied);
        return copied;
    }

    public boolean isCollide() {
        return false;
    }

    public TetrominoPosition getPosition() {
        return this.position;
    }

    public int getBlockSize() {
        return blockSize;
    }
}
