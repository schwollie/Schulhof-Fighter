package input;

import game.Consts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class InputManager implements KeyListener, MouseMotionListener {

    // region Key Flags:

    private boolean upPressed1 = false;
    boolean rightPressed1 = false;
    boolean leftPressed1 = false;
    boolean fightPressed1 = false;

    boolean upPressed2 = false;
    boolean rightPressed2 = false;
    boolean leftPressed2 = false;
    boolean fightPressed2 = false;

    //endregion

    private PlayerKeyListener inputMapping1;
    private  PlayerKeyListener inputMapping2;

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

        }
        //endregion
    }

    public void sendKeyStates() {
        sendMapping1();
        sendMapping2();
    }

    private void sendMapping1() {
        if (upPressed1) { inputMapping1.keyUp(); }
        if (leftPressed1) { inputMapping1.keyLeft(); }
        if (rightPressed1) { inputMapping1.keyRight(); }
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
    }

    public void setListenerMapping1(PlayerKeyListener p) {
        inputMapping1 = p;
    }

    public void setListenerMapping2(PlayerKeyListener p) {
        inputMapping2 = p;
    }


    //For the main menu
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Consts.mousePosition.setX(e.getX());
        Consts.mousePosition.setY(e.getY());
    }
}
