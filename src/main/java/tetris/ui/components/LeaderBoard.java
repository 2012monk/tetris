package tetris.ui.components;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import tetris.helper.LeaderBoardIOManger;
import tetris.ui.constants.Char;
import tetris.ui.message.MenuSelectedMessage;

public class LeaderBoard extends TextArea {


    private static final String TITLE = "LEADER BOARD";
    private final List<String> scoreList = new ArrayList<>();

    public LeaderBoard(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        setHorizontalAlign(TextAlign.CENTER);
        setVerticalAlign(TextAlign.START);
    }

    @Override
    public void handleKey(Char chr) {
        publishMessage(new MenuSelectedMessage("mainMenu"));
    }

    @Override
    public void render() {
        writeScores();
        super.render();
    }

    private void load() {
        List<Score> loadedScore = new ArrayList<>();
        LeaderBoardIOManger.loadScores()
            .forEach(s -> addScore(s, loadedScore));
        loadedScore.sort(Comparator.comparingInt(s -> -s.score));
        for (int i = 0; i < 10; i++) {
            loadedScore.get(i).number = i + 1;
            scoreList.add(loadedScore.get(i).format());
        }
    }

    private void addScore(String s, List<Score> loadedScore) {
        try {
            loadedScore.add(new Score(s));
        } catch (Exception ignore) {
        }
    }

    private void writeScores() {
        load();
        clearString();
        writeString(TITLE + "\n");
        writeString(String.join("\n", scoreList));
        if (scoreList.size() == 0) {
            writeString("Nothing");
        }
    }

    static class Score {

        int number;
        int score;
        String name;

        public Score(String raw) {
            String[] split = raw.split(":");
            name = split[0];
            score = Integer.parseInt(split[1]);
        }

        public String format() {
            return number + ". " + name + " " + score;
        }
    }
}
