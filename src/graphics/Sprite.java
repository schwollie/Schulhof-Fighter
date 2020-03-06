package graphics;

import display.Camera;
import game.GameObject;
import logic.Transform;
import logic.Vector2;

import java.awt.*;

public abstract class Sprite {

    protected boolean visible = true;
    protected GameObject gameObjectRef;
    protected Vector2 offset = new Vector2(0, 0);

    /*public void paintComponent(Graohics2Dg) {
        if (this.visible) {
            draw(g);
        }
    }*/

    public abstract void draw(Graphics2D g, Camera cam);

    public void setVisibility(boolean visibility) {
        this.visible = visibility;
    }

    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }

    public Transform getTransform() {
        return gameObjectRef.getTransform();
    }

    public void setGameObjectRef(GameObject gameObjectRef) {
        this.gameObjectRef = gameObjectRef;
    }
}
