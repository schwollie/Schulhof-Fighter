package prefabs.HUD;

import components.GuiCanvas;
import components.ScreenTransform;
import components.elements.Panel;
import components.elements.Slider;
import components.elements.TextView;
import components.elements.UiImage;
import logic.Dimension2D;
import logic.Vector2;

public class GameHUD extends GuiCanvas {

    public GameHUD(Dimension2D resolution) {
        super(resolution);
        createHud();
    }

    private void createHud() {
        //region hud for player1:
        Panel p = new Panel(this);
        UiImage stats = new UiImage(this, new ScreenTransform(new Vector2(0, 0), new Vector2(0.4, 0)), "images/Gui/stats.png");
        p.addComponent(stats);

        Slider hp = new Slider(this, new ScreenTransform(new Vector2(0.041, 0.0106), new Vector2(0.3236, 0)));
        hp.loadBar("images/Gui/bar1.png");
        hp.setProgress(.5);
        p.addComponent(hp);

        Slider xp = new Slider(this, new ScreenTransform(new Vector2(0.2145, 0.031), new Vector2(.149, 0)));
        xp.loadBar("images/Gui/bar2.png");
        xp.setProgress(.5);
        p.addComponent(xp);

        TextView txt = new TextView(this, new ScreenTransform(new Vector2(.11,.067), new Vector2(.1,.1)) ,"Hausperger");
        txt.setFontSize(12);
        p.addComponent(txt);

        UiImage overlay = new UiImage(this,  new ScreenTransform(new Vector2(0.038,0.01062), new Vector2(.329,.1)), "images/Gui/overlay1.png");
        p.addComponent(overlay);
        // endregion

        //region hud for player2:
        Panel p2 = new Panel(this);
        UiImage stats2 = new UiImage(this, new ScreenTransform(new Vector2(0, 0), new Vector2(0.4, 0)), "images/Gui/stats.png");
        p2.addComponent(stats2);

        Slider hp2 = new Slider(this, new ScreenTransform(new Vector2(0.041, 0.0106), new Vector2(0.3236, 0)));
        hp2.loadBar("images/Gui/bar1.png");
        hp2.setProgress(.5);
        p2.addComponent(hp2);

        Slider xp2 = new Slider(this, new ScreenTransform(new Vector2(0.2145, 0.031), new Vector2(.149, 0)));
        xp2.loadBar("images/Gui/bar2.png");
        xp2.setProgress(.5);
        p2.addComponent(xp2);

        TextView txt2 = new TextView(this, new ScreenTransform(new Vector2(.11,.067), new Vector2(.1,.1)) ,"Hausperger");
        txt2.setFontSize(12);
        p2.addComponent(txt2);

        UiImage overlay2 = new UiImage(this,  new ScreenTransform(new Vector2(0.038,0.01062), new Vector2(.329,.1)), "images/Gui/overlay1.png");
        p2.addComponent(overlay2);

        p2.addTransform(new ScreenTransform(new Vector2(1,0), new Vector2(-1, 1)));
        // endregion
    }
}
