package tetris.game;

import tetris.system.MenuSelector;
import tetris.window.WindowPoolManager;

public class Tetris {

    public static void run(String[] args) {
        WindowPoolManager.init();
        MenuSelector.init();
    }
}
