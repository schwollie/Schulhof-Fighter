package logic;

import graphics.SpecifiedAnchor;

public class Anchor {

    Vector2 anchorFactor = new Vector2(0,0);

    public Anchor() {}

    public Anchor(SpecifiedAnchor a) {
        setBySpecifiedAnchor(a);
    }

    public Anchor(double x, double y) {
        anchorFactor.setX(x);
        anchorFactor.setY(y);
    }

    public void setBySpecifiedAnchor(SpecifiedAnchor a) {
        switch (a) {
            case Center -> anchorFactor = new Vector2(0.5, 0.5);
            case BottomLeft -> anchorFactor = new Vector2(0, 1);
            case BottomRight -> anchorFactor = new Vector2(1, 1);
            case TopLeft -> anchorFactor = new Vector2(0, 0);
            case TopRight-> anchorFactor = new Vector2(1, 0);
        }
    }

    public double getXOffset(double width) {
        return width*anchorFactor.getX();
    }

    public double getYOffset(double height) {
        return height*anchorFactor.getY();
    }

}
