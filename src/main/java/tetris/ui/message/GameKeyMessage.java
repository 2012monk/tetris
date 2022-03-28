package tetris.ui.message;

import tetris.constants.BlockMovement;

public class GameKeyMessage extends Post<BlockMovement> {

    public GameKeyMessage(BlockMovement payload) {
        super(payload);
    }
}
