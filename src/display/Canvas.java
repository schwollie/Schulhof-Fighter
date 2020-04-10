package display;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    private Camera cam;

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
    }

}
