package tetris.ui.message;

import tetris.system.MessageBroker;

public class GameClock {

    private int currentTick = 0;

    public void reset() {
        currentTick = 0;
        updateState();
    }

    public void increaseTick() {
        this.currentTick++;
        updateState();
    }

    public int getCurrentTick() {
        return currentTick;
    }

    private void updateState() {
        MessageBroker.publish(new ClockTickMessage(this));
    }
}
