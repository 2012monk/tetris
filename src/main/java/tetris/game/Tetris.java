package tetris.game;

import tetris.system.MenuInitializer;
import tetris.system.TetrisInitializer;
import tetris.window.WindowPoolManager;

public class Tetris {

    public static void run(String[] args) {
        WindowPoolManager.init();
        MenuInitializer.initMenus();
        TetrisInitializer.initGameWindow();
    }
}
