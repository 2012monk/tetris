package tetris.ui.game;

import static tetris.constants.GameStatus.END;
import static tetris.constants.GameStatus.RESTART;
import static tetris.constants.GameStatus.START;

import tetris.audio.GameAudioPlayer;
import tetris.constants.GameStatus;
import tetris.controller.TetrisGameController;
import tetris.exception.EndOfGameException;
import tetris.gameobject.TetrisBoard;
import tetris.helper.GameKeyHandler;
import tetris.ui.Spatial;
import tetris.ui.annotations.OnMessage;
import tetris.ui.constants.Char;
import tetris.ui.message.GameKeyMessage;
import tetris.ui.message.GameStatusMessage;
import tetris.ui.message.MenuSelectedMessage;
import tetris.ui.window.Window;

public class GameWindow extends Window {

    private final TetrisGameController controller;
    private final GameKeyHandler handler;
    private final GameAudioPlayer player = GameAudioPlayer.getInstance();

    public GameWindow(String name, Spatial screen) {
        super(0, 0, screen.getWidth(), screen.getHeight(), false, name);
        controller = new TetrisGameController(new TetrisBoard());
        handler = new GameKeyHandler();
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
        handler.handleKey(chr);
    }

    @OnMessage
    public void gamStatusMessage(GameStatusMessage message) {
        GameStatus status = message.getPayload();
        if (status == START) {
            start();
        }
        if (status == RESTART) {
            publishMessage(new GameStatusMessage(END));
            publishMessage(new GameStatusMessage(START));
        }
    }

    @OnMessage
    private void moveOrder(GameKeyMessage message) {
        try {
            controller.move(message.getPayload());
        } catch (EndOfGameException e) {
            endGame();
        }
    }

    private void start() {
        controller.resetGame();
    }

    private void endGame() {
        publishMessage(new GameStatusMessage(END));
        publishMessage(new MenuSelectedMessage("leaderBoardInputMenu"));
    }
}
