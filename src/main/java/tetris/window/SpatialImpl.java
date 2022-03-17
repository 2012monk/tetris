package tetris.window;

import tetris.console.Console;

public abstract class SpatialImpl implements Spatial {

    private static final String ERR_ILLEGAL_COORDINATE = "잘못된 좌표";
    private static final String ERR_INVALID_SIZE = "잘못된 크기";
    protected Spatial parent;
    protected boolean borderOn;
    private int x;
    private int y;
    private int width;
    private int height;

    public SpatialImpl(int x, int y, int width, int height) {
        this(x, y, width, height, true);
    }

    public SpatialImpl(Spatial parent, int x, int y, int width, int height) {
        this(x, y, width, height);
        this.parent = parent;
    }

    public SpatialImpl(int x, int y, int width, int height, boolean borderOn) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.borderOn = borderOn;
        verifyCoordinate();
    }

    private void verifyCoordinate() {
        if (y < 0 || x < 0) {
            throw new IllegalArgumentException(ERR_ILLEGAL_COORDINATE);
        }
    }

    // 상대좌표로 이동된 테두리를 포함한 공간이 부모 공간을 침범하지 않는지 테스트
    private void verifySpace() {
        if (!hasParent()) {
            return;
        }
        if (x + width > parent.getWidth() || y + height > parent.getHeight()) {
            String msg = ERR_INVALID_SIZE
                + "\nmax width: " + parent.getWidth()
                + " current: " + (x + width)
                + "\nmax height: " + parent.getHeight()
                + " current : " + (y + height);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public int getAbsoluteX() {
        if (parent == null) {
            return x;
        }
        return this.parent.getInnerX() + x;
    }

    @Override
    public int getAbsoluteY() {
        if (parent == null) {
            return y;
        }
        return this.parent.getInnerY() + y;
    }

    @Override
    public int getInnerX() {
        return getAbsoluteX() + getBorderCalibration();
    }

    @Override
    public int getInnerY() {
        return getAbsoluteY() + getBorderCalibration();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getInnerWidth() {
        return height - getBorderCalibration() * 2;
    }

    @Override
    public int getInnerHeight() {
        return width - getBorderCalibration() * 2;
    }

    public int getBorderCalibration() {
        if (borderOn) {
            return 1;
        }
        return 0;
    }

    @Override
    public Spatial getParent() {
        return this.parent;
    }

    @Override
    public void setParent(Spatial parent) {
        this.parent = parent;
        verifySpace();
    }

    @Override
    public void clear() {
        if (borderOn) {
            Console.drawBorder(this);
        }
        Console.clearArea(this);
    }

    @Override
    public boolean hasParent() {
        return parent != null;
    }
}
