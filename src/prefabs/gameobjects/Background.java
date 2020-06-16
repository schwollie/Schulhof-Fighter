package prefabs.gameobjects;

import gameobjects.GameObject;
import graphics.ImageSprite;
import graphics.SpecifiedAnchor;
import logic.Anchor;
import logic.Vector2;
import scenes.Scene;

public class Background extends GameObject {

    public Background(Scene scene) {
        super("Background", scene, 0);
        setup();
    }

    public void setup() {
        this.addComponent(new ImageSprite(this, 1, "images/Background/back1.png", new Anchor(SpecifiedAnchor.BottomLeft)));
        this.getTransform().setScale(new Vector2(5.4, 5.4));
        this.getTransform().setPosition(new Vector2(-.4, -.55));
    }
}
