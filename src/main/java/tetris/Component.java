package tetris;

import tetris.constants.Char;

public interface Component extends Spatial {

    void update();

    void handleKey(Char chr);
}
