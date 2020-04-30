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

public final class Game {

    // Visuals
    public static Window window;


    // World
    public static Scene currentScene;
    public static final SceneManager sceneManager = new SceneManager();

    //music
    public static final AudioManager audioManager = new AudioManager();

    //input
    public static final InputManager inputManager = new InputManager();

    // Time Manager
    public static final TimeManager timeManager = new TimeManager(Consts.targetFPS);

    //MAIN MENU
    //private MenuCanvas mainmenu; kann eig gelöscht werden oder?

    public static void loadSprites() {
        SpriteLoader.loadAll();
    }

    public static void initScene() {
        currentScene = StandardSceneLoader.getStandardScene(PlayerType.Hausperger, PlayerType.Hausperger);
    }

    public static void initDisplay() {
        window = new Window(Consts.windowWidth, Consts.windowHeight);
    }

    // has to be called on every scene change
    public static void initSceneGraphics() {
        window.addMouseMotionListener(inputManager);
        window.addMouseListener(inputManager);
        window.addKeyListener(inputManager);


        window.add(((Camera) currentScene.getCam()).getCanvas());
        window.setVisible(true);
    }

    public static void start() {
        startGameLoop();
    }

    public static void startGameLoop() {
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

    public static void loadMusic() {
        //audioManager = new AudioManager("test.wav");
        //audioManager.play();
    }
}
