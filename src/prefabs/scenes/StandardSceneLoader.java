package prefabs.scenes;

import display.Camera;
import game.Game;
import gameobjects.ComponentType;
import gameobjects.GameObject;
import input.PlayerInputManager;
import logic.PlayerType;
import logic.Vector2;
import player.Player;
import player.PlayerSide;
import player.controller.ControllerType;
import prefabs.gameobjects.Background;
import prefabs.gameobjects.Ground;
import scenes.Scene;

public abstract class StandardSceneLoader {

    public static Scene getStandardScene(Game game, PlayerType playerType1, PlayerType playerType2) {
        Scene scene = new Scene(game);

        GameObject gameHandler = new GameObject("GameHandler", scene);
        gameHandler.addInstantComponent(new PlayerInputManager(gameHandler, ComponentType.Input));
        scene.addGameObjectNow(gameHandler);

        Player a = new Player(scene, new Vector2(0, 2), playerType1, "Player1", ControllerType.HumanController, PlayerSide.LEFT, gameHandler);
        Player b = new Player(scene, new Vector2(2, 2), playerType2, "Player2", ControllerType.HumanController, PlayerSide.RIGHT, gameHandler);

        scene.addGameObjectNow(a);
        scene.addGameObjectNow(b);

        //Ground
        Ground ground = new Ground(scene);
        scene.addGameObjectNow(ground);

        // Background
        Background background = new Background(scene);
        scene.addGameObjectNow(background);

        //Camera
        Camera cam = new Camera(scene, new Vector2(.15, 2.1));
        scene.addGameObjectNow(cam);

        return scene;
    }

}
