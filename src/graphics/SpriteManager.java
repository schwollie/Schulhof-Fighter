package graphics;

import display.Camera;

import java.awt.*;
import java.util.HashSet;

public class SpriteManager {

    private static final int maxLayers = 100;

    private HashSet<Sprite>[] spriteLayers = new HashSet[maxLayers];

    public SpriteManager() {
        for (int l = 0; l < maxLayers; l++) {
            spriteLayers[l] = new HashSet<>();
        }
    }

    public void addSprite(Sprite sprite) {
        int l = Math.min(Math.max(0, sprite.layer), maxLayers);
        spriteLayers[l].add(sprite);
    }

    public synchronized void RenderSprites(Graphics2D g, Camera cam) {
        // Draw and Clear Sprite Buffer
        for (HashSet<Sprite> sprites: spriteLayers) {
            for (Sprite p: sprites) {
                p.Render(g, cam);
            }
            sprites.clear();
        }
    }

}
