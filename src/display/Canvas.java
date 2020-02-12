package display;

import graphics.Sprite;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JPanel {

    private ArrayList<Sprite> sprites = new ArrayList<>();

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for ( Sprite sprite: sprites) {
            sprite.draw(g);
        }
    }

}
