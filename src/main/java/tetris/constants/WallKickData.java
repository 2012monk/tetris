package tetris.constants;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import tetris.exception.OutOfDataException;
import tetris.gameobject.Tetromino;

public class WallKickData {

    private static final int DATA_SIZE = 5;
    private static final String ERR_INVALID_SIZE = "갯수는 " + DATA_SIZE + "개 입니다.";
    private final TetrominoStatus from;
    private final TetrominoStatus to;
    private final List<WallKickCorrectionValue> values = new ArrayList<>();
    private final ArrayDeque<WallKickCorrectionValue> queue = new ArrayDeque<>();

    public WallKickData(TetrominoStatus from, TetrominoStatus to,
        List<WallKickCorrectionValue> wallKickCorrectionValues) {
        this.to = to;
        this.from = from;
        verifyValueSize(wallKickCorrectionValues);
        this.values.addAll(wallKickCorrectionValues);
        init();
    }

    private void verifyValueSize(List<WallKickCorrectionValue> va) {
        if (va.size() != DATA_SIZE) {
            throw new IllegalArgumentException(ERR_INVALID_SIZE);
        }
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }

    public void correctPosition(Tetromino block) {
        queue.getFirst().correctPosition(block);
    }

    public void reversePosition(Tetromino copied) {
        queue.getFirst().reversePosition(copied);
    }

    public WallKickData next() {
        if (queue.isEmpty()) {
            throw new OutOfDataException();
        }
        queue.removeFirst();
        return this;
    }

    private void init() {
        this.queue.clear();
        this.queue.addAll(values);
    }
}
