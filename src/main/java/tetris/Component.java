package tetris;

import tetris.constants.Char;

public interface Component extends Spatial {

    void render();

    void handleKey(Char chr);
}
