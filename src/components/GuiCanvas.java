package components;

import components.elements.TextView;
import components.event.GuiEvent;
import components.event.GuiEventType;
import components.event.GuiListener;
import display.Camera;
import game.Game;
import input.ExpandedMouseListener;
import logic.Dimension2D;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GuiCanvas implements ExpandedMouseListener {

    public static final double defaultRatio = 1.77777777777777777777777;

    protected Game game;

    private Dimension2D resolution;
    private final ArrayList<GuiComponent> components = new ArrayList<>();
    private final ArrayList<GuiListener> listeners = new ArrayList<>();

    public GuiCanvas(Game game, Dimension2D resolution) {
        super();
        this.game = game;
        this.resolution = resolution;

        game.getInputManager().mouseListeners.add(this);
    }

    public void Render(Graphics2D g, Camera cam) {
        for (GuiComponent guiComponent : components) {
            if (!(guiComponent instanceof TextView)) {
                guiComponent.Render(g, cam);
            }
        }
        for (GuiComponent guiComponent : components) {
            if (guiComponent instanceof TextView) {
                guiComponent.Render(g, cam);
            }
        }
        //repaint();
    }

    public void addGuiComponent(GuiComponent component) {
        this.components.add(component);
    }

    public void addGuiComponents(ArrayList<GuiComponent> components) {
        this.components.addAll(components);
    }

    public void removeGuiComponent(GuiComponent component) {
        components.remove(component);
    }

    public ArrayList<GuiComponent> getGuiComponents() {
        return components;
    }

    public ArrayList<GuiListener> getListeners() {
        return listeners;
    }

    public void addGuiListener(GuiListener listener) {
        listeners.add(listener);
    }

    public double getRatio() {
        return this.resolution.getWidth() / this.resolution.getHeight();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (GuiComponent component : components) {
            if (component.isPointOnComponent(e.getX(), e.getY())) {
                component.onClick();
                evokeComponentAction(component, GuiEventType.CLICK);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (GuiComponent component : components) {
            if (component.isPointOnComponent(e.getX(), e.getY())) {
                component.onPress();
                evokeComponentAction(component, GuiEventType.PRESS);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (GuiComponent component : components) {
            if (component.isPointOnComponent(e.getX(), e.getY())) {
                component.onRelease();
                evokeComponentAction(component, GuiEventType.RELEASE);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (GuiComponent component : components) {
            if (component.isPointOnComponent(e.getX(), e.getY())) {
                component.onDrag();
                evokeComponentAction(component, GuiEventType.DRAG);
            } else {
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (GuiComponent component : components) {
            if (component.isPointOnComponent(e.getX(), e.getY())) {
                component.onHoverEnter();
                evokeComponentAction(component, GuiEventType.MOVE);
            } else {
                component.onHoverExit();
            }
        }
    }

    private void evokeComponentAction(GuiComponent component, GuiEventType type) {
        GuiEvent event = new GuiEvent(component, type);
        for (GuiListener listener : listeners) {
            listener.onAction(event);
        }
    }

    public Game getGame() {
        return game;
    }
}
