package gui.components;

import java.awt.*;

public interface ComponentMethods {
    void paint(Graphics g);

    void onClick();

    void onPress();

    void onRelease();

    void onDrag();

    void onHover();
}
