package tetris.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderBoardManager {

    private static final String DEFAULT_LOCATION = "./scores.txt";

    public static void saveScore(String name, int score) {
        File file = loadFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(name + ":" + score + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public static List<String> loadScores() {
        File file = loadFile();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    private static File loadFile() {
        try {
            File tmp = new File(DEFAULT_LOCATION);
            if (!tmp.exists()) {
                return tmp;
            }
            if (tmp.createNewFile()) {
                throw new IllegalArgumentException();
            }
            return tmp;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}
