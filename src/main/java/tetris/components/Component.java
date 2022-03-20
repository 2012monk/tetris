package tetris.components;

import tetris.constants.Char;
import tetris.window.Spatial;

public interface Component extends Spatial {

    void update();

    void handleKey(Char chr);
}
