package game;

import display.Camera;
import display.Window;
import input.InputManager;
import input.PlayerKeyListener;
import logic.*;
import mainmenu.graphics.MenuCanvas;
import physics.PhysicsComponent;
import physics.RectCollider;
import player.HumanPlayer;
import time.FpsTracker;

import java.awt.*;

public class Game {

    // Visuals
    public Window window;
    public Camera cam;

    // Input
    public InputManager inputManager;

    // World
    public Scene scene = new Scene();

    //MAIN MENU
    private MenuCanvas mainmenu;

    public void initGraphics() {
        // initialize the window & canvas as well as the KeyEventListener
        window = new Window(Consts.windowWidth, Consts.windowHeight);

        inputManager = new InputManager();

        cam = new Camera(scene);

        mainmenu = new MenuCanvas();

        window.addMouseMotionListener(inputManager);
        window.addKeyListener(inputManager);
        window.add(cam.getCanvas());
        //window.add(mainmenu);
        window.setVisible(true);

        //mainmenu.createStandardBubbles(Consts.bubblesAmount);
    }

    public void initGame() {
        HumanPlayer a = new HumanPlayer(scene, new Vector2(0, 0), PlayerTypes.Hausperger, "Player1");
        HumanPlayer b = new HumanPlayer(scene, new Vector2(2, 0), PlayerTypes.Hausperger, "Player2");

        inputManager.setListenerMapping1((PlayerKeyListener) a);
        inputManager.setListenerMapping2((PlayerKeyListener) b);

        scene.addGameObject(a);
        scene.addGameObject(b);

        // Todo ground as gameObject
        Transform groundTrans = new Transform(0,  5);
        GameObject ground = new GameObject("Ground", scene);
        ground.setTransform(groundTrans);
        PhysicsComponent groundCollider = new PhysicsComponent(ground);
        ground.setPhysicsComponent(groundCollider);
        groundCollider.setStatic(true);

        groundCollider.setCollider(new RectCollider(ground, new Vector2(0, -2), new Dimension2D(10, 1)));

        scene.gameObjects.add(ground);
    }

    public void start() {
        //Thread paintThread = new Thread(this::startPaintLoop);
        //paintThread.start();
        startGameLoop();
    }

    public void startGameLoop() {

        FpsTracker fpsTracker = new FpsTracker(120);

        while (true) {
            fpsTracker.stepForward();
            fpsTracker.waitForTargetFPS();

            inputManager.sendKeyStates();

            scene.tick(fpsTracker.getDeltaTime());

            //System.out.println(fpsTracker.getCurrentFPS());

            //mainmenu.tick(fpsTracker.getDeltaTime(), inputManager.getMousePosition());

            EventQueue.invokeLater(cam.getCanvas()::repaint);
        }
    }
}
