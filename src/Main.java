import game.Game;

public class Main {

    public static void main(String[] args) {
        Game.initDisplay();

        Game.loadSprites();

        Game.initScene();
        Game.initSceneGraphics();
        //Game.loadMusic();

        Game.start();
    }
}

