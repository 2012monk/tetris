package tetris.message;

public class Post<T> {

    private final T payload;
    private String name;

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

    public boolean is(Object post) {
        return this.getClass().equals(post.getClass());
    }
}
