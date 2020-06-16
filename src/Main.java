import game.Game;

public class Main {

    public static void main(String[] args) {
        Game.initDisplay();

        Game.initScene();
        Game.initSceneGraphics();

        Game.loadAll();

        Game.start();
    }
}

