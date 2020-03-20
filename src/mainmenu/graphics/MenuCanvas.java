package mainmenu.graphics;

import game.Consts;
import logic.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MenuCanvas extends JPanel {

    private ArrayList<Bubble> bubbles;
    private ArrayList<Bubble> bubblesInRadius = new ArrayList<>();

    public MenuCanvas() {
        bubbles = new ArrayList<>();
    }

    public void createStandardBubbles(int amount) {
        for (int i = 0; i < amount; i++) {
            Bubble createdBubble = new Bubble();
            createdBubble.randomize();
            bubbles.add(createdBubble);
        }
    }

    public void tick(double deltaTime) {
        bubblesInRadius.clear();
        for (Bubble bubble : bubbles) {
            Vector2 currentVelocity = bubble.getVelocity().scalarMult(deltaTime);
            bubble.addPosition(currentVelocity);

            bubble.checkForOutOfSight();

            if (bubble.isBubbleInMouseDistance()) {
                bubblesInRadius.add(bubble);
                Vector2 velocityDirectionToMouse = new Vector2(Consts.mousePosition.getX() - bubble.getPosition().getX(), Consts.mousePosition.getY() - bubble.getPosition().getY());
                velocityDirectionToMouse.normalize();
                velocityDirectionToMouse = velocityDirectionToMouse.scalarMult(bubble.getSpeed() * 0.7);
                Vector2 velocityToMouse = velocityDirectionToMouse.scalarMult(deltaTime);
                bubble.addPosition(velocityToMouse);
            }

        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        //test colors : new Color(141, 228, 205) - rgb(52, 152, 219)
        GradientPaint gradient = new GradientPaint(0, 0, new Color(41, 128, 185), Consts.windowWidth, Consts.windowHeight, new Color(121, 208, 185));
        graphics2D.setPaint(gradient);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);

        for (Bubble bubble : bubblesInRadius) {
            for (Bubble targetBubble : bubblesInRadius) {
                g.drawLine((int) bubble.getPosition().getX(), (int) bubble.getPosition().getY(), (int) targetBubble.getPosition().getX(), (int) targetBubble.getPosition().getY());
            }
        }

        for (Bubble bubble : bubbles) {
            int radius = bubble.isBubbleInMouseDistance() ? bubble.getRadius() + 2 : bubble.getRadius();
            graphics2D.translate(-radius / 2, -radius / 2);
            g.fillOval((int) bubble.getPosition().getX(), (int) bubble.getPosition().getY(), radius, radius);
            graphics2D.translate(radius / 2, radius / 2);
        }


        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 150));
        g.drawString("Schulhof-Fighter", 150, 500);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("ok, die Schrift is nur hingeklatscht, aber es geht um die Partikel xD", 160, 570);
    }

}
