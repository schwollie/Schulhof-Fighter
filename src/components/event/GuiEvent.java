package components.event;

import components.GuiComponent;

public class GuiEvent {
    private GuiComponent source;
    private GuiEventType eventType;

    public GuiEvent(GuiComponent source, GuiEventType eventType) {
        this.source = source;
        this.eventType = eventType;
    }

    public GuiComponent getSource() {
        return source;
    }

    public void setSource(GuiComponent source) {
        this.source = source;
    }

    public GuiEventType getEventType() {
        return eventType;
    }

    public void setEventType(GuiEventType eventType) {
        this.eventType = eventType;
    }
}
