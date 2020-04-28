package graphics;

import display.Camera;
import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import logic.Anchor;
import logic.Vector2;

import java.awt.*;

public abstract class Sprite extends GameComponent implements Drawable {

    protected boolean visible = true;
    protected Vector2 offset = new Vector2(0, 0);
    protected int layer;
    protected float alpha = 1f;

    protected Anchor anchor = new Anchor(SpecifiedAnchor.TopLeft);

    public Sprite(GameObject ref) {
        super(ref, ComponentType.Sprite);
        layer = ref.getLayer();
    }

    public void setVisibility(boolean visibility) {
        this.visible = visibility;
    }

    public void setGameObjectRef(GameObject gameObjectRef) {
        this.reference = gameObjectRef;
    }

    public void setAlpha(double alpha) {
        this.alpha = (float)alpha;
    }

    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }


    @Override
    public final void UpdateDrawables(RenderManager renderManager) {
        renderManager.addDrawable(this);
    }

    @Override
    public abstract void Render(Graphics2D g, Camera cam);

    @Override
    public int getRenderLayer() {
        return layer;
    }

    @Override
    public void setRenderLayer(int layer) {
        this.layer = layer;
    }

    public void setAnchor(Anchor a) {
        this.anchor = a;
    }

}
