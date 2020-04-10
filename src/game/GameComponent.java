package game;

import display.Camera;
import logic.Transform;

import java.awt.*;

public abstract class GameComponent {

    protected GameObject reference;
    protected ComponentType type;
    // relative transform to GameObject
    protected Transform transform = Transform.getIdentity();

    public GameComponent(GameObject ref, ComponentType type) {
        this.reference = ref;
        this.type = type;
    }

    public void tick() {

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

    public void destroy() {
        reference.removeComponent(this);
    }


    public void setRelativeTransform(Transform transform) {
        this.transform = transform;
    }

    public Transform getRelativeTransform() {
        return transform;
    }

    public Transform getAbsoluteTransform() { return transform.add(reference.transform); }
}
