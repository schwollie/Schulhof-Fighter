package display;

import components.GuiCanvas;
import components.ScreenTransform;
import game.Consts;
import game.GameObject;
import game.Scene;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;

import java.awt.*;
import java.io.Serializable;

public class Camera extends GameObject implements Serializable {

    private final Canvas canvas;

    private final double ratio = Consts.ratio;
    private final double scale = 4;
    private final Dimension2D resolution = new Dimension2D(Consts.windowWidth, Consts.windowHeight);

    public Camera(Scene scene, Vector2 pos) {
        super("Camera", scene);
        this.transform.setPosition(pos);
        canvas = new Canvas();
        canvas.setCam(this);
    }

    public void RenderScene(Graphics2D g) {
        scene.Render(g, this);
    }

    @Override
    public void Tick() {
        super.Tick();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Dimension2D getResolution() {
        return resolution;
    }

    public Shape getClip() {
        Rectangle clipShape = new Rectangle(0, 0, (int) this.resolution.getWidth(), (int) this.resolution.getHeight());
        return clipShape;
    }

    public Transform world2Screen(Transform trans) {
        Transform screen = new Transform();

        double xFactor = resolution.getWidth() / scale;
        double yFactor = resolution.getHeight() / scale * ratio;

        // new position
        Vector2 newPos = trans.getPosition().subtract(this.transform.getPosition());
        newPos.setX(newPos.getX() * xFactor);
        newPos.setY(-newPos.getY() * yFactor);
        screen.setPosition(newPos);

        // new Scale
        Vector2 newScale = new Vector2(trans.getScale());
        newScale.setX(newScale.getX() * xFactor);
        newScale.setY(newScale.getY() * yFactor);
        screen.setScale(newScale);

        return screen;
    }

    public Transform gui2Screen(ScreenTransform trans) {
        Transform screen = new Transform();

        double xFactor = resolution.getWidth();
        double yFactor = resolution.getHeight() * GuiCanvas.defaultRatio;

        // new position
        Vector2 newPos = new Vector2(trans.getPos());
        newPos.setX(newPos.getX() * xFactor);
        newPos.setY(newPos.getY() * yFactor);
        screen.setPosition(newPos);

        // new Scale
        Vector2 newScale = new Vector2(trans.getScale());
        newScale.setX(newScale.getX() * xFactor);
        newScale.setY(newScale.getY() * yFactor);
        screen.setScale(newScale);

        return screen;
    }

}
