package display;

import components.HUDCanvas;
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
    private final HUDCanvas HUD;
    private final double ratio = Consts.ratio;
    private final double scale = 4;
    private final Dimension2D ScreenSize = new Dimension2D(Consts.windowWidth, Consts.windowHeight);

    public Camera(Scene scene, Vector2 pos) {
        super("Camera", scene);
        this.transform.setPosition(pos);
        canvas = new Canvas();
        canvas.setCam(this);
        this.HUD = new HUDCanvas();
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

    public HUDCanvas getHUD() { return this.HUD; }

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
        newPos.setY(-newPos.getY() * yFactor);
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
