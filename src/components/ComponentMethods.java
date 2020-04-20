package components;

import display.Camera;

import java.awt.*;

public interface ComponentMethods {
    void Render(Graphics2D g, Camera cam);

    void onClick();

    void onPress();

    void onRelease();

    void onDrag();

    void onHover();
}
