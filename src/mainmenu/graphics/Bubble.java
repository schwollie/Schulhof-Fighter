package mainmenu.graphics;

import game.Consts;
import logic.Vector2;

import java.util.Random;

public class Bubble {
    private static Random random = new Random();

    private Vector2 position;
    private Vector2 velocity;
    private int radius, speed;


    public Bubble() {
    }

    public void addPosition(Vector2 addPosition) {
        position = position.add(addPosition);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPosition(double positionX, double positionY) {
        this.position = new Vector2(positionX, positionY);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double velocityX, double velocityY) {
        this.velocity = new Vector2(velocityX, velocityY);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void randomize() {
        //randomize radius
        radius = random.nextInt(10) + 5;

        //randomize velocity
        speed = random.nextInt(200) + 200;
        double velocityX = random.nextDouble() * speed, velocityY = random.nextDouble() * speed;

        //randomize location
        if (random.nextBoolean()) {
            boolean startAtTop = random.nextBoolean();
            int posY = startAtTop ? 0 : Consts.windowHeight;
            velocityY = startAtTop ? velocityY : -velocityY;

            setPosition(random.nextInt(Consts.windowHeight), posY);
        } else {
            boolean startAtLeft = random.nextBoolean();
            int posX = startAtLeft ? 0 : Consts.windowWidth;
            velocityX = startAtLeft ? velocityX : -velocityX;

            setPosition(posX, random.nextInt(Consts.windowWidth));
        }

        setVelocity(velocityX, velocityY);
    }

    public void checkForOutOfSight() {
        if (position.getX() > Consts.windowWidth || position.getX() < 0 || position.getY() > Consts.windowHeight || position.getY() < 0) {
            randomize();
        }
    }

    public boolean isBubbleInMouseDistance() {
        double distance = Math.sqrt(Math.pow(position.getX() - Consts.mousePosition.getX(), 2) + Math.pow(position.getY() - Consts.mousePosition.getY(), 2));
        return distance <= Consts.bubblesConnectRadius;
    }
}
