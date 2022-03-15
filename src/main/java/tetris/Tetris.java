package tetris;

import java.io.IOException;
import tetris.console.Console;
import tetris.window.Window;
import tetris.window.WindowPoolManager;

public class Tetris {

    public static void main(String[] args) {
        Window w1 = new Window(20, 20, 20, 20);
        Window w2 = new Window(10, 10, 10, 10);
        Window w3 = new Window(0, 0, 35, 35);
        WindowPoolManager.addWindow(w1);
        WindowPoolManager.addWindow(w3);

        Console.readBytes();
        Console.shutdown();
    }
}
