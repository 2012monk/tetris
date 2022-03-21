package tetris.components;

import tetris.constants.Char;
import tetris.system.Post;
import tetris.window.Spatial;

public interface Component extends Spatial {

    <T extends Post<?>> void onMessage(T post);

    void update();

    void handleKey(Char chr);
}
