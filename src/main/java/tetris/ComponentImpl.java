package tetris;

import tetris.annotations.OnMessage;
import tetris.constants.Char;
import tetris.message.Post;
import tetris.system.MessageBroker;

public abstract class ComponentImpl extends SpatialImpl implements Component {

    private static final int MINIMUM_SIZE = 0;
    private static final int DEFAULT_COORDINATE = 0;

    public ComponentImpl(int x, int y, int width, int height) {
        super(x, y, width, height, false);
    }

    public ComponentImpl(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
    }

    protected ComponentImpl(int x, int y) {
        super(x, y, MINIMUM_SIZE, MINIMUM_SIZE);
    }


    protected ComponentImpl() {
        this(DEFAULT_COORDINATE, DEFAULT_COORDINATE, MINIMUM_SIZE, MINIMUM_SIZE);
    }

    protected void publishMessage(Post<?> post) {
        MessageBroker.publish(post);
    }

    protected void subscribe(Class<? extends Post<?>> post) {
        MessageBroker.subscribe(post, this);
    }

    @Override
    public void handleKey(Char chr) {
    }
}
