package player;

import display.Camera;
import game.ComponentType;
import game.GameComponent;
import game.GameObject;
import graphics.AnimationManager;
import graphics.SpriteManager;
import logic.PlayerTypes;

import java.awt.*;


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
    public void UpdateSprites(SpriteManager spriteManager) {
        animManager.UpdateSprites(spriteManager);
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
