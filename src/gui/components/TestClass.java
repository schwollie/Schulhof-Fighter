package gui.components;

import gui.components.event.GuiEvent;
import gui.components.event.GuiEventType;
import gui.components.event.GuiListener;

public class TestClass implements GuiListener {
    @Override
    public void onAction(GuiEvent event) {
        if (event.getEventType() != GuiEventType.MOVE && event.getEventType() != GuiEventType.DRAG)
            System.out.println(event.getEventType().toString());
    }
}
