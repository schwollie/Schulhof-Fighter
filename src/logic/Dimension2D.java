package logic;

public class Dimension2D {

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

    public void setHeight(double height) {
        this.height = height;
    }
}
