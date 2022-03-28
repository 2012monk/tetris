package tetris.helper;

import java.util.ArrayList;
import java.util.List;

public class GameHelperManager {

    private static GameHelperManager instance;
    private final List<GameHelper> helpers = new ArrayList<>();

    private GameHelperManager() {
        helpers.add(ClockTickHelper.getInstance());
        helpers.add(AutoDropHelper.getInstance());
    }

    public static GameHelperManager getInstance() {
        if (instance == null) {
            instance = new GameHelperManager();
        }
        return instance;
    }

    public void start() {
        helpers.forEach(GameHelper::start);
    }

    public void stop() {
        helpers.forEach(GameHelper::stop);
    }

    public void pause() {
        helpers.forEach(GameHelper::pause);
    }

    public void resume() {
        helpers.forEach(GameHelper::resume);
    }
}
