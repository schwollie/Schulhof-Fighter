package logic;


import java.io.Serializable;

public class Dimension2D implements Serializable {

    private double width;
    private double height;

    public Dimension2D(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public Dimension2D(Dimension2D d) {
        this.width = d.width;
        this.height = d.height;
    }

    public Dimension2D multiply(Dimension2D d) {
        return new Dimension2D(this.width * d.width, this.height * d.height);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public double getX() {
        return this.width;
    }

    public double getY() {
        return this.height;
    }

    public Vector2 asVector() {
        return new Vector2(width, height);
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setValues(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    public static Dimension2D valueOf(Vector2 vector) {
        return new Dimension2D(vector.getX(), vector.getY());
    }
}
