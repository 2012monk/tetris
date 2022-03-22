package tetris.system;

import static tetris.constants.TetrominoStatus.INVERSE;
import static tetris.constants.TetrominoStatus.LEFT;
import static tetris.constants.TetrominoStatus.RIGHT;
import static tetris.constants.TetrominoStatus.SPAWN_STATE;

import java.util.Arrays;
import java.util.List;
import tetris.components.NextBlockBoard;
import tetris.components.ScoreBoard;
import tetris.components.TetrisBoard;
import tetris.components.Tetromino;
import tetris.constants.Color;
import tetris.constants.Shape;
import tetris.constants.TetrominoPosition;
import tetris.constants.WallKickCorrectionValue;
import tetris.helper.AutoDropper;
import tetris.helper.GameKeyListener;
import tetris.repository.PositionRepository;
import tetris.repository.TetrominoRepository;
import tetris.repository.WallKickDataRepository;
import tetris.window.Window;
import tetris.window.WindowPoolManager;

public class TetrisInitializer {

    private static final int boardSize = 20;
    private static int xAlign;

    public static void init() {
        WindowPoolManager.init();
        xAlign = WindowPoolManager.getScreen().getHeight() / 2 - boardSize / 2;
        initTetrominos();
        boardWindow();
        presentWindow();
        WindowPoolManager.refreshAll();
    }

    private static void presentWindow() {
        int h = 6;
        int w = 18;
        int scoreH = 3;
        int screenWidth = WindowPoolManager.getScreen().getWidth();
        Window window = new Window(xAlign, screenWidth - screenWidth / 4 - w, w, h);
        window.addComponent(new ScoreBoard(0, 0, w - 2, scoreH));
        WindowPoolManager.addWindow(window);
        window = new Window(xAlign + h, screenWidth - screenWidth / 4 - w, w, h);
        window.addComponent(
            new NextBlockBoard(window.getInnerHeight() / 2 - 1, window.getInnerWidth() / 2 - 3, 6,
                4));
        WindowPoolManager.addWindow(window);
    }

    private static void boardWindow() {
        int centerY = WindowPoolManager.getScreen().getWidth() / 2;

        Window window = new Window(xAlign, centerY - boardSize / 2, boardSize, boardSize);
        WindowPoolManager.addWindow(window);
        TetrisBoard board = new TetrisBoard(0, 0, boardSize - 2, boardSize - 2);
        window.addComponent(board);
        window.addComponent(new GameKeyListener());
        window.addComponent(new AutoDropper());
    }

    private static void initWallKickData() {
        WallKickDataRepository.addData(SPAWN_STATE, RIGHT,
            getWallKickData(0, 0, -1, 0, -1, +1, 0, -2, -1, -2));
        WallKickDataRepository.addData(RIGHT, SPAWN_STATE,
            getWallKickData(0, 0, +1, 0, +1, -1, 0, +2, +1, +2));
        WallKickDataRepository.addData(RIGHT, INVERSE,
            getWallKickData(0, 0, +1, 0, +1, -1, 0, +2, +1, +2));
        WallKickDataRepository.addData(INVERSE, RIGHT,
            getWallKickData(0, 0, -1, 0, -1, +1, 0, -2, -1, -2));
        WallKickDataRepository.addData(INVERSE, LEFT,
            getWallKickData(0, 0, +1, 0, +1, +1, 0, -2, +1, -2));
        WallKickDataRepository.addData(LEFT, INVERSE,
            getWallKickData(0, 0, -1, 0, -1, -1, 0, +2, -1, +2));
        WallKickDataRepository.addData(LEFT, SPAWN_STATE,
            getWallKickData(0, 0, -1, 0, -1, -1, 0, +2, -1, +2));
        WallKickDataRepository.addData(SPAWN_STATE, LEFT,
            getWallKickData(0, 0, +1, 0, +1, +1, 0, -2, +1, -2));

        WallKickDataRepository.addIData(SPAWN_STATE, RIGHT,
            getWallKickData(0, 0, -2, 0, +1, 0, -2, -1, +1, +2));
        WallKickDataRepository.addIData(RIGHT, SPAWN_STATE,
            getWallKickData(0, 0, +2, 0, -1, 0, +2, +1, -1, -2));
        WallKickDataRepository.addIData(RIGHT, INVERSE,
            getWallKickData(0, 0, -1, 0, +2, 0, -1, +2, +2, -1));
        WallKickDataRepository.addIData(INVERSE, RIGHT,
            getWallKickData(0, 0, +1, 0, -2, 0, +1, -2, -2, +1));
        WallKickDataRepository.addIData(INVERSE, LEFT,
            getWallKickData(0, 0, +2, 0, -1, 0, +2, +1, -1, -2));
        WallKickDataRepository.addIData(LEFT, INVERSE,
            getWallKickData(0, 0, -2, 0, +1, 0, -2, -1, +1, +2));
        WallKickDataRepository.addIData(LEFT, SPAWN_STATE,
            getWallKickData(0, 0, +1, 0, -2, 0, +1, -2, -2, +1));
        WallKickDataRepository.addIData(SPAWN_STATE, LEFT,
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
        initWallKickData();
        initPosition();
        Tetromino S = getS();
        Tetromino I = new Tetromino(Color.RED, Shape.I, 4);
        I.addPoint(1, 0);
        I.addPoint(1, 1);
        I.addPoint(1, 2);
        I.addPoint(1, 3);
        Tetromino T = new Tetromino(Color.GREEN, Shape.T);
        T.addPoint(0, 1);
        T.addPoint(1, 0);
        T.addPoint(1, 1);
        T.addPoint(1, 2);
        Tetromino Z = new Tetromino(Color.YELLOW, Shape.Z);
        Z.addPoint(0, 2);
        Z.addPoint(0, 1);
        Z.addPoint(1, 1);
        Z.addPoint(1, 0);
        Tetromino O = new Tetromino(Color.CYAN, Shape.O, 2);
        O.addPoint(0, 0);
        O.addPoint(0, 1);
        O.addPoint(1, 0);
        O.addPoint(1, 1);
        Tetromino L = new Tetromino(Color.WHITE, Shape.L);
        L.addPoint(0, 0);
        L.addPoint(1, 0);
        L.addPoint(2, 0);
        L.addPoint(2, 1);
        Tetromino J = new Tetromino(Color.MAGENTA, Shape.J);
        J.addPoint(0, 2);
        J.addPoint(1, 2);
        J.addPoint(2, 2);
        J.addPoint(2, 1);

        TetrominoRepository.addTetromino(I);
        TetrominoRepository.addTetromino(S);
        TetrominoRepository.addTetromino(T);
        TetrominoRepository.addTetromino(J);
        TetrominoRepository.addTetromino(L);
        TetrominoRepository.addTetromino(Z);
        TetrominoRepository.addTetromino(O);
    }

    public static Tetromino getS() {
        Tetromino S = new Tetromino(Color.BLUE, Shape.S);
        S.addPoint(0, 0);
        S.addPoint(0, 1);
        S.addPoint(1, 1);
        S.addPoint(1, 2);
        return S;
    }

    private static List<WallKickCorrectionValue> getWallKickData(
        int y1, int x1,
        int y2, int x2,
        int y3, int x3,
        int y4, int x4,
        int y5, int x5
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
