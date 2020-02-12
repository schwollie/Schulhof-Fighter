package input;

import player.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InputManager implements KeyListener {

    // region Key Flags:

    boolean upPressed1 = false;
    boolean rightPressed1 = false;
    boolean leftPressed1 = false;
    boolean fightPressed1 = false;

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
    }

    @Override
    public void keyPressed(KeyEvent e) {
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
    }


    private void resetMapping1Flags() {
        upPressed1 = false;
        rightPressed1 = false;
        leftPressed1 = false;
        fightPressed1 = false;
    }

    public void sendKeyStates() {
        sendMapping1();
    }

    private void sendMapping1() {
        if (upPressed1) { inputMapping1.keyUp(); }
        if (leftPressed1) { inputMapping1.keyLeft(); }
        if (rightPressed1) { inputMapping1.keyRight(); }
    }

    public void setListenerMapping1(PlayerKeyListener p) { inputMapping1 = p; }
    public void setListenerMapping2(PlayerKeyListener p) { inputMapping2 = p; }

}
