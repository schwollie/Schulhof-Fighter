package player.utils;

import animation.AnimationManager;
import components.elements.*;
import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;
import graphics.RenderManager;
import logic.PlayerType;
import player.Player;
import player.PlayerState;
import prefabs.HUD.GameHUD;
import prefabs.gameobjects.Shadow;


public class VisualPlayer extends GameComponent {

    private Player player;
    private final AnimationManager animManager;
    private Shadow shadow;

    private Panel panel;
    private UiImage back;
    private Slider healthBar;
    private Slider staminaBar;
    private TextView text;
    private UiImage overlay;
    private ClockPointer clockPointer;

    public VisualPlayer(PlayerType type, GameObject ref) {
        super(ref, ComponentType.VisualElement);

        player = (Player)ref;
        animManager = new AnimationManager(type, ref);
        setUpGuiReferences();

        shadow = new Shadow("Shadow", ref.getScene(), ref);
    }

    public void setUpGuiReferences() {
        assert reference.getScene().getGuiCanvas() instanceof GameHUD : "not the right gui type!";
        GameHUD hud = (GameHUD) reference.getScene().getGuiCanvas();

        if (this.reference.getTag().equals("Player1")) {
            panel = hud.getP1Panel();
            back = hud.getP1Back();
            healthBar = hud.getP1HealthSlider();
            staminaBar = hud.getP1StaminaSlider();
            text = hud.getP1Text();
            overlay = hud.getP1Overlay();
            clockPointer = hud.getP1Pointer();
        } else if (this.reference.getTag().equals("Player2")) {
            panel = hud.getP2Panel();
            back = hud.getP2Back();
            healthBar = hud.getP2HealthSlider();
            staminaBar = hud.getP2StaminaSlider();
            text = hud.getP2Text();
            overlay = hud.getP2Overlay();
            clockPointer = hud.getP2Pointer();
        } else {
            throw new Error("Wrong Tag for Players");
        }

        text.setText(player.getType().toString());
    }

    @Override
    public void tick() {
        animManager.tick();
        updateGui();
    }

    private void updateGui() {
        healthBar.setProgress(player.getHealthManager().getHealthPercentage());
        clockPointer.setProgress(player.getHealthManager().getStaminaPercentage());
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
