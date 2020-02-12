package logic;

public class Transform {

    private Vector2 position;
    private Vector2 scale;
    private float rotation;

    public Transform(float x, float y, float scaleX, float scaleY, float rotZ) {
        position = new Vector2(x, y);
        scale = new Vector2(scaleX, scaleY);
        rotation = rotZ;
    }

    public Transform(float x, float y) {
        position = new Vector2(x, y);
        scale = Vector2.ones;
        rotation = 1f;
    }

    public Transform(Vector2 v) {
        this.position = v;
        scale = Vector2.ones;
        rotation = 1f;
    }

    public double getX() { return this.position.getX(); }
    public double getY() { return this.position.getY(); }

    public Vector2 getPosition() { return this.position; }

    public void setPosition(Vector2 pos) {
        this.position = pos;
    }

    public void addPosition(Vector2 add) { this.position = this.position.add(add); }

    public void move(Vector2 offset) {
        this.position.add(offset);
    }

    public static Transform getEmpty() { return new Transform(0, 0); }

}
