package display;

import game.Consts;
import game.Game;
import logic.Dimension2D;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private boolean fullscreen = false;

    public Window(int width, int height) {
        super();
        this.setSize(new Dimension(width, height));
        this.setTitle("Schulhof Fighter");
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setVisible(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout());

        if (Consts.Fullscreen) {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setSize(Consts.screenSize.width, Consts.screenSize.height);
            this.setUndecorated(true);
            fullscreen = true;
        }
    }

    public void changeScreenSize() {
        dispose();
        Dimension2D size;

        if (fullscreen) {
            this.setExtendedState(JFrame.NORMAL);
            this.setSize(new Dimension(Consts.windowWidth, Consts.windowHeight));
            this.setUndecorated(false);
            size = new Dimension2D(Consts.windowWidth, Consts.windowHeight);
        } else {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setUndecorated(true);
            size = new Dimension2D(Consts.screenSize.getWidth(), Consts.screenSize.getHeight());
        }

        ((Camera) Game.currentScene.getCam()).setResolution(size);
        setVisible(true);
        fullscreen = !fullscreen;
    }


}
