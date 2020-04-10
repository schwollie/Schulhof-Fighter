package graphics;

import display.Camera;
import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import logic.Transform;
import logic.Vector2;

import java.awt.*;

public abstract class Sprite extends GameComponent {

    protected boolean visible = true;
    protected Vector2 offset = new Vector2(0, 0);

    public Sprite(GameObject ref) {
        super(ref, ComponentType.Sprite);
    }

    @Override
    public abstract void Render(Graphics2D g, Camera cam);

    public void setVisibility(boolean visibility) {
        this.visible = visibility;
    }

    public void setGameObjectRef(GameObject gameObjectRef) {
        this.reference = gameObjectRef;
    }
}
