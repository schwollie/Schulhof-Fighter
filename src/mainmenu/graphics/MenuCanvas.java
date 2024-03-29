package mainmenu.graphics;

import components.elements.Button;
import components.GuiComponent;
import components.GuiCanvas;
import game.Consts;
import logic.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class MenuCanvas {//extends GuiCanvas {

    /*private ArrayList<Bubble> bubbles;
    private ArrayList<Bubble> bubblesInRadius = new ArrayList<>();


    public MenuCanvas() {
        //bubbles = new ArrayList<>();
        //Button testBtn = new Button("TEST BUTTON", 20, 10);
        //testBtn.setTextAlign(GuiComponent.TextAlign.CENTER);
        //testBtn.setLeft(10);
        //addGuiComponent(testBtn);
    }

    public void createStandardBubbles(int amount) {
        for (int i = 0; i < amount; i++) {
            Bubble createdBubble = new Bubble();
            createdBubble.randomize();
            bubbles.add(createdBubble);
        }
    }

    public void tick(double deltaTime, Vector2 mousePosition) {
        synchronized (bubblesInRadius) {
            synchronized (bubbles) {
                bubblesInRadius.clear();

                for (Bubble bubble : bubbles) {
                    Vector2 currentVelocity = bubble.getVelocity().scalarMult(deltaTime);
                    bubble.addPosition(currentVelocity);

                    bubble.checkForOutOfSight();

                    if (bubble.isInMouseRadius(mousePosition)) {
                        bubblesInRadius.add(bubble);
                        Vector2 velocityDirectionToMouse = new Vector2(mousePosition.getX() - bubble.getPosition().getX(), mousePosition.getY() - bubble.getPosition().getY());
                        velocityDirectionToMouse.normalize();
                        velocityDirectionToMouse = velocityDirectionToMouse.scalarMult(bubble.getSpeed() * 0.7);
                        Vector2 velocityToMouse = velocityDirectionToMouse.scalarMult(deltaTime);
                        bubble.addPosition(velocityToMouse);
                    }

                }
            }
        }
        EventQueue.invokeLater(this::repaint);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        //test colors : new Color(141, 228, 205) - rgb(52, 152, 219)
        GradientPaint backgroundGradient = new GradientPaint(0, 0, new Color(41, 128, 185), Consts.windowWidth, Consts.windowHeight, new Color(121, 208, 185));
        graphics2D.setPaint(backgroundGradient);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);

        synchronized (bubblesInRadius) {
            for (int i = 0; i < bubblesInRadius.size(); i++) {
                Bubble bubble = bubblesInRadius.get(i);
                for (int j = i; j < bubblesInRadius.size(); j++) {
                    Bubble targetBubble = bubblesInRadius.get(j);
                    g.drawLine((int) bubble.getPosition().getX(), (int) bubble.getPosition().getY(), (int) targetBubble.getPosition().getX(), (int) targetBubble.getPosition().getY());
                }
            }
        }
        synchronized (bubbles) {
            for (Bubble bubble : bubbles) {
                int radius = bubble.getRadius();
                int posX = (int) bubble.getPosition().getX();
                int posY = (int) bubble.getPosition().getY();
                graphics2D.translate(-radius / 2, -radius / 2);
                //für schattierung
                GradientPaint shadowGradient = new GradientPaint(posX, posY + radius / 3, new Color(220, 220, 220), posX + radius, posY + radius, new Color(255, 255, 255));
                graphics2D.setPaint(shadowGradient);
                g.fillOval(posX, posY, radius, radius);

                graphics2D.translate(radius / 2, radius / 2);
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 150));
        g.drawString("Schulhof-Fighter", 150, 500);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("ok, die Schrift is nur hingeklatscht, aber es geht um die Partikel xD", 160, 570);
    }*/

}
