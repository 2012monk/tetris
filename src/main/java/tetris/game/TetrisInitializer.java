package tetris.game;

import tetris.ui.components.BlockCounter;
import tetris.ui.components.GameClockPanel;
import tetris.ui.components.GameTitle;
import tetris.ui.components.HelpMessage;
import tetris.ui.components.NextBlockBoard;
import tetris.ui.components.PausedAlert;
import tetris.ui.components.ScoreBoard;
import tetris.ui.components.TetrisBoardPanel;
import tetris.ui.game.GameWindow;
import tetris.ui.window.Window;
import tetris.ui.window.WindowPoolManager;

public class TetrisInitializer {

    private static final int BOARD_SIZE = 22;
    private static int xAlign;
    private static Window window;

    public static void initGameWindow() {
        window = new GameWindow(WindowPoolManager.getScreen());
        WindowPoolManager.addWindow(window);
        xAlign = GameTitle.getTitleHeight() + 1;
        window.addComponent(new GameTitle(0, 0, window.getInnerWidth(), window.getInnerHeight(),
            false));
        boardWindow();
        presentWindow();
        leftBoard();
    }

    private static void presentWindow() {
        int w = 17;
        int scoreH = 5;
        int boardHeight = 4;
        int y = (window.getInnerWidth() - BOARD_SIZE) / 2 + BOARD_SIZE + 2;
        window.addComponent(new ScoreBoard(xAlign, y, w, scoreH, true));
        window.addComponent(
            new NextBlockBoard(xAlign + scoreH, y, w, boardHeight, true));
        window.addComponent(new HelpMessage(xAlign + scoreH + boardHeight, y, w, 13, true));
        alertComponent();
    }

    private static void alertComponent() {
        int height = 3;
        int x = xAlign + height;
        int y = (WindowPoolManager.getScreen().getInnerWidth() - BOARD_SIZE) / 2;
        window.addComponent(new PausedAlert(x, y, BOARD_SIZE, height, true));
    }

    private static void leftBoard() {
        int w = 17;
        int y = (window.getInnerWidth() - BOARD_SIZE) / 2 - BOARD_SIZE + 3;
        window.addComponent(new GameClockPanel(xAlign, y, w, 3, true));
        window.addComponent(new BlockCounter(xAlign + 3, y, w, 19, true));
    }

    private static void boardWindow() {
        int y = (window.getInnerWidth() - BOARD_SIZE) / 2;
        TetrisBoardPanel board = new TetrisBoardPanel(xAlign, y, BOARD_SIZE, BOARD_SIZE);
        window.addComponent(board);
    }
}
