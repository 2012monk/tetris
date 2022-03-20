package tetris.components;

import tetris.constants.KeyCode;
import tetris.window.Spatial;

public interface Component extends Spatial {

    void update();

    void handleKey(KeyCode keyCode);
}
