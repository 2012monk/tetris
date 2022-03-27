package tetris.game;

import tetris.helper.AutoDropHelper;
import tetris.helper.ClockTickHelper;

public class GameHelperManager {

    private static GameHelperManager instance;
    private final ClockTickHelper clock = ClockTickHelper.getInstance();
    private final AutoDropHelper dropper = AutoDropHelper.getInstance();

    public static GameHelperManager getInstance() {
        if (instance == null) {
            instance = new GameHelperManager();
        }
        return instance;
    }

    public void start() {
        clock.startGameClock();
        dropper.start();
    }

    public void stop() {
        clock.stopClock();
        dropper.stopTask();
    }
}
