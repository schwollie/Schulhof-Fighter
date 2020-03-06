package display;

import game.Consts;
import graphics.Sprite;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Camera {

    // Todo: make all sprites into one coodirnate system not linked to pixels

    private Canvas canvas;
    private Vector2 position = new Vector2(0, 0); // world coordinates
    private Dimension2D worldSize = new Dimension2D(6, 4); // world coordinates
    private Dimension2D ScreenSize = new Dimension2D(Consts.windowWidth, Consts.windowHeight);

    public Camera() {
        canvas = new Canvas();
        canvas.setCam(this);
    }

    public void setVisibleSprites(ArrayList<Sprite> sprites) {
        this.canvas.setSprites(sprites);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Dimension2D getScreenSize() {
        return ScreenSize;
    }

    public void setScale(Dimension2D size) {
        this.worldSize = size;
    }

    public Shape getClip() {
        Rectangle clipShape = new Rectangle(0, 0, (int)this.ScreenSize.getWidth(), (int)this.ScreenSize.getHeight());
        return  clipShape;
    }

    public Transform worldToScreen(Transform transform) {
        Transform screen = new Transform();

        double xFactor =  ScreenSize.getWidth() / worldSize.getWidth();
        double yFactor = ScreenSize.getHeight() / worldSize.getHeight();

        // new position
        Vector2 newPos = transform.getPosition().subtract(this.position);
        newPos.setX(newPos.getX() * xFactor);
        newPos.setY(newPos.getY() * yFactor);
        screen.setPosition(newPos);

        // new Scale
        Vector2 newScale = new Vector2(transform.getSize());
        newScale.setX(newScale.getX() * xFactor);
        newScale.setY(newScale.getY() * yFactor);
        screen.setSize(newScale);

        // rot
        return screen;
    }

}
