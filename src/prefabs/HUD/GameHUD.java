package prefabs.HUD;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import components.elements.Panel;
import components.elements.*;
import game.Game;
import logic.Dimension2D;
import logic.Vector2;

import java.awt.*;

public class GameHUD extends GuiCanvas {

    private static final ScreenTransform backT = new ScreenTransform(new Vector2(0, 0), new Vector2(0.4, .1));
    private static final ScreenTransform staminaT = new ScreenTransform(new Vector2(0.041, 0.0106), new Vector2(0.3236, 0));
    private static final ScreenTransform healthT = new ScreenTransform(new Vector2(0.2145, 0.031), new Vector2(.149, 0));
    private static final ScreenTransform textT = new ScreenTransform(new Vector2(.11, .04), new Vector2(.1, .1));
    private static final ScreenTransform overlayT = new ScreenTransform(new Vector2(0.038, 0.01062), new Vector2(.329, .1));
    private static final ScreenTransform pointer = new ScreenTransform(new Vector2(0.287, 0.06), new Vector2(.02, 0));

    private final Panel p1Panel = new Panel(this);
    private UiImage p1Back;
    private Slider p1HealthSlider;
    private Slider p1StaminaSlider;
    private TextView p1Text;
    private UiImage p1Overlay;
    private ClockPointer p1Pointer;

    private final Panel p2Panel = new Panel(this);
    private UiImage p2Back;
    private Slider p2HealthSlider;
    private Slider p2StaminaSlider;
    private TextView p2Text;
    private UiImage p2Overlay;
    private ClockPointer p2Pointer;

    public GameHUD(Game game, Dimension2D resolution) {
        super(game, resolution);
        createHud();
    }

    private void createHud() {
        //region hud for player1:

        p1Back = new UiImage(this, backT, "images/Gui/stats.png");
        p2Back = new UiImage(this, backT, "images/Gui/stats.png");

        p1HealthSlider = new Slider(this, staminaT);
        p1HealthSlider.loadBar("images/Gui/bar1.png");
        p2HealthSlider = new Slider(this, staminaT);
        p2HealthSlider.loadBar("images/Gui/bar1.png");
        //p2HealthSlider.setReverse(true);

        p1StaminaSlider = new Slider(this, healthT);
        p1StaminaSlider.loadBar("images/Gui/bar2.png");
        p2StaminaSlider = new Slider(this, healthT);
        p2StaminaSlider.loadBar("images/Gui/bar2.png");
        //p2StaminaSlider.setReverse(true);

        p1Text = new TextView(this, textT, "");
        p1Text.setTextColor(Color.WHITE);
        p2Text = new TextView(this, textT, "");
        p2Text.setTextColor(Color.WHITE);

        p1Pointer = new ClockPointer(this, pointer, "images/Gui/pointer.png");
        p1Pointer.setRotOffset(270);
        p1Pointer.setInvertDir(true);
        p2Pointer = new ClockPointer(this, pointer, "images/Gui/pointer.png");
        p2Pointer.setRotOffset(270);
        p2Pointer.setInvertDir(true);//muss noch verschoben werden!?

        p1Overlay = new UiImage(this, overlayT, "images/Gui/overlay1.png");
        p2Overlay = new UiImage(this, overlayT, "images/Gui/overlay1.png");

        p1Panel.addComponents(new GuiComponent[]{p1Back, p1HealthSlider, p1StaminaSlider, p1Text, p1Overlay, p1Pointer});
        p2Panel.addComponents(new GuiComponent[]{p2Back, p2HealthSlider, p2StaminaSlider, p2Text, p2Overlay, p2Pointer});

        p2Panel.addTransform(new ScreenTransform(new Vector2(1, 0), new Vector2(-1, 1)));
    }

    public Panel getP1Panel() {
        return p1Panel;
    }

    public UiImage getP1Back() {
        return p1Back;
    }

    public Slider getP1HealthSlider() {
        return p1HealthSlider;
    }

    public Slider getP1StaminaSlider() {
        return p1StaminaSlider;
    }

    public TextView getP1Text() {
        return p1Text;
    }

    public UiImage getP1Overlay() {
        return p1Overlay;
    }

    public Panel getP2Panel() {
        return p2Panel;
    }

    public UiImage getP2Back() {
        return p2Back;
    }

    public Slider getP2HealthSlider() {
        return p2HealthSlider;
    }

    public Slider getP2StaminaSlider() {
        return p2StaminaSlider;
    }

    public TextView getP2Text() {
        return p2Text;
    }

    public UiImage getP2Overlay() {
        return p2Overlay;
    }

    public ClockPointer getP1Pointer() {
        return p1Pointer;
    }

    public ClockPointer getP2Pointer() {
        return p2Pointer;
    }
}
