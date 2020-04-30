package prefabs.scenes;

import display.Camera;
import gameobjects.ComponentType;
import gameobjects.GameObject;
import input.PlayerInputManager;
import logic.Dimension2D;
import logic.PlayerType;
import logic.Vector2;
import player.Player;
import player.PlayerSide;
import player.controller.ControllerType;
import prefabs.gameobjects.Background;
import prefabs.gameobjects.Ground;
import prefabs.gameobjects.Wall;
import scenes.Scene;

public abstract class StandardSceneLoader {

    public static Scene getStandardScene(PlayerType p1, PlayerType p2) {
        Scene scene = new Scene();

        GameObject gameHandler = new GameObject("GameHandler", scene);
        gameHandler.addInstantComponent(new PlayerInputManager(gameHandler, ComponentType.Input));
        scene.addGameObjectNow(gameHandler);

        Player a = new Player(scene, new Vector2(1, 1), p1, "Player1", ControllerType.HumanController, PlayerSide.LEFT, gameHandler);
        Player b = new Player(scene, new Vector2(3, 1), p2, "Player2", ControllerType.HumanController, PlayerSide.RIGHT, gameHandler);

        scene.addGameObjectNow(a);
        scene.addGameObjectNow(b);

        //Ground
        Ground ground = new Ground(scene);
        scene.addGameObjectNow(ground);

        //Walls
        Wall wall1 = new Wall(scene, new Vector2(-.85, 0), new Dimension2D(1, 100));
        scene.addGameObjectNow(wall1);

        Wall wall2 = new Wall(scene, new Vector2(4, 0), new Dimension2D(1, 100));
        scene.addGameObjectNow(wall2);

        // Background
        Background background = new Background(scene);
        scene.addGameObjectNow(background);

        //Camera
        Camera cam = new Camera(scene, new Vector2(.15, 2.1), 3.8);
        scene.addGameObjectNow(cam);

        return scene;
    }

}
