package logic;


import java.awt.*;

public class Vector2 {

    public static final Vector2 zero = new ImmutableVector2(0, 0);
    public static final Vector2 ones = new ImmutableVector2(1, 1);

    private double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {}

    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void setValues(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void normalize() {
        double len = getLength();
        if (len == 0) { return; }

        double factor = 1/len;
        this.x = x*factor;
        this.y = y*factor;
    }

    public Vector2 getNormalized() {
        Vector2 newV = new Vector2(this.x, this.y);
        newV.normalize();
        return newV;
    }

    public double getLength() {
        return Math.sqrt(this.x*this.x + this.y*this.y);
    }

    public double getLengthSquared() {
        return (this.x*this.x + this.y*this.y);
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(this.x + v.getX(), this.y + v.getY());
    }

    public Vector2 subtract(Vector2 v) { return new Vector2(this.x - v.getX(), this.y - v.getY()); }

    public Vector2 rowWiseMultiplication(Vector2 other) {
        return new Vector2(this.getX()*other.getX(),  this.getY()*other.getY());
    }

    public Vector2 scalarMult(double a) {
        return new Vector2(a*this.getX(), a*this.getY());
    }

    public double dotProduct(Vector2 other) {
        return other.getX() * this.getX() + other.getY() * this.getY();
    }

    public static double dotProduct(Vector2 a, Vector2 b) {
        return a.dotProduct(b);
    }

    public Vector2 rotateVec(double alpha) {
        double newX = Math.cos(alpha) * this.getX() - Math.sin(alpha) * this.getY();
        double newY = Math.sin(alpha) * this.getX() - Math.cos(alpha) * this.getY();
        return new Vector2(newX, newY);
    }

    public double getDistance(Vector2 other) {
        return Math.sqrt( Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2));
    }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    public double getX() { return this.x; }
    public double getY() { return this.y; }

    public void print() {
        System.out.println("(" + this.getX() + " | " + this.getY() + ")" + "    Length: " + this.getLength());
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2) {
            Vector2 other = (Vector2)obj;
            if (other.getX()==this.x && other.getY() == this.y) { return true; }
            return false;
        } else { return false; }
    }
}

class ImmutableVector2 extends Vector2 {

    public ImmutableVector2(double x, double y) {
        super(x, y);
    }

    @Override
    public void normalize() {
        throw new IllegalStateException("Read only");
    }

    @Override
    public void setX(double x) {
        throw new IllegalStateException("Read only");
    }

    @Override
    public void setY(double y) {
        throw new IllegalStateException("Read only");
    }
}
