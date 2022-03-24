package tetris;

import tetris.constants.Char;
import tetris.message.Post;

public interface Component extends Spatial {

    <T extends Post<?>> void onMessage(T post);

    void update();

    void handleKey(Char chr);
}
