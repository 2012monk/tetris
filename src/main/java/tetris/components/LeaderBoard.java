package tetris.components;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import tetris.constants.Char;
import tetris.helper.LeaderBoardManager;
import tetris.system.MenuSelector;

public class LeaderBoard extends TextArea {


    private static final String TITLE = "LEADER BOARD";
    private final List<Score> loadedScore = new ArrayList<>();

    public LeaderBoard(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        setHorizontalAlign(TextAlign.START);
    }

    @Override
    public void handleKey(Char chr) {
        MenuSelector.mainMenu();
    }

    @Override
    public void update() {
        writeScores();
        super.update();
    }

    private void load() {
        loadedScore.clear();
        LeaderBoardManager.loadScores()
            .stream()
            .filter(s -> s.contains(":"))
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
        for (int i = 0; i < loadedScore.size(); i++) {
            String line = (i + 1) + ". " + loadedScore.get(i).format();
            writeString(line + "\n");
        }
        if (loadedScore.size() == 0) {
            writeString("Nothing");
        }
    }

    private void printTitle() {
        for (int i = 0; i < ((getInnerWidth() - TITLE.length()) / 2); i++) {
            writeString(" ");
        }
        writeString(TITLE + "\n");
    }

    static class Score {

        int score;
        String name;

        public Score(String raw) {
            String[] splitted = raw.split(":");
            name = splitted[0];
            score = Integer.parseInt(splitted[1]);
        }

        public String format() {
            return name + " " + score;
        }
    }
}
