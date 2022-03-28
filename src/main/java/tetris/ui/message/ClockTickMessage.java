package tetris.ui.message;

public class ClockTickMessage extends Post<GameClock> {

    public ClockTickMessage(GameClock payload) {
        super(payload);
    }
}
