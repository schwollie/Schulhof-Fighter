package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


// used to transform all components of this panel at the same time
// does not inherit from GuiComponent because its just a data container
public class Panel {

    private LinkedList<GuiComponent> components = new LinkedList<>();
    private final GuiCanvas parentGui;

    public Panel(GuiCanvas canvas) {
        parentGui = canvas;
    }

    public void addTransform(ScreenTransform screenTransform) {
        for (GuiComponent g : components) {
            g.addTransform(screenTransform);
        }
    }

    public void addComponent(GuiComponent e) {
        components.add(e);
        parentGui.addGuiComponent(e);
    }

    public void addComponents(GuiComponent[] es) {
        components.addAll(new ArrayList<>(Arrays.asList(es)));
        parentGui.addGuiComponents(new ArrayList<>(Arrays.asList(es)));
    }

    public void removeComponent(GuiComponent e, boolean removeFromParent) {
        this.components.remove(e);

        if (removeFromParent) {
            this.parentGui.removeGuiComponent(e);
        }
    }

    public void setVisible() {
        for (GuiComponent g : components) {
            g.setVisible();
        }
    }

    public void hideElements() {
        for (GuiComponent g : components) {
            g.hideElement();
        }
    }

    public LinkedList<GuiComponent> getComponents() {
        return components;
    }
}
