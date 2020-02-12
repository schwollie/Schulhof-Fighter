package graphics;

import logic.Transform;

import java.awt.*;

public abstract class Sprite {

    protected boolean visible = true;
    protected Transform transform;

    public void paintComponent(Graphics g) {
        if (this.visible) {
            draw(g);
        }
    }

    public abstract void draw(Graphics g);

    public void setVisibility(boolean visibility) {
        this.visible = visibility;
    }
}
