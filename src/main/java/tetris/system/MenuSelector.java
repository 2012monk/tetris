package tetris.system;

import tetris.components.LeaderInputBoard;
import tetris.constants.GameStatus;
import tetris.message.GameStatusMessage;
import tetris.window.Window;
import tetris.window.WindowPoolManager;

public class MenuSelector {

    private static Window mainMenu;
    private static Window game;
    private static Window nameInputBoard;

    public static void init() {
        mainMenu = MenuInitializer.getMenuWindow();
        game = TetrisInitializer.getGameWindow();
        nameInputBoard = getLeaderBoardInput();
        mainMenu();
    }

    public static void mainMenu() {
        WindowPoolManager.focus(mainMenu);
    }

    public static void gameStart() {
        MessageBroker.publish(new GameStatusMessage(GameStatus.START));
        WindowPoolManager.focus(game);
    }

    public static void leaderBoardInput() {
        WindowPoolManager.focus(nameInputBoard);
    }

    public static void quit() {
        WindowPoolManager.shutDown();
    }

    private static Window getLeaderBoardInput() {
        int width = 40;
        int height = 10;
        int x = (WindowPoolManager.getScreen().getInnerHeight() - height) / 2;
        int y = (WindowPoolManager.getScreen().getInnerWidth() - width) / 2;
        Window window = new Window(x, y, width, height, true);
        window.addComponent(new LeaderInputBoard(0, 0, width - 2, height - 2));
        WindowPoolManager.addWindow(window);
        return window;
    }
}
