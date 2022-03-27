package tetris;

import tetris.game.MenuInitializer;
import tetris.game.TetrisInitializer;
import tetris.ui.window.WindowPoolManager;

public class Tetris {

    public static void run(String[] args) {
        WindowPoolManager.init();
        MenuInitializer.initMenus();
        TetrisInitializer.initGameWindow();
    }
}
