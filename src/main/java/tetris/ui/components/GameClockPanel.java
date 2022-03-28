package tetris.ui.components;

import tetris.constants.GameStatus;
import tetris.ui.ComponentImpl;
import tetris.ui.annotations.OnMessage;
import tetris.ui.console.Console;
import tetris.ui.message.ClockTickMessage;
import tetris.ui.message.GameStatusMessage;

public class GameClockPanel extends ComponentImpl {

    private int tick = 0;

    public GameClockPanel(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        subscribe(GameStatusMessage.class);
        subscribe(ClockTickMessage.class);
    }

    @OnMessage
    public void onMessage(GameStatusMessage post) {
//        GameStatus status = post.getPayload();
//        if (status == GameStatus.START || status == GameStatus.END) {
//            initClock();
//        }
    }

    @OnMessage
    public void onTick(ClockTickMessage message) {
        this.tick = message.getPayload().getCurrentTick();
        render();
    }

    private void initClock() {
        this.tick = 0;
    }

    public void increaseTick() {
        this.tick += 1;
        render();
    }

    private String getTick() {
        int sec = tick % 60;
        int minute = (tick / 60) % 60;
        return String.format("%02d", minute) + ":" + String.format("%02d", sec);
    }

    @Override
    public void render() {
        if (!hasParent()) {
            return;
        }
        clear();
        String strTick = getTick();
        Console.drawString(getInnerX(),
            getInnerWidth() / 2 - strTick.length() / 2 + getInnerY(),
            strTick);
    }
}
