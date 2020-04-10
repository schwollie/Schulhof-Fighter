package input;

import logic.Vector2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class InputManager implements KeyListener, MouseMotionListener {

    // region Key Flags:

    private boolean upPressed1 = false; // W
    boolean rightPressed1 = false; // D
    boolean leftPressed1 = false; // A
    boolean kickPressed1 = false; // Q
    boolean punchPressed1 = false; // E
    boolean blockPressed1 = false; // Left Shift

    boolean upPressed2 = false; // up arrow key
    boolean rightPressed2 = false; // right arrow key
    boolean leftPressed2 = false; // left arrow key
    boolean kickPressed2 = false; // comma
    boolean punchPressed2 = false; //  period
    boolean blockPressed2 = false; // right shift

    //endregion

    private PlayerKeyListener inputMapping1;
    private PlayerKeyListener inputMapping2;

    //mouse location
    private Vector2 mousePosition = new Vector2(0, 0);

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //region Mapping 1:

        if (inputMapping1 != null) {

            // D = right
            if (e.getKeyCode() == KeyEvent.VK_D) {
                rightPressed1 = false;
            }

            // A = left
            if (e.getKeyCode() == KeyEvent.VK_A) {
                leftPressed1 = false;
            }

            // W = Up
            if (e.getKeyCode() == KeyEvent.VK_W) {
                upPressed1 = false;
            }

            // Shift = block
            if (e.getKeyCode() == KeyEvent.VK_SHIFT && e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
                blockPressed1 = false;
            }

            // E = punch
            if (e.getKeyCode() == KeyEvent.VK_E) {
                punchPressed1 = false;
            }

            // Q = kick
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                kickPressed1 = false;
            }

        }
        //endregion

        if (inputMapping2 != null) {

            // D = right
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed2 = false;
            }

            // A = left
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed2 = false;
            }

            // W = Up
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed2 = false;
            }

            // Period = Punch
            if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
                punchPressed2 = false;
            }

            // Comma = Kick
            if (e.getKeyCode() == KeyEvent.VK_COMMA) {
                kickPressed2 = false;
            }

            // right alt = Block
            if (e.getKeyCode() == KeyEvent.VK_ALT && e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) {
                blockPressed1 = false;
            }

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //general
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

        //region Mapping 1:

        if (inputMapping1 != null) {
            // D = right
            if (e.getKeyCode() == KeyEvent.VK_D) {
                rightPressed1 = true;
            }

            // A = left
            if (e.getKeyCode() == KeyEvent.VK_A) {
                leftPressed1 = true;
            }

            // W = Up
            if (e.getKeyCode() == KeyEvent.VK_W) {
                upPressed1 = true;
            }

            // Shift = block
            if (e.getKeyCode() == KeyEvent.VK_SHIFT && e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT) {
                blockPressed1 = true;
            }

            // E = punch
            if (e.getKeyCode() == KeyEvent.VK_E) {
                punchPressed1 = true;
            }

            // Q = kick
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                kickPressed1 = true;
            }

        }
        //endregion

        //region Mapping 2:

        if (inputMapping2 != null) {

            // D = right
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed2 = true;
            }

            // A = left
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed2 = true;
            }

            // W = Up
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed2 = true;
            }

            // Period = Punch
            if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
                punchPressed2 = true;
            }

            // Comma = Kick
            if (e.getKeyCode() == KeyEvent.VK_COMMA) {
                kickPressed2 = true;
            }

            // right alt = Block
            if (e.getKeyCode() == KeyEvent.VK_ALT && e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) {
                blockPressed1 = true;
            }

        }
        //endregion
    }

    public void sendKeyStates() {
        sendMapping1();
        sendMapping2();
    }

    private void sendMapping1() {
        if (upPressed1) {
            inputMapping1.keyUp();
        }
        if (leftPressed1) {
            inputMapping1.keyLeft();
        }
        if (rightPressed1) {
            inputMapping1.keyRight();
        }
        if (kickPressed1) {
            inputMapping1.keyKick();
        }
        if (punchPressed1) {
            inputMapping1.keyPunch();
        }
        if (blockPressed1) {
            inputMapping1.keyBlock();
        }
    }

    private void sendMapping2() {
        if (upPressed2) {
            inputMapping2.keyUp();
        }
        if (leftPressed2) {
            inputMapping2.keyLeft();
        }
        if (rightPressed2) {
            inputMapping2.keyRight();
        }
        if (kickPressed2) {
            inputMapping2.keyKick();
        }
        if (punchPressed2) {
            inputMapping2.keyPunch();
        }
        if (blockPressed2) {
            inputMapping2.keyBlock();
        }
    }

    public void setListenerMapping1(PlayerKeyListener p) {
        inputMapping1 = p;
    }

    public void setListenerMapping2(PlayerKeyListener p) {
        inputMapping2 = p;
    }

    //For the main menu
    public Vector2 getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(Vector2 mousePosition) {
        this.mousePosition = mousePosition;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition.setX(e.getX());
        mousePosition.setY(e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition.setX(e.getX());
        mousePosition.setY(e.getY());
    }
}
