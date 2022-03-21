package tetris.system;

public class Post<T> {

    private String name;
    private T payload;

    public Post(String name, T payload) {
        this.name = name;
        this.payload = payload;
    }

    public Post(T payload) {
        this.payload = payload;
    }

    public String getName() {
        return name;
    }

    public T getPayload() {
        return payload;
    }
}
