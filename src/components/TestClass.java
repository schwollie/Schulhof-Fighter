package components;

import components.event.GuiEvent;
import components.event.GuiEventType;
import components.event.GuiListener;

public class TestClass implements GuiListener {
    @Override
    public void onAction(GuiEvent event) {
        if (event.getEventType() != GuiEventType.MOVE && event.getEventType() != GuiEventType.DRAG)
            System.out.println(event.getEventType().toString());
    }
}
