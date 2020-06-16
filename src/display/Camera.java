package display;

import components.GuiCanvas;
import components.ScreenTransform;
import game.Consts;
import game.Game;
import gameobjects.GameObject;
import loading.SpriteLoader;
import logic.Dimension2D;
import logic.Shaker;
import logic.Transform;
import logic.Vector2;
import scenes.Scene;
import settings.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Camera extends GameObject implements Serializable {

    private final Canvas canvas;

    private final double ratio = Consts.ratio;
    private Dimension2D resolution;
    private double scale;

    private BufferedImage vignetteEffect;
    private int vignetteStrength = 0;

    public Camera(Scene scene, Vector2 pos, double scale) {
        super("Camera", scene);
        this.scale = scale;
        this.transform.setPosition(pos);
        resolution = new Dimension2D(Game.window.getWidth(), Game.window.getHeight());
        canvas = new Canvas();
        canvas.setCam(this);
    }

    public static Transform gui2Screen(ScreenTransform trans, Dimension2D resolution) {
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

    public void RenderScene(Graphics2D g) {
        RenderingHints s = Settings.getQuality();
        g.setRenderingHints(s);

        scene.Render(g, this);
        postProcessing(g);
    }

    private void postProcessing(Graphics2D g) {
        if (vignetteEffect != null) {
            g.drawImage(vignetteEffect, 0, 0, Game.window.getWidth(), Game.window.getHeight(), null);
        }
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

    public void setResolution(Dimension2D resolution) {
        this.resolution = resolution;
    }

    public Shape getClip() {
        Rectangle clipShape = new Rectangle(0, 0, (int) this.resolution.getWidth(), (int) this.resolution.getHeight());
        return clipShape;
    }

    public void shake(Vector2 maxDeviation, Vector2 speed, double maxTime) {
        Shaker s = new Shaker(this, maxDeviation, speed, maxTime);
        this.addComponent(s);
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

    public void setVignetteStrength(int vignetteStrength) {
        this.vignetteStrength = vignetteStrength;

        if (vignetteStrength == 1) {
            vignetteEffect = SpriteLoader.getFromFilePath("images/Background/vignette.png");
        } else if (vignetteStrength >= 2) {
            vignetteEffect = SpriteLoader.getFromFilePath("images/Background/vignette-strong.png");
        }

    }
}
