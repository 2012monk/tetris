package tetris.components;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import tetris.constants.Color;
import tetris.constants.Shape;
import tetris.window.Spatial;
import tetris.window.SpatialImpl;

public class Tetromino extends ComponentContainer<Point> {

    private static final int DEFAULT_BLOCK_SIZE = 3;
    private final ArrayDeque<Point> originalPoints = new ArrayDeque<>();
    private final int blockSize;
    private final Shape shape;
    private final Color color;
    private boolean initialized = false;

    private Tetromino(int x, int y, int blockSize, Color color, Shape shape) {
        super(x, y, blockSize * 2, blockSize, false);
        this.color = color;
        this.shape = shape;
        this.blockSize = blockSize;
    }

    private Tetromino(List<Point> points, Color color, Shape shape, int blockSize) {
        this(0, 0, blockSize, color, shape);
        points.forEach(p -> addPoint(p.getRelativeX(), p.getRelativeY()));
    }

    public Tetromino(Color color, Shape shape, int blockSize) {
        this(0, 0, blockSize, color, shape);
    }

    public Tetromino(Color color, Shape shape) {
        this(color, shape, DEFAULT_BLOCK_SIZE);
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
    }

    private void rotate90(Point point) {
        int x = point.getRelativeX();
        int y = point.getRelativeY();
        addPoint(y, blockSize - x - 1);
    }

    private List<Point> clearPoints() {
        List<Point> tmp = new ArrayList<>(this.originalPoints);
        this.originalPoints.clear();
        this.components.clear();
        return tmp;
    }

    public void moveDown() {
        this.x++;
    }

    public void moveLeft() {
        this.y--;
    }

    public void moveRight() {
        this.y++;
    }

    public void addPoint(int x, int y) {
        this.originalPoints.add(new Point(x, y, color));
        addComponent(new Point(x, y * 2, color));
        addComponent(new Point(x, y * 2 + 1, color));
    }

    public void init(Spatial spatial) {
        if (initialized && spatial.equals(this.parent)) {
            return;
        }
        setParent(spatial);
        initialized = true;
        moveToStartingPoint();
    }

    private void moveToStartingPoint() {
        this.x = 1 - components.stream()
            .mapToInt(SpatialImpl::getAbsoluteX)
            .max()
            .orElseThrow(NoSuchElementException::new);
        this.y = (parent.getInnerWidth() / 2) - getWidth() / 2;
    }

    public Tetromino copy() {
        List<Point> copiedOriginalPoints = originalPoints.stream()
            .map(Point::copy)
            .collect(Collectors.toList());
        return new Tetromino(copiedOriginalPoints, color, shape, blockSize);
    }

    public List<Point> points() {
        return this.components;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }
}
