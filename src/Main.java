import game.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        game.initDisplay();
        game.initScene();
        game.initSceneGraphics();
        game.loadMusic();

        game.start();
    }
}

