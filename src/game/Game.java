package game;

import display.Camera;
import display.Window;
import input.InputManager;
import loading.LoadingManager;
import prefabs.scenes.LoadingSceneLoader;
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
    //public static final AudioManager audioManager = new AudioManager();

    //input
    public static final InputManager inputManager = new InputManager();

    // Time Manager
    public static final TimeManager timeManager = new TimeManager(Consts.targetFPS);

    public static void loadAll() {
        LoadingManager.loadAll(currentScene);
    }

    public static void initScene() {
        currentScene = LoadingSceneLoader.getLoadingScene();//StandardSceneLoader.getStandardScene(PlayerType.Sippl, PlayerType.Hausperger);
    }

    public static void initDisplay() {
        window = new Window(Consts.windowWidth, Consts.windowHeight);

        window.addMouseMotionListener(inputManager);
        window.addMouseListener(inputManager);
        window.addKeyListener(inputManager);
    }

    // has to be called on every scene change
    public static void initSceneGraphics() {
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
            //System.out.println(timeManager.getCurrentFPS());

            //mainmenu.tick(fpsTracker.getDeltaTime(), inputManager.getMousePosition());
        }
    }

    public static void loadMusic() {
        //audioManager = new AudioManager("test.wav");
        //audioManager.play();
    }

    public static void changeScene(Scene newScene) {
        window.remove(((Camera) currentScene.getCam()).getCanvas());
        currentScene = newScene;
        initSceneGraphics();
    }
}
