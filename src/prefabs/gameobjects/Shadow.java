package prefabs.gameobjects;

import gameobjects.GameObject;
import gameobjects.OnDestroyListener;
import graphics.ImageSprite;
import logic.Vector2;
import physics.Collider;
import physics.CollissionListener;
import scenes.Scene;

public class Shadow extends GameObject implements CollissionListener, OnDestroyListener {

    private GameObject gameObject2Follow;
    private ImageSprite shadowImg;

    private double scaleFactor = .5;

    private double currentY = 1000;
    private double currentX = 0;
    private double yOffset;

    private double maxScale = 0.45;

    public Shadow(String tag, Scene world, GameObject gameObject) {
        super(tag, world, 1);
        this.gameObject2Follow = gameObject;
        this.gameObject2Follow.getPhysicsComponent().getCollider().addListener(this);
        this.gameObject2Follow.listenOnDestroy(this);

        shadowImg = new ImageSprite(this, 1, "images/Others/shadow2.png");
        shadowImg.setAlpha(0.5);
        this.addComponent(shadowImg);

        world.addGameObject(this);

        setScale();

        yOffset = gameObject2Follow.getTransform().getYScale() / 2;
    }

    private void setScale() {
        double d = distMult();
        Vector2 scale = new Vector2(maxScale*d, maxScale*d*0.5);
        this.transform.setScale(scale);
    }

    private double distMult() {
        double d = Math.abs(currentY - gameObject2Follow.getTransform().getY());
        return  1 / (d*scaleFactor+1);
    }

    @Override
    public void Tick() {
        super.Tick();

        currentX = gameObject2Follow.getTransform().getX();

        this.transform.setPosition(new Vector2(currentX, currentY - yOffset + 0.035));
        setScale();

    }


    @Override
    public void onCollision(Collider c1, Collider c2) {
        if (c1.getGameObject().getTag().equals("ground") || c2.getGameObject().getTag().equals("ground")) {
            currentY = gameObject2Follow.getTransform().getY();
        }
    }

    @Override
    public void onDestroy() {
        this.destroy(0);
    }
}
