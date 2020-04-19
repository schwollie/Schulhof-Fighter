package graphics;

import display.Camera;

import java.awt.*;

public interface Drawable {

    void Render(Graphics2D g, Camera cam);

    int getRenderLayer();

    void setRenderLayer(int layer);

}
