package tetris.components;

import java.util.ArrayList;
import java.util.List;
import tetris.window.Window;

public class Tetromino extends ComponentImpl {

    private static final int BLOCK_WIDTH = 3;
    private static final int BLOCK_HEIGHT = 3;
    private List<Point> points = new ArrayList<>();
    private Color color;
    private int xOffset = 0;
    private int yOffset = 0;

    public Tetromino(Color color) {
        this.color = color;
    }

    public Tetromino(List<Point> points, Color color) {
        this.points.addAll(points);
        points.forEach(p -> p.setWindow(this.window));
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
        validateCoordinate(x);
        validateCoordinate(y);
        this.points.add(new Point(x, y, color));
    }

    private void validateCoordinate(int p) {
        if (p < 0 || p > BLOCK_WIDTH) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void setWindow(Window window) {
        super.setWindow(window);
        points.forEach(p -> p.setWindow(window));
    }

    @Override
    public void update() {
        this.points.forEach(Point::update);
    }

    public List<Point> points() {
        return this.points;
    }
}
