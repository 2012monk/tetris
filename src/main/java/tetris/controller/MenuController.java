package tetris.controller;

import tetris.audio.GameAudioPlayer;
import tetris.system.MessageBroker;
import tetris.ui.message.MenuSelectedMessage;
import tetris.ui.window.Window;
import tetris.ui.window.WindowPoolManager;

public class MenuController {

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

    public void selectMenu(String name) {
        if (name.equals(QUIT)) {
            quit();
            return;
        }
        try {
            select(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void quit() {
        GameAudioPlayer.getInstance().shutDown();
        WindowPoolManager.shutDown();
    }

    private void select(String name) {
        WindowPoolManager.focus(getWindow(name));
    }

    private Window getWindow(String name) {
        return WindowPoolManager.getWindow(name);
    }
}
