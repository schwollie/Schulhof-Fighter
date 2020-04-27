package components;

import display.Camera;

import java.awt.*;

public interface ComponentMethods {

    void Render(Graphics2D g, Camera cam);

    default void onClick() {}

    default void onPress() {}

    default void onRelease() {}

    default void onDrag() {}

    default void onHoverEnter() {}

    default void onHoverExit() {}
}
