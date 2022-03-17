package tetris;

import java.util.Arrays;
import java.util.Iterator;
import tetris.components.Shape;
import tetris.components.TetrisBoard;
import tetris.components.TetrominoRepository;
import tetris.console.Console;
import tetris.system.TetrisInitializer;
import tetris.window.Window;
import tetris.window.WindowPoolManager;

public class Tetris {

    public static void main(String[] args) throws InterruptedException {
        Window w1 = new Window(20, 20, 20, 20);
        Window w2 = new Window(10, 10, 10, 10);
        Window w3 = new Window(0, 0, 35, 35);
//        WindowPoolManager.addWindow(w1);
        TetrisInitializer.initTetrominos();
        WindowPoolManager.addWindow(w3);

        TetrisBoard board = new TetrisBoard(0, 0, 20, 20);
        w3.addComponent(board);
        board.printBlock(TetrominoRepository.getTetrominoByShape(Shape.T));
        char c = 'a';
        while (c != 'q') {
            for (Shape shape : Shape.values()) {
                board.printBlock(TetrominoRepository.getTetrominoByShape(shape));
                Thread.sleep(1000);
            }
            c = (char) Console.readBytes();
        }

        Console.shutdown();
    }
}
