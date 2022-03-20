package tetris.components;

import tetris.constants.Char;
import tetris.window.SpatialImpl;

public abstract class ComponentImpl extends SpatialImpl implements Component {

    private static final int MINIMUM_SIZE = 1;
    private static final int DEFAULT_COORDINATE = 0;

    public ComponentImpl(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public ComponentImpl(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
    }

    protected ComponentImpl(int x, int y) {
        super(x, y, MINIMUM_SIZE, MINIMUM_SIZE);
    }


    protected ComponentImpl() {
        this(DEFAULT_COORDINATE, DEFAULT_COORDINATE, MINIMUM_SIZE, MINIMUM_SIZE);
    }

    @Override
    public void handleKey(Char chr) {
    }
}
