package tetris.system;

import static tetris.constants.TetrominoStatus.INVERSE;
import static tetris.constants.TetrominoStatus.LEFT;
import static tetris.constants.TetrominoStatus.RIGHT;
import static tetris.constants.TetrominoStatus.SPAWN_STATE;

import java.util.Arrays;
import java.util.List;
import tetris.constants.TetrominoPosition;
import tetris.constants.TetrominoShape;
import tetris.constants.WallKickCorrectionValue;
import tetris.gameobject.Tetromino;
import tetris.helper.AutoDropper;
import tetris.repository.PositionRepository;
import tetris.repository.TetrominoRepository;
import tetris.repository.WallKickDataRepository;
import tetris.ui.components.BlockCounter;
import tetris.ui.components.GameClock;
import tetris.ui.components.GameTitle;
import tetris.ui.components.HelpMessage;
import tetris.ui.components.NextBlockBoard;
import tetris.ui.components.ScoreBoard;
import tetris.ui.components.TetrisBoardPanel;
import tetris.ui.constants.Color;
import tetris.ui.game.GameWindow;
import tetris.ui.window.Window;
import tetris.ui.window.WindowPoolManager;

public class TetrisInitializer {

    private static final int BOARD_SIZE = 22;
    private static int xAlign;
    private static Window window;

    public static Window getGameWindow() {
        if (window == null) {
            initGameWindow();
        }
        return window;
    }


    public static void initGameWindow() {
        window = new GameWindow("gameMenu", WindowPoolManager.getScreen());
        WindowPoolManager.addWindow(window);
        GameTitle title = new GameTitle(0, 0, window.getInnerWidth(), window.getInnerHeight(),
            false);
        xAlign = GameTitle.getTitleHeight() + 1;
        window.addComponent(title);
        initTetrominos();
        boardWindow();
        presentWindow();
        leftBoard();
    }

    private static void presentWindow() {
        int w = 17;
        int scoreH = 5;
        int boardHeight = 4;
        int y = (window.getInnerWidth() - BOARD_SIZE) / 2 + BOARD_SIZE + 2;
        window.addComponent(new ScoreBoard(xAlign, y, w, scoreH, true));
        window.addComponent(
            new NextBlockBoard(xAlign + scoreH, y, w, boardHeight, true));
        window.addComponent(new HelpMessage(xAlign + scoreH + boardHeight, y, w, 11, true));
    }

    private static void leftBoard() {
        int w = 17;
        int y = (window.getInnerWidth() - BOARD_SIZE) / 2 - BOARD_SIZE + 1;
        window.addComponent(new GameClock(xAlign, y, w, 3, true));
        window.addComponent(new BlockCounter(xAlign + 3, y, w, 17, true));
    }

    private static void boardWindow() {
        int y = (window.getInnerWidth() - BOARD_SIZE) / 2;
        TetrisBoardPanel board = new TetrisBoardPanel(xAlign, y, BOARD_SIZE, BOARD_SIZE);
        window.addComponent(board);
        window.addComponent(new AutoDropper());
    }

    /*
     * Super Rotation System WallKick Data
     */
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
        Tetromino I = new Tetromino(Color.RED, TetrominoShape.I, 4);
        I.addCell(1, 0);
        I.addCell(1, 1);
        I.addCell(1, 2);
        I.addCell(1, 3);
        Tetromino T = new Tetromino(Color.GREEN, TetrominoShape.T);
        T.addCell(0, 1);
        T.addCell(1, 0);
        T.addCell(1, 1);
        T.addCell(1, 2);
        Tetromino Z = new Tetromino(Color.YELLOW, TetrominoShape.Z);
        Z.addCell(0, 2);
        Z.addCell(0, 1);
        Z.addCell(1, 1);
        Z.addCell(1, 0);
        Tetromino O = new Tetromino(Color.CYAN, TetrominoShape.O, 2);
        O.addCell(0, 0);
        O.addCell(0, 1);
        O.addCell(1, 0);
        O.addCell(1, 1);
        Tetromino L = new Tetromino(Color.WHITE, TetrominoShape.L);
        L.addCell(1, 0);
        L.addCell(1, 1);
        L.addCell(1, 2);
        L.addCell(0, 2);
        Tetromino J = new Tetromino(Color.MAGENTA, TetrominoShape.J);
        J.addCell(1, 0);
        J.addCell(1, 1);
        J.addCell(1, 2);
        J.addCell(0, 0);

        TetrominoRepository.addTetromino(I);
        TetrominoRepository.addTetromino(S);
        TetrominoRepository.addTetromino(T);
        TetrominoRepository.addTetromino(J);
        TetrominoRepository.addTetromino(L);
        TetrominoRepository.addTetromino(Z);
        TetrominoRepository.addTetromino(O);
    }

    public static Tetromino getS() {
        Tetromino S = new Tetromino(Color.BLUE, TetrominoShape.S);
        S.addCell(0, 0);
        S.addCell(0, 1);
        S.addCell(1, 1);
        S.addCell(1, 2);
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
