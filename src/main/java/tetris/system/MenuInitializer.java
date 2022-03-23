package tetris.system;

import tetris.components.GameTitle;
import tetris.components.Menu;
import tetris.constants.GameStatus;
import tetris.message.GameStatusMessage;
import tetris.window.Window;
import tetris.window.WindowPoolManager;

public class MenuInitializer {

    private static Window window;

    public static void init() {
        getWindow().addComponent(getTitleBoard());
        getWindow().addComponent(getMenu());
    }

    private static Menu getMenu() {
        int h = 6;
        int w = 24;
        int x = GameTitle.getTitleHeight() + 1;
        int y = (window.getInnerWidth() - w) / 2;
        Menu menu = new Menu(x, y, w, h);
        menu.addMenu("start", () -> {
            MessageBroker.publish(new GameStatusMessage(GameStatus.START));
            WindowPoolManager.unFocus(window);
        });
        menu.addMenu("leader board", () -> {
        });
        menu.addMenu("quit", WindowPoolManager::shutDown);
        return menu;
    }

    public static GameTitle getTitleBoard() {
        int x = 0;
        int y = 0;
        return new GameTitle(x, y, getWindow().getInnerWidth() - y,
            getWindow().getInnerHeight() - x,
            false);
    }

    private static Window getWindow() {
        if (window == null) {
            window = WindowPoolManager.addWindow();
        }
        return window;
    }
}