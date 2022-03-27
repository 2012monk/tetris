package tetris.controller;

import tetris.constants.GameKey;
import tetris.exception.EndOfGameException;
import tetris.game.GameHelperManager;
import tetris.gameobject.TetrisGame;
import tetris.system.MessageBroker;
import tetris.ui.message.MenuSelectedMessage;

public class TetrisController {

    private final GameHelperManager helperManager = GameHelperManager.getInstance();
    private final TetrisGame game;

    public TetrisController(TetrisGame game) {
        this.game = game;
    }

    public void moveOrder(GameKey key) {
        try {
            game.move(key);
        } catch (EndOfGameException e) {
            helperManager.stop();
            MessageBroker.publish(new MenuSelectedMessage("leaderBoardInputMenu"));
        }
    }

    public void start() {
        game.resetGame();
        helperManager.start();
    }

    public void resume() {
        helperManager.start();
    }

    public void stop() {
        helperManager.stop();
    }
}
