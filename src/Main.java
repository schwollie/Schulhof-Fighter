import game.Game;

public class Main {

    public static void main(String[] args) {
        Game game = Game.game();

        game.initDisplay();

        game.loadSprites();

        game.initScene();
        game.initSceneGraphics();
        //game.loadMusic();

        game.start();
    }
}

