import game.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();


        game.initDisplay();

        long a = System.currentTimeMillis();
        game.loadSprites();
        long b = System.currentTimeMillis();
        System.out.println(b-a);

        game.initScene();
        game.initSceneGraphics();
        //game.loadMusic();

        game.start();
    }
}

