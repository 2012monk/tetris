package tetris.ui;

import tetris.ui.constants.Color;

public interface Spatial {

    int getAbsoluteX();

    int getAbsoluteY();

    int getInnerX();

    int getInnerY();

    int getInnerWidth();

    int getInnerHeight();

    int getRelativeX();

    int getRelativeY();

    int getWidth();

    int getHeight();

    boolean isInsideSpace(int x, int y);

    Spatial getParent();

    void setParent(Spatial parent);

    void clear();

    boolean hasParent();

    Color getFg();

    Color getBg();
}
