package tetris.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import tetris.constants.TetrominoPosition;
import tetris.constants.TetrominoStatus;

public class PositionRepository {

    private static final List<TetrominoPosition> positions = new ArrayList<>();

    public static void addPosition(TetrominoPosition position) {
        positions.add(position);
    }

    public static TetrominoPosition getInitialPosition() {
        return positions.stream().filter(p -> p.getStatus() == TetrominoStatus.SPAWN_STATE)
            .findFirst().orElseThrow(NoSuchElementException::new);
    }

    public static TetrominoPosition getPosition(TetrominoStatus status) {
        return positions.stream().filter(p -> p.getStatus() == status)
            .findFirst().orElseThrow(NoSuchElementException::new);
    }

}
