package tetris.components;

import java.util.ArrayList;
import java.util.List;
import tetris.console.Console;
import tetris.window.Spatial;

public abstract class ComponentContainer extends ComponentImpl {

    protected List<Component> components = new ArrayList<>();

    public ComponentContainer(Spatial space) {
        super(space);
    }

    public ComponentContainer(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    protected void addComponent(Component component) {
        this.components.add(component);
        component.setParent(this.space);
    }

    @Override
    public void update() {
        Console.clearArea(space);
        components.forEach(Component::update);
    }
}
