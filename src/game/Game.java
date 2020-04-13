package game;

import display.Camera;
import display.Window;
import graphics.RectSprite;
import logic.Dimension2D;
import logic.PlayerTypes;
import logic.Transform;
import logic.Vector2;
import mainmenu.graphics.MenuCanvas;
import physics.PhysicsGameComponent;
import physics.RectCollider;
import player.HumanPlayer;
import time.TimeManager;

import java.awt.*;

public class Game {

    // Visuals
    public Window window;
    public Camera cam;


    // World
    public Scene scene;
    public SceneManager sceneManager;

    //MAIN MENU
    private MenuCanvas mainmenu;

    public void init() {
        scene = new Scene();
        sceneManager = new SceneManager();
    }

    public void initGraphics() {
        // initialize the window & canvas as well as the KeyEventListener
        window = new Window(Consts.windowWidth, Consts.windowHeight);

        cam = new Camera(scene, new Vector2(0, 4));
        scene.addGameObject(cam);

        mainmenu = new MenuCanvas();

        window.addMouseMotionListener(scene.getInputManager());
        window.addKeyListener(scene.getInputManager());
        window.add(cam.getCanvas());
        //window.add(mainmenu);
        window.setVisible(true);

        //mainmenu.createStandardBubbles(Consts.bubblesAmount);

    }

    public void initGame() {
        loadSceneManually();
        //scene = sceneManager.loadScene("default");
    }

    public void loadSceneManually() {
        HumanPlayer a = new HumanPlayer(scene, new Vector2(0, 2), PlayerTypes.Hausperger, "Player1");
        HumanPlayer b = new HumanPlayer(scene, new Vector2(2, 2), PlayerTypes.Hausperger, "Player2");

        scene.getInputManager().setListenerMapping1(a);
        scene.getInputManager().setListenerMapping2(b);

        scene.addGameObject(a);
        scene.addGameObject(b);

        // Todo ground as gameObject
        Transform groundTrans = new Transform(0, 0);
        GameObject ground = new GameObject("Ground", scene);
        ground.setTransform(groundTrans);
        PhysicsGameComponent groundCollider = new PhysicsGameComponent(ground);
        ground.setPhysicsComponent(groundCollider);
        groundCollider.setStatic(true);
        groundCollider.setCollider(new RectCollider(ground, new Vector2(0, 0), new Dimension2D(10, 1)));
        ground.addComponent(new RectSprite(ground, new Vector2(0, 0), new Dimension2D(10, 1)));

        scene.gameObjects.add(ground);

        sceneManager.saveScene(scene, "default");
    }

    public void start() {
        //Thread paintThread = new Thread(this::startPaintLoop);
        //paintThread.start();
        startGameLoop();
    }

    public void startGameLoop() {

        TimeManager timeManager = new TimeManager(120);
        scene.timeManager = timeManager;

        while (true) {
            timeManager.stepForward();
            timeManager.waitForTargetFPS();

            scene.tick();

            //System.out.println(timeManager.getCurrentFPS());

            //mainmenu.tick(fpsTracker.getDeltaTime(), inputManager.getMousePosition());

            EventQueue.invokeLater(cam.getCanvas()::repaint);
        }
    }
}
