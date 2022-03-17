package tetris.system;

import tetris.components.Color;
import tetris.components.Shape;
import tetris.components.Tetromino;
import tetris.components.TetrominoRepository;

public class TetrisInitializer {

    public static void initWindows() {

    }

    public static void initTetrominos() {
        Tetromino S = new Tetromino(Color.BLUE, Shape.S);
        S.addPoint(0, 0);
        S.addPoint(0, 1);
        S.addPoint(1, 1);
        S.addPoint(1, 2);
        Tetromino I = new Tetromino(Color.RED, Shape.I, 4);
        I.addPoint(0, 0);
        I.addPoint(0, 1);
        I.addPoint(0, 2);
        I.addPoint(0, 3);
        Tetromino T = new Tetromino(Color.GREEN, Shape.T);
        T.addPoint(0, 1);
        T.addPoint(1, 0);
        T.addPoint(1, 1);
        T.addPoint(1, 2);
        Tetromino Z = new Tetromino(Color.YELLOW, Shape.Z);
        Z.addPoint(0, 0);
        Z.addPoint(1, 0);
        Z.addPoint(1, 1);
        Z.addPoint(1, 2);
        Tetromino O = new Tetromino(Color.CYAN, Shape.O);
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
        J.addPoint(0, 0);
        J.addPoint(0, 1);
        J.addPoint(0, 2);
        J.addPoint(1, 2);

        TetrominoRepository.addTetromino(I);
        TetrominoRepository.addTetromino(S);
        TetrominoRepository.addTetromino(T);
        TetrominoRepository.addTetromino(J);
        TetrominoRepository.addTetromino(L);
        TetrominoRepository.addTetromino(Z);
        TetrominoRepository.addTetromino(O);
    }
}
