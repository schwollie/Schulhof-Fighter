package prefabs.scenes;

import display.Camera;
import game.Consts;
import game.Game;
import logic.Dimension2D;
import logic.PlayerType;
import logic.Vector2;
import prefabs.HUD.LoadingHUD;
import scenes.Scene;

public class LoadingSceneLoader {

    public static Scene getLoadingScene() {

        Scene scene = new Scene() {
            @Override
            public void onLoadFinish() {
                Game.changeScene(StandardSceneLoader.getStandardScene(PlayerType.Hausperger, PlayerType.Hausperger));
            }
        };

        scene.setGuiCanvas(new LoadingHUD(new Dimension2D(Consts.windowWidth, Consts.windowHeight)));

        //Camera
        Camera cam = new Camera(scene, new Vector2(.15, 2.1), 3.8);
        scene.addGameObjectNow(cam);

        return scene;
    }

}
