package tetris.window;

import tetris.console.Console;

public class Window {

    private Rectangle border;

    private boolean activateBorder = true;

    public Window(int x, int y, int width, int height) {
        this.border = new Rectangle(x, y, width, height);
    }

    public Window(Rectangle rectangle) {
        this.border = rectangle;
    }

    public Window(int width, int height) {
        this.border = new Rectangle(width, height);
    }


    public void paint() {
        if (activateBorder) {
            Console.drawBorder(this.border);
        }
        paintPanel();
    }

    public void paintPanel() {
        for (int i = 1; i < this.border.getWidth(); i++) {
            for (int j = 1; j < this.border.getHeight(); j++) {
                Console.addCell(i, j);
            }
        }
    }
}
