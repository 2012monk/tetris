package tetris.system;

import tetris.controller.MenuController;
import tetris.ui.components.GameTitle;
import tetris.ui.components.LeaderBoard;
import tetris.ui.components.LeaderInputBoard;
import tetris.ui.components.Menu;
import tetris.ui.game.MenuWindow;
import tetris.ui.window.Window;
import tetris.ui.window.WindowPoolManager;

public class MenuInitializer {

    private static final MenuController controller = MenuController.getInstance();
    private static Window mainMenuWindow;
    private static Window leaderBoardWindow;

    public static void initMenus() {
        getMainWindow().addComponent(getTitleBoard());
        getMainWindow().addComponent(getMenu());
        createLeaderBoardInputWindow();
        createLeaderBoardWindow();
        WindowPoolManager.focus(getMainWindow());
    }

    private static Menu getMenu() {
        int h = 6;
        int w = 24;
        int x = GameTitle.getTitleHeight() + 1;
        int y = (mainMenuWindow.getInnerWidth() - w) / 2;
        Menu menu = new Menu(x, y, w, h);
        menu.addMenuItem("gameMenu", "start");
        menu.addMenuItem("leaderBoardMenu", "leader board");
        menu.addMenuItem("quit");
        return menu;
    }

    public static GameTitle getTitleBoard() {
        int x = 0;
        int y = 0;
        return new GameTitle(x, y, WindowPoolManager.getScreen().getInnerWidth() - y,
            WindowPoolManager.getScreen().getInnerHeight() - x,
            false);
    }

    public static void createLeaderBoardWindow() {
        int w = 26;
        int h = 13;
        if (leaderBoardWindow == null) {
            leaderBoardWindow = WindowPoolManager.addWindow("leaderBoardMenu");
        }
        int x = GameTitle.getTitleHeight();
        int y = (leaderBoardWindow.getInnerWidth() - w) / 2;
        leaderBoardWindow.addComponent(getTitleBoard());
        leaderBoardWindow.addComponent(new LeaderBoard(x, y, w, h, true));
    }

    private static void createLeaderBoardInputWindow() {
        int width = 40;
        int height = 10;
        int x = (WindowPoolManager.getScreen().getInnerHeight() - height) / 2;
        int y = (WindowPoolManager.getScreen().getInnerWidth() - width) / 2;
        Window window = new Window(x, y, width, height, true, "leaderBoardInputMenu");
        window.addComponent(new LeaderInputBoard(0, 0, width - 2, height - 2));
        WindowPoolManager.addWindow(window);
    }


    private static Window getMainWindow() {
        if (mainMenuWindow == null) {
            mainMenuWindow = new MenuWindow(0, 0, WindowPoolManager.getScreen().getInnerWidth(),
                WindowPoolManager.getScreen().getInnerHeight(), false, "mainMenu");
            WindowPoolManager.addWindow(mainMenuWindow);
        }
        return mainMenuWindow;
    }
}