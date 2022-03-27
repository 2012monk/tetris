package tetris.components;

import tetris.annotations.OnMessage;
import tetris.console.Console;
import tetris.gameobject.Cell;
import tetris.message.BoardUpdateMessage;

public class TetrisBoardPanel extends MatrixBoard {

    private static final char EMPTY_SPACE = '.';

    public TetrisBoardPanel(int x, int y, int width, int height) {
        super(x, y, width, height, true);
        this.emptySpace = EMPTY_SPACE;
        subscribe(BoardUpdateMessage.class);
    }

    @OnMessage
    public void renderBoard(BoardUpdateMessage message) {
        updateCurrentState(message.getPayload().getBoardStatus());
        render();
    }

    private void printCell(Cell p) {
        Console.drawChar(p.getX() + getInnerX(), p.getY() * 2 + getInnerY(), p.getSpace(),
            fg, p.getColor());
        Console.drawChar(p.getX() + getInnerX(), p.getY() * 2 + 1 + getInnerY(), p.getSpace(),
            fg, p.getColor());
    }
}
