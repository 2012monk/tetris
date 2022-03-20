package tetris.system;

import tetris.components.TetrisBoard;
import tetris.components.Tetromino;
import tetris.components.TetrominoRepository;
import tetris.constants.Color;
import tetris.constants.Shape;
import tetris.window.Window;
import tetris.window.WindowPoolManager;

public class TetrisInitializer {

    static {
        WindowPoolManager.init();
    }

    public static void initWindows() {
        WindowPoolManager.addWindow(boardWindow());
    }

    private static Window boardWindow() {
        int size = 20;
        int centerX = WindowPoolManager.getScreen().getWidth() / 2;
        int centerY = WindowPoolManager.getScreen().getHeight() / 2;
        Window window = new Window(centerX - size / 2, centerY - size / 2, size, size);
        TetrisBoard board = new TetrisBoard(0, 0, size, size);
        window.addComponent(board);
        return window;
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
