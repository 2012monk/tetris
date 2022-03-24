package tetris.system;

import tetris.components.GameTitle;
import tetris.components.LeaderBoard;
import tetris.components.Menu;
import tetris.window.Window;
import tetris.window.WindowPoolManager;

public class MenuInitializer {

    private static Window window;
    private static Window leaderBoardWindow;

    public static Window getMenuWindow() {
        if (window == null) {
            init();
        }
        return window;
    }

    public static Window getLeaderBoardWindow() {
        if (leaderBoardWindow == null) {
            getLeaderBoard();
        }
        return leaderBoardWindow;
    }

    private static void init() {
        getWindow().addComponent(getTitleBoard());
        getWindow().addComponent(getMenu());
    }

    private static Menu getMenu() {
        int h = 6;
        int w = 24;
        int x = GameTitle.getTitleHeight() + 1;
        int y = (window.getInnerWidth() - w) / 2;
        Menu menu = new Menu(x, y, w, h);
        menu.addMenuItem("start", MenuSelector::gameStart);
        menu.addMenuItem("leader board", MenuSelector::leaderBoard);
        menu.addMenuItem("quit", MenuSelector::quit);
        return menu;
    }

    public static GameTitle getTitleBoard() {
        int x = 0;
        int y = 0;
        return new GameTitle(x, y, getWindow().getInnerWidth() - y,
            getWindow().getInnerHeight() - x,
            false);
    }

    public static void getLeaderBoard() {
        int w = 26;
        int h = 13;
        if (leaderBoardWindow == null) {
            leaderBoardWindow = WindowPoolManager.addWindow();
        }
        int x = GameTitle.getTitleHeight();
        int y = (leaderBoardWindow.getInnerWidth() - w) / 2;
        leaderBoardWindow.addComponent(getTitleBoard());
        leaderBoardWindow.addComponent(new LeaderBoard(x, y, w, h, true));
    }

    private static Window getWindow() {
        if (window == null) {
            window = WindowPoolManager.addWindow();
        }
        return window;
    }
}