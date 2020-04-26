package prefabs.gameobjects;

import gameobjects.GameObject;
import logic.Anchor;
import scenes.Scene;
import graphics.SpecifiedAnchor;
import graphics.ImageSprite;
import logic.Vector2;

public class Background extends GameObject {

    public Background(Scene scene) {
        super("Background", scene, 0);
        setup();
    }

    public void setup() {
        this.addComponent(new ImageSprite(this, 1,"images/Background/back1.png", new Anchor(SpecifiedAnchor.BottomLeft)));
        this.getTransform().setScale(new Vector2(4.5, 4.5));
        this.getTransform().setPosition(new Vector2(0, -.05));
    }
}
