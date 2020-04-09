package player;

import display.Camera;
import game.GameComponent;
import game.ComponentType;
import game.GameObject;
import graphics.AnimationManager;
import logic.PlayerTypes;

import java.awt.*;


public class VisualPlayer extends GameComponent {

    private AnimationManager animManager;

    public VisualPlayer(PlayerTypes type, GameObject ref) {
        super(ref, ComponentType.VisualElement);
        animManager = new AnimationManager(type, ref);
    }

    @Override
    public void tick(double dt) {
        animManager.runAnimation(dt);
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        animManager.Render(g, cam);
    }

    public void setState(PlayerState state) {
        animManager.setState(state);
    }
}
