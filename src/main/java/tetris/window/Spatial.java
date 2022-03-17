package tetris.window;

public interface Spatial {

    int getAbsoluteX();

    int getAbsoluteY();

    int getWidth();

    int getHeight();

    Spatial getParent();

    void setParent(Spatial parent);

    void clear();
}
