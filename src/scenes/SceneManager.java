package scenes;

import com.blogspot.debukkitsblog.util.FileStorage;
import logic.PlayerType;
import prefabs.scenes.StandardSceneLoader;

import java.io.File;
import java.io.IOException;

public class SceneManager {

    public final String KEY_VALUE = "scene";

    public Scene loadScene(String name) {
        String path = "scenes/" + name + ".dat";
        File file = new File(path);
        FileStorage fs = null;
        if (!file.exists()) {
            return StandardSceneLoader.getStandardScene(PlayerType.Hausperger, PlayerType.Hausperger);
        }
        try {
            fs = new FileStorage(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = (Scene) fs.get(KEY_VALUE);
        scene.loadImages();
        System.out.println("Loaded scene::" + name);
        return scene;
    }

    public void saveScene(Scene scene, String name) {
        scene.unloadImages();
        File file = new File("scenes/" + name + ".dat");
        FileStorage fs = null;
        try {
            file.createNewFile();
            fs = new FileStorage(file);
            fs.store(KEY_VALUE, scene);
            fs.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved scene::" + name);
        scene.loadImages();
    }
}
