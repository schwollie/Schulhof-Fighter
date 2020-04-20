package prefabs.scenes;

import display.Camera;
import game.Scene;
import logic.PlayerTypes;
import logic.Vector2;
import player.HumanPlayer;
import prefabs.gameobjects.Background;
import prefabs.gameobjects.Ground;

public abstract class StandardSceneLoader {

    public static Scene getStandardScene() {
        Scene scene = new Scene();
        HumanPlayer a = new HumanPlayer(scene, new Vector2(0, 2), PlayerTypes.Hausperger, "Player1");
        HumanPlayer b = new HumanPlayer(scene, new Vector2(2, 2), PlayerTypes.Hausperger, "Player2");

        scene.getInputManager().setListenerMapping1(a);
        scene.getInputManager().setListenerMapping2(b);

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
