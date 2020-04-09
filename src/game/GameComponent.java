package game;

import display.Camera;

import java.awt.*;

public abstract class GameComponent {

    protected GameObject reference;
    protected ComponentType type;

    public GameComponent(GameObject ref, ComponentType type) {
        this.reference = ref;
        this.type = type;
    }

    public void tick(double deltaTime) {

    }

    public void Render(Graphics2D g, Camera cam) {
        // most components don't need to be rendered
    }

    public GameObject getReference() {
        return reference;
    }

    public ComponentType getType() {
        return type;
    }
}
