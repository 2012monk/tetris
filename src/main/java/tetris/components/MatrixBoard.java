package tetris.components;

import java.util.ArrayList;
import java.util.List;
import tetris.ComponentImpl;
import tetris.console.Console;
import tetris.constants.Color;
import tetris.gameobject.Cell;
import tetris.gameobject.TetrisBoard;

public class MatrixBoard extends ComponentImpl {

    private static final char POINT_CHAR = ' ';
    private final List<Cell> currentState = new ArrayList<>();

    public MatrixBoard() {
    }

    public MatrixBoard(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
    }

    @Override
    public void render() {
        if (!hasParent()) {
            return;
        }
        clear();
        renderCurrentState();
    }

    public void renderBoard(TetrisBoard board) {
        updateCurrentState(board.getBoardStatus());
        render();
    }

    private void updateCurrentState(List<Cell> points) {
        this.currentState.clear();
        this.currentState.addAll(points);
    }

    private void renderCurrentState() {
        currentState.forEach(this::renderCell);
    }

    private void renderCell(Cell point) {
        if (!isInside(point)) {
            return;
        }
        Console.drawChar(calculateX(point.getX() * 2), calculateY(point.getY()), POINT_CHAR,
            getDefaultFg(), point.getColor());
        Console.drawChar(calculateX(point.getX() * 2 + 1), calculateY(point.getY()), POINT_CHAR,
            getDefaultFg(), point.getColor());
    }

    private boolean isInside(Cell point) {
        return isInsideSpace(calculateX(point.getX()), calculateY(point.getY()));
    }

    private Color getDefaultFg() {
        return fg;
    }

    private int calculateY(int y) {
        return getInnerY() + y;
    }

    private int calculateX(int x) {
        return getInnerX() + x;
    }

}
