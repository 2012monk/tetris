package tetris.system;

import tetris.components.GameMenu;
import tetris.components.Menu;
import tetris.console.Console;
import tetris.window.Window;
import tetris.window.WindowPoolManager;

public class MenuInitializer {

    public static void initMenu() {
        Window window = new Window(0, 0, Console.getScreenWidth(), Console.getScreenHeight(),
            false);
        int h = 12;
        int w = 24;
        int x = (window.getInnerHeight() - h) / 2 ;
        int y = (window.getInnerWidth() - w) / 2;
        Menu menu = new Menu(x, y, w, h);
        menu.addMenu("start", () -> {
        });
        menu.addMenu("leader board", () -> {
        });
        menu.addMenu("quit", () -> {
        });
//        window.addComponent(
//            new GameMenu(0, 0, window.getInnerWidth(), window.getInnerHeight(), false));
        window.addComponent(menu);
        WindowPoolManager.addWindow(window);
    }
}
