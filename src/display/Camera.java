package display;

import game.Consts;
import game.GameObject;
import game.Scene;
import logic.Dimension2D;
import logic.Transform;
import logic.Vector2;

import java.awt.*;

public class Camera extends GameObject {

    private final Canvas canvas;
    private final double ratio = Consts.ratio;
    private final double scale = 6;
    private final Dimension2D ScreenSize = new Dimension2D(Consts.windowWidth, Consts.windowHeight);

    public Camera(Scene scene) {
        super("Camera", scene);
        canvas = new Canvas();
        canvas.setCam(this);
    }

    public void RenderScene(Graphics2D g) {
        scene.Render(g, this);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Dimension2D getScreenSize() {
        return ScreenSize;
    }

    public Shape getClip() {
        Rectangle clipShape = new Rectangle(0, 0, (int) this.ScreenSize.getWidth(), (int) this.ScreenSize.getHeight());
        return clipShape;
    }

    public Transform worldToScreen(Transform trans) {
        Transform screen = new Transform();

        double xFactor = ScreenSize.getWidth() / scale;
        double yFactor = ScreenSize.getHeight() / scale * ratio;

        // new position
        Vector2 newPos = trans.getPosition().subtract(this.transform.getPosition());
        newPos.setX(newPos.getX() * xFactor);
        newPos.setY(newPos.getY() * yFactor);
        screen.setPosition(newPos);

        // new Scale
        Vector2 newScale = new Vector2(trans.getScale());
        newScale.setX(newScale.getX() * xFactor);
        newScale.setY(newScale.getY() * yFactor);
        screen.setScale(newScale);

        // rot
        return screen;
    }

}
