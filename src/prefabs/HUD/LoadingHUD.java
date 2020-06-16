package prefabs.HUD;

import components.GuiCanvas;
import components.ScreenTransform;
import components.elements.Rect;
import components.elements.TextView;
import logic.Dimension2D;
import logic.Vector2;

import java.awt.*;

public class LoadingHUD extends GuiCanvas {


    public LoadingHUD(Dimension2D resolution) {
        super(resolution);

        Rect r = new Rect(this, new ScreenTransform(new Vector2(0, 0), new Vector2(1, 1)));
        r.setColor(new Color(0, 0, 0));
        r.setAlpha(1);
        addGuiComponent(r);

        ScreenTransform t = new ScreenTransform(new Vector2(0, 0), new Vector2(1, 1));
        TextView p = new TextView(this, t, "Loading...");
        p.setFontSize(20);
        //p.setTextColor(new Color(0,0,0));

        this.addGuiComponent(p);
    }
}
