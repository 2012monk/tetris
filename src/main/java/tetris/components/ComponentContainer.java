package tetris.components;

import java.util.ArrayList;
import java.util.List;

public abstract class ComponentContainer extends ComponentImpl {

    protected List<Component> components = new ArrayList<>();

    public ComponentContainer(int x, int y, int width, int height, boolean borderOn,
        List<? extends Component> components) {
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

    public <T extends Component> void addComponent(T component) {
        this.components.add(component);
        component.setParent(this);
        update();
    }

    public <T extends Component> void addComponents(List<T> components) {
        this.components.addAll(components);
        components.forEach(c -> c.setParent(this));
        update(); // TODO 업데이트 순서와 refresh 순서 점검
    }

    @Override
    public void update() {
        clear();
        components.forEach(Component::update);
    }
}
