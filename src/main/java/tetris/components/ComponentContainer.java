package tetris.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tetris.constants.Char;

public abstract class ComponentContainer<T extends Component> extends ComponentImpl {

    protected List<T> components = Collections.synchronizedList(new ArrayList<>());

    public ComponentContainer(int x, int y, int width, int height, boolean borderOn,
        List<T> components) {
        this(x, y, width, height, borderOn);
        this.components.addAll(components);
        components.forEach(c -> c.setParent(this));
    }

    public ComponentContainer(int x, int y, int width, int height, boolean borderOn) {
        super(x, y, width, height, borderOn);
    }

    public ComponentContainer(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void addComponent(T component) {
        this.components.add(component);
        component.setParent(this);
    }

    public void addComponents(List<T> components) {
        this.components.addAll(components);
        components.forEach(c -> c.setParent(this));
    }

    @Override
    public void update() {
        if (!hasParent()) {
            return;
        }
        clear();
        components.forEach(Component::update);
    }

    @Override
    public void handleKey(Char chr) {
        this.components.forEach(c -> c.handleKey(chr));
    }
}
