package tetris.components;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import tetris.constants.Char;
import tetris.helper.LeaderBoardManager;
import tetris.message.MenuSelectedMessage;

public class LeaderBoard extends TextArea {


    private static final String TITLE = "LEADER BOARD";
    private final List<Score> loadedScore = new ArrayList<>();

    public LeaderBoard(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        setHorizontalAlign(TextAlign.START);
    }

    @Override
    public void handleKey(Char chr) {
        publishMessage(new MenuSelectedMessage("mainMenu"));
    }

    @Override
    public void update() {
        writeScores();
        super.update();
    }

    private void load() {
        loadedScore.clear();
        LeaderBoardManager.loadScores()
            .forEach(this::addScore);
        loadedScore.sort(Comparator.comparingInt(s -> -s.score));
    }

    private void addScore(String s) {
        try {
            this.loadedScore.add(new Score(s));
        } catch (Exception ignore) {
        }
    }

    private void writeScores() {
        load();
        clearString();
        printTitle();
        for (int i = 0; i < Math.min(10, loadedScore.size()); i++) {
            String line = (i + 1) + ". " + loadedScore.get(i).format();
            writeString(line + "\n");
        }
        if (loadedScore.size() == 0) {
            writeString("Nothing");
        }
    }

    private void printTitle() {
        int padding = (getInnerWidth() - TITLE.length()) / 2;
        for (int i = 0; i < padding; i++) {
            writeString(" ");
        }
        writeString(TITLE + "\n");
    }

    static class Score {

        int score;
        String name;

        public Score(String raw) {
            String[] split = raw.split(":");
            name = split[0];
            score = Integer.parseInt(split[1]);
        }

        public String format() {
            return name + " " + score;
        }
    }
}
