package tetris.controller;

import tetris.constants.BlockMovement;
import tetris.exception.EndOfGameException;
import tetris.gameobject.TetrisGame;
import tetris.helper.GameHelperManager;
import tetris.ui.message.MenuSelectedMessage;
import tetris.ui.system.MessageBroker;

public class TetrisController {

    private final GameHelperManager helperManager = GameHelperManager.getInstance();
    private final TetrisGame game;

    public TetrisController(TetrisGame game) {
        this.game = game;
    }

    public void moveOrder(BlockMovement key) {
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
        helperManager.resume();
    }

    public void stop() {
        helperManager.stop();
    }
}
