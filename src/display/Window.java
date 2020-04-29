package display;

import game.Consts;
import game.Game;
import logic.Dimension2D;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private boolean fullscreen = false;
    private Game game;

    public Window(Game game, int width, int height) {
        super();
        this.game = game;
        this.setSize(new Dimension(width, height));
        this.setTitle("Schulhof Fighter");
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setVisible(false);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout());
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
        ((Camera) game.currentScene.getCam()).setResolution(size);
        setVisible(true);
        fullscreen = !fullscreen;
    }

}
