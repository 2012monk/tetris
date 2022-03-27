package tetris.gameobject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import tetris.components.Tetromino;
import tetris.exception.BlockCollideException;

public class TetrisBoard {

    private static final int DEFAULT_HEIGHT = 20;
    private static final int DEFAULT_WIDTH = 10;
    private final List<Cell> fixedCells = new ArrayList<>();
    private final List<Cell> temporaryCells = new ArrayList<>();
    private final int width;
    private final int height;

    public TetrisBoard() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
    }

    public TetrisBoard(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Cell> getBoardStatus() {
        List<Cell> status = new ArrayList<>(fixedCells);
        status.addAll(temporaryCells);
        return Collections.unmodifiableList(status);
    }

    public void updateTetromino(Tetromino tetromino) throws BlockCollideException {
        collisionTest(tetromino);
        temporaryCells.clear();
        this.temporaryCells.addAll(tetromino.getCalculatedCells());
    }

    public void updateGuideTetromino(Tetromino tetromino) throws BlockCollideException {
        this.temporaryCells.addAll(tetromino.getCalculatedCells());
    }

    public void collisionTest(Tetromino tetromino) throws BlockCollideException {
        verifyPoints(tetromino.getCalculatedCells());
    }

    private void verifyPoints(List<Cell> points) throws BlockCollideException {
        points.forEach(this::verifyPoint);
        points.forEach(this::verifyOverlap);
    }

    private void verifyOverlap(Cell point) throws BlockCollideException {
        boolean overlapped = this.fixedCells.stream().anyMatch(p -> p.isOverlapped(point));
        if (overlapped) {
            throw new BlockCollideException();
        }
    }

    private void verifyPoint(Cell cell) throws BlockCollideException {
        int x = cell.getX();
        int y = cell.getY();
        if (!isInsideSpace(x, y)) {
            throw new BlockCollideException();
        }
    }

    private boolean isInsideSpace(int x, int y) {
        return 0 <= x && x < getHeight() && 0 <= y && y < getWidth();
    }

    public int stackTetromino(Tetromino block) {
        return stackCells(block.getCalculatedCells());
    }

    private int stackCells(List<Cell> cells) {
        verifyPoints(cells);
        fixedCells.addAll(cells);
        return deleteFullRow();
    }

    private int deleteFullRow() {
        Map<Integer, List<Cell>> lines = fixedCells
            .stream()
            .collect(Collectors.groupingBy(Cell::getX));

        List<Integer> fullRows = getFullRow(lines);
        int lineCount = fullRows.size();
        if (fullRows.isEmpty()) {
            return lineCount;
        }
        resetBoard();
        lines.values().removeIf(v -> v.size() == width);
        lines.forEach((key, value) -> {
            int cali =
                fullRows.size() + Collections.binarySearch(fullRows, key) + 1;
            value.forEach(cell -> cell.setCartesianZero(cali, 0));
        });
        lines.forEach((key, value) -> fixedCells.addAll(value));
        return lineCount;
    }

    private List<Integer> getFullRow(Map<Integer, List<Cell>> lines) {
        return lines.entrySet()
            .stream()
            .filter(e -> e.getValue().size() == width)
            .map(Entry::getKey)
            .sorted()
            .collect(Collectors.toList());
    }

    public void resetBoard() {
        this.fixedCells.clear();
        this.temporaryCells.clear();
    }
}
