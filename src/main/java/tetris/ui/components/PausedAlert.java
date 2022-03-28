package tetris.ui.components;


import tetris.constants.GameStatus;
import tetris.ui.annotations.OnMessage;
import tetris.ui.constants.Color;
import tetris.ui.message.GameStatusMessage;

public class PausedAlert extends TextArea {

    private static final String MESSAGE = "PAUSED";

    public PausedAlert(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        writeString(MESSAGE);
        subscribe(GameStatusMessage.class);
        setFg(Color.YELLOW);
    }

    @Override
    public void render() {
    }

    @OnMessage
    private void onMessage(GameStatusMessage statusMessage) {
        if (statusMessage.getPayload() == GameStatus.PAUSE) {
            super.render();
        }
    }
}
