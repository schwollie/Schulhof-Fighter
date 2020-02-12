package game;

import display.Canvas;
import display.Window;
import input.InputManager;
import input.KeyEventListener;
import input.PlayerKeyListener;
import logic.PlayerTypes;
import logic.Transform;
import logic.Vector2;
import physics.PhysicsObject;
import player.HumanPlayer;
import player.Player;

import java.awt.*;

public class Game {

    // Visuals
    public Window window;
    public Canvas mainCanvas;

    // Input
    public InputManager inputManager;

    // World
    public GameWorld gameWorld = new GameWorld();

    public void initGraphics() {
        // initialize the window & canvas as well as the KeyEventListener
        window = new Window(Consts.windowWidth, Consts.windowHeight);
        mainCanvas = new Canvas();

        inputManager = new InputManager();

        window.addKeyListener(inputManager);
        window.add(mainCanvas);
        window.setVisible(true);
    }

    public void initGame() {
        gameWorld.player1 = new HumanPlayer(Vector2.zero, PlayerTypes.Default);
        gameWorld.player2 = new HumanPlayer(new Vector2(400, 0), PlayerTypes.Default);
        //gameWorld.player1.getPlayerPhysics().setMass(10);
        //gameWorld.player2.getPlayerPhysics().setStatic(true);

        gameWorld.physicsObjects.add(gameWorld.player1.getPlayerPhysics());
        gameWorld.physicsObjects.add(gameWorld.player2.getPlayerPhysics());

        // Todo ground as gameObject
        Transform groundTrans = new Transform(0, Consts.windowHeight - 400);
        GameObject ground = new GameObject();
        PhysicsObject groundCollider = new PhysicsObject(ground);
        groundCollider.setStatic(true);

        //groundCollider.setCollider(new LineCollider(groundTrans, groundCollider, 0));
        //aagameWorld.physicsObjects.add(groundCollider);


        inputManager.setListenerMapping1((PlayerKeyListener) gameWorld.player1);
        //inputManager.setListenerMapping2((PlayerKeyListener) gameWorld.player2);
    }


    public void start() {
        //Thread paintThread = new Thread(this::startPaintLoop);
        //paintThread.start();
        startGameLoop();
    }

    public void startGameLoop() {

        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {

            }

            inputManager.sendKeyStates();
            gameWorld.tick(0.01, mainCanvas);
            EventQueue.invokeLater(mainCanvas::repaint);
        }

    }
}
