package components;

import components.event.GuiEvent;
import components.event.GuiEventType;
import components.event.GuiListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public abstract class Panel extends JPanel implements MouseListener, MouseMotionListener {
    private ArrayList<GuiComponent> components = new ArrayList<>();
    private ArrayList<GuiListener> listeners = new ArrayList<>();

    public Panel() {
        super();
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (GuiComponent guiComponent : components) {
            guiComponent.paint(g);
        }
        repaint();
    }

    public void addGuiComponent(GuiComponent component) {
        component.setParentPanel(this);
        components.add(component);
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
                component.setPressed(true);
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
            component.setPressed(false);
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
                component.setHovered(true);
                evokeComponentAction(component, GuiEventType.DRAG);
            } else {
                component.setHovered(false);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (GuiComponent component : components) {
            if (isPointOnComponent(e.getX(), e.getY(), component)) {
                component.onHover();
                component.setHovered(true);
                evokeComponentAction(component, GuiEventType.MOVE);
            } else {
                component.setHovered(false);
            }
        }
    }

    protected boolean isPointOnComponent(int x, int y, GuiComponent component) {
        int maxX = component.getX() + component.getWidth();
        int maxY = component.getY() + component.getHeight();
        return x >= component.getX() && x <= maxX &&
                y >= component.getY() && y <= maxY;
    }

    private void evokeComponentAction(GuiComponent component, GuiEventType type) {
        GuiEvent event = new GuiEvent(component, type);
        for (GuiListener listener : listeners) {
            listener.onAction(event);
        }
    }
}
