package tetris.window;

public class Space extends SpatialImpl {

    public Space(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
    }

    public Space(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Space(Spatial parent, int x, int y, int width, int height) {
        this(x, y, width, height);
        setParent(parent);
    }

}
