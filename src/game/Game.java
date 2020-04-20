package game;

import display.Camera;
import display.Window;
import logic.PlayerTypes;
import logic.Vector2;
import mainmenu.graphics.MenuCanvas;
import player.HumanPlayer;
import prefabs.gameobjects.Background;
import prefabs.gameobjects.Ground;
import prefabs.scenes.StandardSceneLoader;
import time.TimeManager;

import java.awt.*;

public class Game {

    // Visuals
    public Window window;

    // World
    public Scene scene;
    public SceneManager sceneManager;

    //MAIN MENU
    private MenuCanvas mainmenu;

    public void initScene() {
        sceneManager = new SceneManager();
        scene = StandardSceneLoader.getStandardScene();
    }

    public void initDisplay() {
        window = new Window(Consts.windowWidth, Consts.windowHeight);
    }

    // has to be called on every scene change
    public void initSceneGraphics() {
        window.addMouseMotionListener(scene.getInputManager());
        window.addKeyListener(scene.getInputManager());
        window.add(((Camera)scene.getCam()).getCanvas());
        window.setVisible(true);

        //mainmenu = new MenuCanvas();
        //window.add(mainmenu);
        //mainmenu.createStandardBubbles(Consts.bubblesAmount);

    }

    public void start() {
        startGameLoop();
    }

    public void startGameLoop() {
        while (true) {

            //wait for target FPS
            scene.getTimeManager().stepForward();
            scene.getTimeManager().waitForTargetFPS();

            // update physics and scripts
            scene.tick();

            //redraw scene
            EventQueue.invokeLater(((Camera)scene.getCam()).getCanvas()::repaint);

            //System.out.println(timeManager.getCurrentFPS());
            //mainmenu.tick(fpsTracker.getDeltaTime(), inputManager.getMousePosition());
        }
    }
}
