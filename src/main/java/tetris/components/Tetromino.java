package tetris.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import tetris.constants.Color;
import tetris.constants.Shape;
import tetris.window.Spatial;
import tetris.window.SpatialImpl;

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
    private boolean initialized = false;

    private Tetromino(int x, int y, int blockSize, Color bg, Shape shape) {
        super(x, y, blockSize * 2, blockSize, false);
        setBg(bg);
        this.shape = shape;
        this.blockSize = blockSize;
        this.cell = DEFAULT_CELL;
    }

    private Tetromino(List<Point> points, Color fg, Color bg, Shape shape, int x, int y,
        int blockSize, char cell, Spatial parent) {
        super(x, y, blockSize * 2, blockSize, false);
        this.shape = shape;
        this.blockSize = blockSize;
        this.cell = cell;
        setFg(fg);
        setBg(bg);
        setParent(parent);
        points.forEach(p -> addPoint(p.getRelativeX(), p.getRelativeY()));
    }

    private Tetromino(List<Point> points, Color bg, Shape shape, int x, int y, int blockSize) {
        this(x, y, blockSize, bg, shape);
        points.forEach(p -> addPoint(p.getRelativeX(), p.getRelativeY()));
    }

    public Tetromino(Color bg, Shape shape, int blockSize) {
        this(0, 0, blockSize, bg, shape);
    }

    public Tetromino(Color bg, Shape shape) {
        this(bg, shape, DEFAULT_BLOCK_SIZE);
    }

    /*
     * 270 degree rotate x,y => -y, x
     * TODO 선형변환 로직 분리
     */
    public void rotate270() {
        clearPoints().forEach(this::rotate270);
    }

    /**
     * 90 degree rotate
     */
    public void rotate90() {
        clearPoints().forEach(this::rotate90);
    }

    private void rotate270(Point point) {
        int x = point.getRelativeX();
        int y = point.getRelativeY();
        addPoint(blockSize - y - 1, x);
        updateInParent();
    }

    private void rotate90(Point point) {
        int x = point.getRelativeX();
        int y = point.getRelativeY();
        addPoint(y, blockSize - x - 1);
        updateInParent();
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
        this.x = 1 - components.stream()
            .mapToInt(SpatialImpl::getRelativeX)
            .max()
            .orElseThrow(NoSuchElementException::new);
        alignCenter();
    }

    public void alignCenter() {
        this.y = (parent.getInnerWidth() / 2) - getWidth() / 2;
        this.y -= this.y % HORIZONTAL_BASIS;
    }

    public Tetromino copy() {
        return new Tetromino(originalPoints, bg, shape, getRelativeX(), getRelativeY(),
            blockSize);
    }

    public Tetromino getGuideBlock() {
        return new Tetromino(originalPoints, fg, bg, shape, getRelativeX(),
            getRelativeY(),
            blockSize, GUIDER_CELL, getParent());
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

    @Override
    public void clear() {
        this.components.forEach(Point::clear);
    }

    public void updateInParent() {
        if (hasParent()) {
            update();
        }
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public int getActualWidth() {
        int min = this.components.stream().mapToInt(SpatialImpl::getRelativeY).min().orElse(0);
        int max = this.components.stream().mapToInt(SpatialImpl::getRelativeY).max().orElse(0);
        return max - min + 1;
    }
}
