package tetris.controller;

import tetris.annotations.OnMessage;
import tetris.constants.GameStatus;
import tetris.message.GameStatusMessage;
import tetris.message.MenuSelectedMessage;
import tetris.system.MessageBroker;
import tetris.window.Window;
import tetris.window.WindowPoolManager;

public class MenuController {

    private static final String MAIN_MENU = "mainMenu";
    private static final String GAME_MENU = "gameMenu";
    private static final String LEADER_BOARD_MENU = "leaderBoardMenu";
    private static final String QUIT = "quit";
    private static MenuController instance;

    private MenuController() {
        MessageBroker.subscribe(MenuSelectedMessage.class, this);
    }

    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }

    @OnMessage
    private void selectMenu(MenuSelectedMessage message) {
        if (message.getPayload().equals(QUIT)) {
            quit();
            return;
        }
        try {
            select(message.getPayload());
            startGame(message.getPayload());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGame(String name) {
        if (name.equals(GAME_MENU)) {
            MessageBroker.publish(new GameStatusMessage(GameStatus.START));
        }
    }

    public void quit() {
        WindowPoolManager.shutDown();
    }

    private void select(String name) {
        WindowPoolManager.focus(getWindow(name));
    }

    private Window getWindow(String name) {
        return WindowPoolManager.getWindow(name);
    }
}
