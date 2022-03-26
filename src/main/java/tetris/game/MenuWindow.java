package tetris.game;

import tetris.audio.GameAudioPlayer;
import tetris.window.Window;

public class MenuWindow extends Window {

    private final GameAudioPlayer player = GameAudioPlayer.getInstance();

    public MenuWindow(String name) {
        super(name);
    }

    public MenuWindow(int x, int y, int width, int height, boolean borderOn, String name) {
        super(x, y, width, height, borderOn, name);
    }

    public void onWindowFocused() {
        player.playMenuBgm();
    }

    public void onWindowUnfocused() {
//        player.pause();
    }

}
