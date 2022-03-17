package tetris;

import tetris.components.TetrisBoard;
import tetris.components.TetrominoRepository;
import tetris.console.Console;
import tetris.constants.Shape;
import tetris.system.TetrisInitializer;
import tetris.window.Window;
import tetris.window.WindowPoolManager;

public class Tetris {

    public static void main(String[] args) throws InterruptedException {
//        Window w1 = new Window(20, 20, 20, 20);
//        Window w2 = new Window(10, 10, 10, 10);
        Window w3 = new Window(0, 0, 35, 35);
        TetrisInitializer.initTetrominos();
        WindowPoolManager.addWindow(w3);

        TetrisBoard board = new TetrisBoard(0, 0, 20, 20);
        w3.addComponent(board);
        char c = 'a';
        while (c != 'q') {
            for (Shape shape : Shape.values()) {
                board.printBlock(TetrominoRepository.getTetrominoByShape(shape));
//                WindowPoolManager.refreshAll();
                Thread.sleep(1000);
            }
            c = (char) Console.readBytes();
        }

        Console.shutdown();
    }
}
