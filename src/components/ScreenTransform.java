package components;

import logic.Vector2;

public class ScreenTransform {

    // Coordinates in Screen space are given in percentage to the size of the screen
    private Vector2 pos;
    private Vector2 scale;

    public ScreenTransform(Vector2 pos, Vector2 scale) {
        this.pos = pos;
        this.scale = scale;
    }

    public ScreenTransform() {
        pos = new Vector2(0,0);
        scale = new Vector2(1, 1);
    }

    public void setRight(double factor) {
        pos.setX(1-factor);
    }

    public void setLeft(double factor) {
        pos.setX(factor);
    }

    public void setUp(double factor) {
        pos.setY(factor);
    }

    public void setDown(double factor) {
        pos.setY(1-factor);
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getScale() {
        return scale;
    }

    public void setScale(Vector2 scale) {
        this.scale = scale;
    }

    public ScreenTransform add(ScreenTransform other) {
        ScreenTransform newT = new ScreenTransform();
        newT.pos = other.getPos().add(this.pos.rowWiseMultiplication(other.scale));
        newT.scale = other.scale.rowWiseMultiplication(this.scale);
        return newT;
    }
}
