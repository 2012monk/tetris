package tetris.components;

public class GameTitle extends TextArea {


    private static final String TITLE = "\n"
        + " ________  ________  ________  _______   ______   ______  \n"
        + "/        |/        |/        |/       \\ /      | /      \\ \n"
        + "$$$$$$$$/ $$$$$$$$/ $$$$$$$$/ $$$$$$$  |$$$$$$/ /$$$$$$  |\n"
        + "   $$ |   $$ |__       $$ |   $$ |__$$ |  $$ |  $$ \\__$$/ \n"
        + "   $$ |   $$    |      $$ |   $$    $$<   $$ |  $$      \\ \n"
        + "   $$ |   $$$$$/       $$ |   $$$$$$$  |  $$ |   $$$$$$  |\n"
        + "   $$ |   $$ |_____    $$ |   $$ |  $$ | _$$ |_ /  \\__$$ |\n"
        + "   $$ |   $$       |   $$ |   $$ |  $$ |/ $$   |$$    $$/ \n"
        + "   $$/    $$$$$$$$/    $$/    $$/   $$/ $$$$$$/  $$$$$$/  \n"
        + "                                                          \n";

    public GameTitle(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
        initTitle();
    }

    public static int getTitleHeight() {
        return TITLE.split("\n").length;
    }

    public void initTitle() {
        writeString(TITLE);
    }
}
