package tetris.ui.game;

import static tetris.constants.GameStatus.END;
import static tetris.constants.GameStatus.PAUSE;
import static tetris.constants.GameStatus.RESUME;
import static tetris.constants.GameStatus.START;

import tetris.audio.GameAudioPlayer;
import tetris.constants.GameStatus;
import tetris.controller.TetrisController;
import tetris.gameobject.TetrisBoard;
import tetris.gameobject.TetrisGame;
import tetris.helper.GameKeyHandler;
import tetris.ui.Spatial;
import tetris.ui.annotations.OnMessage;
import tetris.ui.constants.Char;
import tetris.ui.message.GameKeyMessage;
import tetris.ui.message.GameStatusMessage;
import tetris.ui.window.Window;

public class GameWindow extends Window {

    private final TetrisController controller;
    private final GameKeyHandler keyHandler;
    private final GameAudioPlayer player = GameAudioPlayer.getInstance();

    public GameWindow(String name, Spatial screen) {
        super(0, 0, screen.getWidth(), screen.getHeight(), false, name);
        keyHandler = new GameKeyHandler();
        controller = new TetrisController(new TetrisGame(new TetrisBoard()));
        subscribe(GameKeyMessage.class);
        subscribe(GameStatusMessage.class);
    }

    @Override
    public void onWindowFocused() {
        publishMessage(new GameStatusMessage(START));
        player.playInGameBgm();
    }

    @Override
    public void handleKey(Char chr) {
        keyHandler.handleKey(chr);
    }

    @OnMessage
    public void gamStatusMessage(GameStatusMessage message) {
        GameStatus status = message.getPayload();
        if (status == START) {
            controller.start();
        }
        if (status == END || status == PAUSE) {
            controller.stop();
        }
        if (status == RESUME) {
            controller.resume();
        }
    }

    @OnMessage
    public void gameKey(GameKeyMessage message) {
        controller.moveOrder(message.getPayload());
    }
}
