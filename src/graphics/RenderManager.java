package graphics;

import display.Camera;

import java.awt.*;
import java.util.HashSet;

public class RenderManager {

    private static final int maxLayers = 100;

    private HashSet<Drawable>[] spriteLayers = new HashSet[maxLayers];

    public RenderManager() {
        for (int l = 0; l < maxLayers; l++) {
            spriteLayers[l] = new HashSet<>();
        }
    }

    public synchronized void addDrawable(Drawable drawable) {
        int l = Math.min(Math.max(0, drawable.getRenderLayer()), maxLayers);
        spriteLayers[l].add(drawable);
    }

    public void RenderSprites(Graphics2D g, Camera cam) {
        // Draw and Clear Sprite Buffer
        for (HashSet<Drawable> drawables: spriteLayers) {
            for (Drawable p: drawables) {
                p.Render(g, cam);
            }
        }
    }

    public void clearBuffer() {
        for (HashSet<Drawable> drawables: spriteLayers) {
            drawables.clear();
        }
    }

}
