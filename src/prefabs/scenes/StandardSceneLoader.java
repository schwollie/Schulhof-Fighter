package prefabs.scenes;

import display.Camera;
import logic.PlayerType;
import logic.Vector2;
import player.Player;
import player.PlayerSide;
import player.controller.ControllerType;
import prefabs.gameobjects.Background;
import prefabs.gameobjects.Ground;
import scenes.Scene;

public abstract class StandardSceneLoader {

    public static Scene getStandardScene(PlayerType p1, PlayerType p2) {
        Scene scene = new Scene();

        Player a = new Player(scene, new Vector2(0, 2), p1, "Player1", ControllerType.HumanController, PlayerSide.LEFT);
        Player b = new Player(scene, new Vector2(2, 2), p2, "Player2", ControllerType.HumanController, PlayerSide.RIGHT);

        scene.addGameObjectNow(a);
        scene.addGameObjectNow(b);

        //Ground
        Ground ground = new Ground(scene);
        scene.addGameObjectNow(ground);

        // Background
        Background background = new Background(scene);
        scene.addGameObjectNow(background);

        //Camera
        Camera cam = new Camera(scene, new Vector2(0, 2.2));
        scene.addGameObjectNow(cam);

        return scene;
    }

}
