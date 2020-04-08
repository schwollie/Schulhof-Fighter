package gui.components.event;

import gui.components.Component;

public class GuiEvent {
    private Component source;
    private GuiEventType eventType;

    public GuiEvent(Component source, GuiEventType eventType) {
        this.source = source;
        this.eventType = eventType;
    }

    public Component getSource() {
        return source;
    }

    public void setSource(Component source) {
        this.source = source;
    }

    public GuiEventType getEventType() {
        return eventType;
    }

    public void setEventType(GuiEventType eventType) {
        this.eventType = eventType;
    }
}
