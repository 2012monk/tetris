package tetris.game;

import static tetris.constants.TetrominoStatus.INVERSE;
import static tetris.constants.TetrominoStatus.LEFT;
import static tetris.constants.TetrominoStatus.RIGHT;
import static tetris.constants.TetrominoStatus.SPAWN_STATE;
import static tetris.repository.TetrominoRepository.addTetromino;
import static tetris.repository.WallKickDataRepository.addData;
import static tetris.repository.WallKickDataRepository.addIData;

import java.util.Arrays;
import java.util.List;
import tetris.constants.TetrominoPosition;
import tetris.constants.TetrominoShape;
import tetris.constants.WallKickCorrectionValue;
import tetris.gameobject.Tetromino;
import tetris.repository.PositionRepository;
import tetris.ui.constants.Color;

public class TetrisDataInitializer {

    public static void initData() {
        initWallKickData();
        initPosition();
        initTetrominos();
    }

    /*
     * Super Rotation System WallKick Data
     */
    private static void initWallKickData() {
        addData(SPAWN_STATE, RIGHT,
            getWallKickData(0, 0, -1, 0, -1, +1, 0, -2, -1, -2));
        addData(RIGHT, SPAWN_STATE,
            getWallKickData(0, 0, +1, 0, +1, -1, 0, +2, +1, +2));
        addData(RIGHT, INVERSE,
            getWallKickData(0, 0, +1, 0, +1, -1, 0, +2, +1, +2));
        addData(INVERSE, RIGHT,
            getWallKickData(0, 0, -1, 0, -1, +1, 0, -2, -1, -2));
        addData(INVERSE, LEFT,
            getWallKickData(0, 0, +1, 0, +1, +1, 0, -2, +1, -2));
        addData(LEFT, INVERSE,
            getWallKickData(0, 0, -1, 0, -1, -1, 0, +2, -1, +2));
        addData(LEFT, SPAWN_STATE,
            getWallKickData(0, 0, -1, 0, -1, -1, 0, +2, -1, +2));
        addData(SPAWN_STATE, LEFT,
            getWallKickData(0, 0, +1, 0, +1, +1, 0, -2, +1, -2));

        addIData(SPAWN_STATE, RIGHT,
            getWallKickData(0, 0, -2, 0, +1, 0, -2, -1, +1, +2));
        addIData(RIGHT, SPAWN_STATE,
            getWallKickData(0, 0, +2, 0, -1, 0, +2, +1, -1, -2));
        addIData(RIGHT, INVERSE,
            getWallKickData(0, 0, -1, 0, +2, 0, -1, +2, +2, -1));
        addIData(INVERSE, RIGHT,
            getWallKickData(0, 0, +1, 0, -2, 0, +1, -2, -2, +1));
        addIData(INVERSE, LEFT,
            getWallKickData(0, 0, +2, 0, -1, 0, +2, +1, -1, -2));
        addIData(LEFT, INVERSE,
            getWallKickData(0, 0, -2, 0, +1, 0, -2, -1, +1, +2));
        addIData(LEFT, SPAWN_STATE,
            getWallKickData(0, 0, +1, 0, -2, 0, +1, -2, -2, +1));
        addIData(SPAWN_STATE, LEFT,
            getWallKickData(0, 0, -1, 0, +2, 0, -1, +2, +2, -1));

    }

    public static void initPosition() {
        TetrominoPosition spawn = new TetrominoPosition(SPAWN_STATE);
        TetrominoPosition right = new TetrominoPosition(RIGHT);
        TetrominoPosition left = new TetrominoPosition(LEFT);
        TetrominoPosition inv = new TetrominoPosition(INVERSE);
        spawn.setRightPosition(right);
        spawn.setLeftPosition(left);
        right.setRightPosition(inv);
        right.setLeftPosition(spawn);
        left.setRightPosition(spawn);
        left.setLeftPosition(inv);
        inv.setRightPosition(left);
        inv.setLeftPosition(right);
        PositionRepository.addPosition(spawn);
        PositionRepository.addPosition(right);
        PositionRepository.addPosition(inv);
        PositionRepository.addPosition(left);
    }

    public static void initTetrominos() {
        addTetromino(get(Color.GREEN, TetrominoShape.T,
            0, 1, 1, 0, 1, 1, 1, 2, -1));
        addTetromino(get(Color.YELLOW, TetrominoShape.Z,
            0, 2, 0, 1, 1, 1, 1, 0, -1));
        addTetromino(get(Color.WHITE, TetrominoShape.L,
            1, 0, 1, 1, 1, 2, 0, 2, -1));
        addTetromino(get(Color.MAGENTA, TetrominoShape.J,
            1, 0, 1, 1, 1, 2, 0, 0, -1));
        addTetromino(get(Color.BLUE, TetrominoShape.S,
            0, 0, 0, 1, 1, 1, 1, 2, -1));
        addTetromino(get(Color.RED, TetrominoShape.I,
            1, 0, 1, 1, 1, 2, 1, 3, 4));
        addTetromino(get(Color.CYAN, TetrominoShape.O,
            0, 0, 0, 1, 1, 0, 1, 1, 2));
    }

    public static Tetromino get(Color color, TetrominoShape shape,
        int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int size
    ) {
        Tetromino t = new Tetromino(color, shape);
        if (size != -1) {
            t = new Tetromino(color, shape, size);
        }
        t.addCell(x1, y1);
        t.addCell(x2, y2);
        t.addCell(x3, y3);
        t.addCell(x4, y4);
        return t;
    }

    private static List<WallKickCorrectionValue> getWallKickData(
        int y1, int x1, int y2, int x2, int y3, int x3, int y4, int x4, int y5, int x5
    ) {
        return Arrays.asList(
            new WallKickCorrectionValue(x1, y1),
            new WallKickCorrectionValue(x2, y2),
            new WallKickCorrectionValue(x3, y3),
            new WallKickCorrectionValue(x4, y4),
            new WallKickCorrectionValue(x5, y5)
        );
    }
}
