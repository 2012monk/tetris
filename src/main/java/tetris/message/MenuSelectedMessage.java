package tetris.message;

public class MenuSelectedMessage extends Post<String> {

    public MenuSelectedMessage(String menuName) {
        super(menuName);
    }

    public boolean is(String name) {
        return getPayload().equals(name);
    }
}
