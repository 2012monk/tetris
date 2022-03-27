package tetris.controller;

import tetris.audio.GameAudioPlayer;
import tetris.system.MessageBroker;
import tetris.ui.annotations.OnMessage;
import tetris.ui.message.MenuSelectedMessage;
import tetris.ui.window.Window;
import tetris.ui.window.WindowPoolManager;

public class MenuController {

    private static final String MAIN_MENU = "mainMenu";
    private static final String GAME_MENU = "gameMenu";
    private static final String LEADER_BOARD_MENU = "leaderBoardMenu";
    private static final String QUIT = "quit";
    private static MenuController instance;
    private final GameAudioPlayer player = GameAudioPlayer.getInstance();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        WindowPoolManager.shutDown();
        GameAudioPlayer.getInstance().shutDown();
    }

    private void select(String name) {
        WindowPoolManager.focus(getWindow(name));
    }

    private Window getWindow(String name) {
        return WindowPoolManager.getWindow(name);
    }
}
