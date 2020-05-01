package display;

import game.Game;
import loading.SpriteLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class Canvas extends JPanel {

    private Camera cam;
    private BufferedImage vignetteEffect;

    public Canvas (){
        vignetteEffect = SpriteLoader.getFromFilePath("images/Background/vignette.png");
    }

    public Camera getCam() {
        return cam;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        cam.RenderScene(graphics2D);
        graphics2D.drawImage(vignetteEffect, 0,0, Game.window.getWidth(), Game.window.getHeight(), null);
    }
}
