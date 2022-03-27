package tetris.ui;

import tetris.ui.constants.Char;

public interface Component extends Spatial {

    void render();

    void handleKey(Char chr);
}
