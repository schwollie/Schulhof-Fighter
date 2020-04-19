package player;

import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import graphics.AnimationManager;
import graphics.RenderManager;
import logic.PlayerTypes;


public class VisualPlayer extends GameComponent {

    private final AnimationManager animManager;

    public VisualPlayer(PlayerTypes type, GameObject ref) {
        super(ref, ComponentType.VisualElement);
        animManager = new AnimationManager(type, ref);
    }

    @Override
    public void tick() {
        animManager.tick();
    }

    @Override
    public void UpdateDrawables(RenderManager renderManager) {
        animManager.UpdateDrawables(renderManager);
    }

    public void setState(PlayerState state) {
        animManager.setState(state);
    }

    public void unloadImage() {
        animManager.unloadImage();
    }

    public void loadImage() {
        animManager.loadImage();
    }
}
