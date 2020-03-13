package logic;

public class Transform {

    private Vector2 position;
    private Vector2 scale;
    private double rotation;

    public Transform(double x, double y, double scaleX, double scaleY, double rotZ) {
        position = new Vector2(x, y);
        scale = new Vector2(scaleX, scaleY);
        rotation = rotZ;
    }

    public Transform(double x, double y) {
        position = new Vector2(x, y);
        scale = Vector2.ones;
        rotation = 1f;
    }

    public Transform() {
        this.position = Vector2.zero;
        this.scale = Vector2.ones;
        rotation = 0f;
    }

    public Transform(Vector2 pos) {
        this.position = pos;
        scale = Vector2.ones;
        rotation = 0f;
    }

    public Transform(Vector2 pos, Vector2 scale) {
        this.position = new Vector2(pos);
        this.scale = new Vector2(scale);
        rotation = 0f;
    }

    public Transform(Transform transform) {
        this.position = new Vector2(transform.position);
        this.scale = new Vector2(transform.scale);
        this.rotation = transform.rotation;
    }

    public double getX() { return this.position.getX(); }
    public double getY() { return this.position.getY(); }

    public Vector2 getScale() {
        return scale;
    }

    public double getXScale() { return  this.scale.getX(); }
    public double getYScale() { return  this.scale.getY(); }

    public Vector2 getPosition() { return this.position; }

    public void setPosition(Vector2 pos) {
        this.position = pos;
    }

    public Transform addPosition(Vector2 add) {
        Transform newTrans = new Transform(this);
        newTrans.setPosition(this.position.add(add));
        return newTrans;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vector2 scale) {
        this.scale = scale;
    }

    public void move(Vector2 offset) {
        this.position.add(offset);
    }

    public static Transform getEmpty() { return new Transform(0, 0); }

    @Override
    public String toString() {
        return "Transform{" +
                "position=" + position +
                ", size=" + scale +
                ", rotation=" + rotation +
                '}';
    }
}
