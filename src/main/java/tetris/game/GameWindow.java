package tetris.game;

import static tetris.constants.GameStatus.END;
import static tetris.constants.GameStatus.RESTART;
import static tetris.constants.GameStatus.START;

import tetris.Spatial;
import tetris.annotations.OnMessage;
import tetris.audio.GameAudioPlayer;
import tetris.constants.Char;
import tetris.constants.GameStatus;
import tetris.controller.TetrisGameController;
import tetris.exception.EndOfGameException;
import tetris.message.GameKeyMessage;
import tetris.message.GameStatusMessage;
import tetris.message.MenuSelectedMessage;
import tetris.model.TetrisBoard;
import tetris.window.Window;

public class GameWindow extends Window {

    private final TetrisGameController controller;
    private final GameAudioPlayer player = GameAudioPlayer.getInstance();

    public GameWindow(String name, Spatial screen) {
        super(0, 0, screen.getWidth(), screen.getHeight(), false, name);
        controller = new TetrisGameController(new TetrisBoard());
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
        super.handleKey(chr);
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
