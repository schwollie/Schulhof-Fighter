package display;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{

    public Window(int width, int height) {
        super();
        this.setResizable(false);
        this.setTitle("Schulhof-Fighter");
        this.setSize(new Dimension(width, height));
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
