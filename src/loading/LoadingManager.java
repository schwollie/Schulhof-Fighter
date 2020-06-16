package loading;

public class LoadingManager {

    public static void loadAll(OnLoadFinishedListener l) {

        double progress = 0;
        double total = 3;

        SpriteLoader.loadAll();
        AudioLoader.loadAll();

        l.onLoadFinish();
    }

}
