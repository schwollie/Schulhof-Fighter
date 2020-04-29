package input;

import game.Game;
import logic.Vector2;

import java.awt.event.*;
import java.util.ArrayList;

public class InputManager implements KeyListener, MouseMotionListener, MouseListener {

    public ArrayList<KeyListener> keyListeners = new ArrayList<>();
    public ArrayList<ExpandedMouseListener> mouseListeners = new ArrayList<>();

    //mouse location
    private Vector2 mousePosition = new Vector2(0, 0);

    private Game game;

    public InputManager(Game game) {
        this.game = game;
    }

    // region keys
    @Override
    public void keyTyped(KeyEvent e) {
        keyListeners.forEach(keyListener -> keyListener.keyTyped(e));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyListeners.forEach(keyListener -> keyListener.keyReleased(e));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //general
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            game.window.changeScreenSize();
        }
        keyListeners.forEach(keyListener -> {
            keyListener.keyPressed(e);
        });
    }

    //endregion

    //region mouse:
    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition.setX(e.getX());
        mousePosition.setY(e.getY());

        mouseListeners.forEach(motionListener -> {motionListener.mouseDragged(e);});
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseListeners.forEach(mouseListener -> mouseListener.mouseMoved(e));

        mousePosition.setX(e.getX());
        mousePosition.setY(e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseListeners.forEach(mouseListener -> mouseListener.mouseClicked(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseListeners.forEach(mouseListener -> mouseListener.mousePressed(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseListeners.forEach(mouseListener -> mouseListener.mouseReleased(e));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseListeners.forEach(mouseListener -> mouseListener.mouseEntered(e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseListeners.forEach(mouseListener -> mouseListener.mouseExited(e));
    }
    // endregion


    //For the main menu
    public Vector2 getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(Vector2 mousePosition) {
        this.mousePosition = mousePosition;
    }
}
