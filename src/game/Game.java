package game;

import audio.AudioManager;
import display.Camera;
import display.Window;
import input.InputManager;
import loading.SpriteLoader;
import logic.PlayerType;
import prefabs.scenes.StandardSceneLoader;
import scenes.Scene;
import scenes.SceneManager;

import java.awt.*;

public class Game {

    // Visuals
    public Window window;

    // World
    public Scene currentScene;
    public SceneManager sceneManager;

    //music
    private AudioManager audioManager;

    //input
    private InputManager inputManager = new InputManager();

    //MAIN MENU
    //private MenuCanvas mainmenu;

    public void loadSprites() {
        SpriteLoader.loadAll();
    }

    public void initScene() {
        sceneManager = new SceneManager();
        currentScene = StandardSceneLoader.getStandardScene(this, PlayerType.Hausperger, PlayerType.Hausperger);
    }

    public void initDisplay() {
        window = new Window(Consts.windowWidth, Consts.windowHeight);
    }

    // has to be called on every scene change
    public void initSceneGraphics() {
        window.addMouseMotionListener(inputManager);
        window.addMouseListener(inputManager);
        window.addKeyListener(inputManager);


        window.add(((Camera) currentScene.getCam()).getCanvas());
        window.setVisible(true);
    }

    public void start() {
        startGameLoop();
    }

    public void startGameLoop() {
        while (true) {

            //wait for target FPS
            currentScene.getTimeManager().stepForward();
            currentScene.getTimeManager().waitForTargetFPS();

            // update physics and scripts
            currentScene.tick();

            //redraw scene
            EventQueue.invokeLater(((Camera) currentScene.getCam()).getCanvas()::repaint);

            //System.out.println(scene.getTimeManager().getCurrentFPS());
            //mainmenu.tick(fpsTracker.getDeltaTime(), inputManager.getMousePosition());
        }
    }

    public void loadMusic() {
        //audioManager = new AudioManager("test.wav");
        //audioManager.play();
    }

    public InputManager getInputManager() { return this.inputManager; }
}
