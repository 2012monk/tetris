package tetris.system;

import tetris.components.NextBlockBoard;
import tetris.components.ScoreBoard;
import tetris.components.TetrisBoard;
import tetris.components.Tetromino;
import tetris.constants.Color;
import tetris.constants.Shape;
import tetris.helper.AutoDropper;
import tetris.helper.GameKeyListener;
import tetris.repository.TetrominoRepository;
import tetris.window.Window;
import tetris.window.WindowPoolManager;

public class TetrisInitializer {

    private static int xAlign;
    private static final int boardSize = 20;

    public static void init() {
        WindowPoolManager.init();
        xAlign = WindowPoolManager.getScreen().getHeight() / 2 - boardSize / 2;
        initTetrominos();
        boardWindow();
        presentWindow();
        WindowPoolManager.refreshAll();
    }

    private static void presentWindow() {
        int h = 15;
        int w = 18;
        int scoreH = 3;
        int screenWidth = WindowPoolManager.getScreen().getWidth();
        Window window = new Window(xAlign, screenWidth - screenWidth / 4 - w, w, h);
        window.addComponent(new ScoreBoard(0, 0, w - 2, scoreH));
        window.addComponent(new NextBlockBoard(scoreH + 1, w / 2 - 4, 6, 4));
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

    public static void initTetrominos() {
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
}
