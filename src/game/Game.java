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
import time.TimeManager;

import java.awt.*;

public class Game {

    //region Singletion Pattern
    private static Game game;

    public static Game game()
    {
        // To ensure only one instance is created
        if (game == null)
        {
            game = new Game();
        }
        return game;
    }

    private Game() {}
    // endregion

    // Visuals
    public Window window;

    // World
    public Scene currentScene;
    public SceneManager sceneManager;

    //music
    public AudioManager audioManager;

    //input
    private final InputManager inputManager = new InputManager(this);

    // Time Manager
    public final TimeManager timeManager = new TimeManager(Consts.targetFPS);

    //MAIN MENU
    //private MenuCanvas mainmenu; kann eig gelöscht werden oder?

    public void loadSprites() {
        SpriteLoader.loadAll();
    }

    public void initScene() {
        sceneManager = new SceneManager(this);
        currentScene = StandardSceneLoader.getStandardScene(this, PlayerType.Hausperger, PlayerType.Hausperger);
    }

    public void initDisplay() {
        window = new Window(this, Consts.windowWidth, Consts.windowHeight);
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
            timeManager.stepForward();
            timeManager.waitForTargetFPS();

            // update physics and scripts
            currentScene.tick();

            //redraw scene
            EventQueue.invokeLater(((Camera) currentScene.getCam()).getCanvas()::repaint);
            //System.out.println(getTimeManager().getCurrentFPS());

            //mainmenu.tick(fpsTracker.getDeltaTime(), inputManager.getMousePosition());
        }
    }

    public void loadMusic() {
        //audioManager = new AudioManager("test.wav");
        //audioManager.play();
    }

    public InputManager getInputManager() { return this.inputManager; }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }

}
