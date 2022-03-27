package tetris.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import tetris.components.Tetromino;
import tetris.constants.Shape;
import tetris.constants.TetrominoPosition;
import tetris.constants.TetrominoStatus;
import tetris.constants.WallKickCorrectionValue;
import tetris.constants.WallKickData;

public class WallKickDataRepository {

    private static final Map<TetrominoStatus, Map<TetrominoStatus, List<WallKickCorrectionValue>>> origin = new HashMap<>();
    private static final Map<TetrominoStatus, Map<TetrominoStatus, List<WallKickCorrectionValue>>> IBlockData = new HashMap<>();


    public static WallKickData getLeftRotateData(Tetromino block) {
        TetrominoPosition position = block.getPosition();
        TetrominoPosition next = position.getLeftPosition();
        if (block.getShape() == Shape.I) {
            return getIData(position.getStatus(), next.getStatus());
        }
        return getData(position.getStatus(), next.getStatus());
    }

    public static WallKickData getRightRotateData(Tetromino block) {
        TetrominoPosition position = block.getPosition();
        TetrominoPosition next = position.getRightPosition();
        if (block.getShape() == Shape.I) {
            return getIData(position.getStatus(), next.getStatus());
        }
        return getData(position.getStatus(), next.getStatus());
    }

    public static void addData(TetrominoStatus from, TetrominoStatus to,
        List<WallKickCorrectionValue> values) {
        origin.putIfAbsent(from, new HashMap<>());
        origin.get(from).putIfAbsent(to, new ArrayList<>());
        origin.get(from).get(to).addAll(values);
    }

    public static void addIData(TetrominoStatus from, TetrominoStatus to,
        List<WallKickCorrectionValue> values) {
        IBlockData.putIfAbsent(from, new HashMap<>());
        IBlockData.get(from).putIfAbsent(to, new ArrayList<>());
        IBlockData.get(from).get(to).addAll(values);
    }

    private static WallKickData getIData(TetrominoStatus from, TetrominoStatus to) {
        if (!IBlockData.containsKey(from)) {
            throw new NoSuchElementException(from.name() + " " + to.name());
        }
        if (!IBlockData.get(from).containsKey(to)) {
            throw new NoSuchElementException(from.name() + " " + to.name());
        }
        return new WallKickData(from, to, IBlockData.get(from).get(to));
    }

    private static WallKickData getData(TetrominoStatus from, TetrominoStatus to) {
        if (!origin.containsKey(from)) {
            throw new NoSuchElementException(from.name() + " " + to.name());
        }
        if (!origin.get(from).containsKey(to)) {
            throw new NoSuchElementException(from.name() + " " + to.name());
        }
        return new WallKickData(from, to, origin.get(from).get(to));
    }
}
