package components;

import components.elements.Panel;
import components.elements.Slider;
import components.elements.TextAlign;
import components.elements.TextView;
import components.elements.UiImage;
import components.event.GuiEvent;
import components.event.GuiEventType;
import components.event.GuiListener;
import display.Camera;
import logic.Dimension2D;
import logic.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;

public class GuiCanvas extends JPanel implements MouseListener, MouseMotionListener {

    public static final double defaultRatio = 1.77777777777777777777777;

    private Dimension2D resolution;
    private final ArrayList<GuiComponent> components = new ArrayList<>();
    private final ArrayList<GuiListener> listeners = new ArrayList<>();

    public GuiCanvas(Dimension2D resolution) {
        super();
        this.resolution = resolution;
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void Render(Graphics2D g, Camera cam) {
        for (GuiComponent guiComponent : components) {
            guiComponent.Render(g, cam);
        }
        repaint();
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
            if (isPointOnComponent(e.getX(), e.getY(), component)) {
                component.onClick();
                evokeComponentAction(component, GuiEventType.CLICK);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (GuiComponent component : components) {
            if (isPointOnComponent(e.getX(), e.getY(), component)) {
                component.onPress();
                //component.setPressed(true);
                evokeComponentAction(component, GuiEventType.PRESS);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (GuiComponent component : components) {
            if (isPointOnComponent(e.getX(), e.getY(), component)) {
                component.onRelease();
                evokeComponentAction(component, GuiEventType.RELEASE);
            }
            //component.setPressed(false);
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
            if (isPointOnComponent(e.getX(), e.getY(), component)) {
                component.onDrag();
                //component.setHovered(true);
                evokeComponentAction(component, GuiEventType.DRAG);
            } else {
                //component.setHovered(false);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (GuiComponent component : components) {
            if (isPointOnComponent(e.getX(), e.getY(), component)) {
                component.onHover();
                //component.setHovered(true);
                evokeComponentAction(component, GuiEventType.MOVE);
            } else {
                //component.setHovered(false);
            }
        }
    }

    protected boolean isPointOnComponent(int x, int y, GuiComponent component) {
        int maxX = component.getInPixelX(resolution) + component.getInPixelWidth(resolution);
        int maxY = component.getInPixelY(resolution) + component.getInPixelHeight(resolution);
        return x >= component.getInPixelX(resolution) && x <= maxX &&
                y >= component.getInPixelY(resolution) && y <= maxY;
    }

    private void evokeComponentAction(GuiComponent component, GuiEventType type) {
        GuiEvent event = new GuiEvent(component, type);
        for (GuiListener listener : listeners) {
            listener.onAction(event);
        }
    }
}
