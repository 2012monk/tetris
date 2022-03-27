package tetris.ui.game;

import tetris.audio.GameAudioPlayer;
import tetris.controller.MenuController;
import tetris.ui.annotations.OnMessage;
import tetris.ui.message.MenuSelectedMessage;
import tetris.ui.window.Window;

public class MenuWindow extends Window {

    private static final String MENU_NAME = "mainMenu";
    private final MenuController controller = MenuController.getInstance();
    private final GameAudioPlayer player = GameAudioPlayer.getInstance();

    public MenuWindow(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn, MENU_NAME);
        subscribe(MenuSelectedMessage.class);
    }

    public void onWindowFocused() {
        player.playMenuBgm();
    }

    @OnMessage
    private void selectMenu(MenuSelectedMessage message) {
        controller.selectMenu(message.getPayload());
    }

}
