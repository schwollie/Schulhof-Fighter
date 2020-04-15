package graphics;

import display.Camera;
import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import logic.Vector2;

import java.awt.*;

public abstract class Sprite extends GameComponent {

    protected boolean visible = true;
    protected Vector2 offset = new Vector2(0, 0);
    protected int layer;

    public Sprite(GameObject ref) {
        super(ref, ComponentType.Sprite);
        layer = ref.getLayer();
    }

    @Override
    public final void UpdateSprites(SpriteManager spriteManager) {
        spriteManager.addSprite(this);
    }

    public abstract void Render(Graphics2D g, Camera cam);

    public void setVisibility(boolean visibility) {
        this.visible = visibility;
    }

    public void setGameObjectRef(GameObject gameObjectRef) {
        this.reference = gameObjectRef;
    }
}
