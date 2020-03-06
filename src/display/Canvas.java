package display;

import graphics.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Canvas extends JPanel {

    private Camera cam;
    private ArrayList<Sprite> sprites = new ArrayList<>();

    public void setSprites(ArrayList<Sprite> sprites) {
        this.sprites = sprites;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setClip(cam.getClip());

        for (Sprite sprite: sprites) {
            sprite.draw(g2d, cam);
        }
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }
}
