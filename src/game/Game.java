package game;

import display.Camera;
import display.Window;
import input.InputManager;
import input.PlayerKeyListener;
import logic.*;
import mainmenu.graphics.MenuCanvas;
import physics.PhysicsObject;
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
    public GameWorld gameWorld = new GameWorld();

    //MAIN MENU
    private MenuCanvas mainmenu;

    public void initGraphics() {
        // initialize the window & canvas as well as the KeyEventListener
        window = new Window(Consts.windowWidth, Consts.windowHeight);

        inputManager = new InputManager();

        cam = new Camera();

        mainmenu = new MenuCanvas();

        window.addMouseMotionListener(inputManager);
        window.addKeyListener(inputManager);
        window.add(cam.getCanvas());
        //window.add(mainmenu);
        window.setVisible(true);

        //mainmenu.createStandardBubbles(Consts.bubblesAmount);
    }

    public void initGame() {
        gameWorld.player1 = new HumanPlayer(gameWorld, new Vector2(0, 0), PlayerTypes.Hausperger, "Player1");
        gameWorld.player2 = new HumanPlayer(gameWorld, new Vector2(2, 0), PlayerTypes.Hausperger, "Player2");

        gameWorld.physicsObjects.add(gameWorld.player1.getPlayerPhysics());
        gameWorld.physicsObjects.add(gameWorld.player2.getPlayerPhysics());

        // Todo ground as gameObject
        Transform groundTrans = new Transform(0,  5);
        GameObject ground = new GameObject("Ground", gameWorld);
        ground.setTransform(groundTrans);
        PhysicsObject groundCollider = new PhysicsObject(ground);
        ground.setPhysicsObject(groundCollider);
        groundCollider.setStatic(true);


        groundCollider.setCollider(new RectCollider(ground, new Vector2(0, -2), new Dimension2D(10, 1)));
        gameWorld.physicsObjects.add(groundCollider);


        inputManager.setListenerMapping1((PlayerKeyListener) gameWorld.player1);
        inputManager.setListenerMapping2((PlayerKeyListener) gameWorld.player2);
    }

    public void initVisuals() {
        cam.setVisibleSprites(gameWorld.getSprites());
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
            gameWorld.tick(fpsTracker.getDeltaTime());

            //System.out.println(fpsTracker.getCurrentFPS());

            mainmenu.tick(fpsTracker.getDeltaTime(), inputManager.getMousePosition());

            EventQueue.invokeLater(cam.getCanvas()::repaint);
        }
    }
}
