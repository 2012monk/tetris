package tetris.components;

import java.util.List;
import java.util.stream.Collectors;

public class Tetromino extends ComponentContainer {

    private static final int DEFAULT_BLOCK_SIZE = 3;
    private Shape shape;
    private Color color;

    private Tetromino(int x, int y, int blockSize, Color color, Shape shape) {
        super(x, y, blockSize * 2, blockSize * 2);
        this.color = color;
        this.shape = shape;
    }

    private Tetromino(int x, int y, Color color, Shape shape) {
        this(x, y, DEFAULT_BLOCK_SIZE, color, shape);
    }

    public Tetromino(List<Point> points, Color color, Shape shape, int blockSize) {
        this(0, 0, blockSize, color, shape);
        this.components.addAll(points);
    }

    public Tetromino(Color color, Shape shape, int blockSize) {
        this(0, 0, blockSize, color, shape);
    }

    public Tetromino(Color color, Shape shape) {
        this(color, shape, DEFAULT_BLOCK_SIZE);
    }

    public void rotate() {

    }

    public void rotateRight() {

    }

    public void moveDown(int maxHeight) {

    }

    public void moveLeft(int maxWidth) {

    }

    public void moveRight(int maxWidth) {

    }

    public void addPoint(int x, int y) {
        addComponent(new Point(x, y * 2, color));
        addComponent(new Point(x, y * 2 + 1, color));
    }

    public Tetromino copy() {
        List<Point> copied = components.stream()
            .map(c -> ((Point) c).copy())
            .collect(Collectors.toList());
        return new Tetromino(copied, color, shape, getWidth());
    }

    public List<Component> points() {
        return this.components;
    }

    public Shape getShape() {
        return shape;
    }

}
