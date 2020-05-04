package input;

import game.Game;
import gameobjects.ComponentType;
import gameobjects.GameComponent;
import gameobjects.GameObject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerInputManager extends GameComponent implements KeyListener {

    // region mapping:
    int upPressed1Mapping = KeyEvent.VK_W;
    int rightPressed1Mapping = KeyEvent.VK_D;
    int leftPressed1Mapping = KeyEvent.VK_A;
    int kickPressed1Mapping = KeyEvent.VK_Q;
    int punchPressed1Mapping = KeyEvent.VK_E;
    int blockPressed1Mapping = KeyEvent.VK_S;
    int projectilePressed1Mapping = KeyEvent.VK_X;

    int upPressed2Mapping = KeyEvent.VK_UP;
    int rightPressed2Mapping = KeyEvent.VK_RIGHT;
    int leftPressed2Mapping = KeyEvent.VK_LEFT;
    int kickPressed2Mapping = KeyEvent.VK_M;
    int punchPressed2Mapping = KeyEvent.VK_COMMA;
    int blockPressed2Mapping = KeyEvent.VK_DOWN;
    int projectilePressed2Mapping = KeyEvent.VK_ALT;
    //

    // region Key Flags:

    boolean upPressed1 = false; // W
    boolean rightPressed1 = false; // D
    boolean leftPressed1 = false; // A
    boolean kickPressed1 = false; // Q
    boolean punchPressed1 = false; // E
    boolean blockPressed1 = false; // S
    boolean projectilePressed1 = false; // Special1 = X

    boolean upPressed2 = false; // up arrow key
    boolean rightPressed2 = false; // right arrow key
    boolean leftPressed2 = false; // left arrow key
    boolean kickPressed2 = false; // comma
    boolean punchPressed2 = false; //  period
    boolean blockPressed2 = false; // right shift
    boolean projectilePressed2 = false; // Special1 = ;

    //endregion

    private PlayerKeyListener inputMapping1;
    private PlayerKeyListener inputMapping2;

    public PlayerInputManager(GameObject ref, ComponentType type) {
        super(ref, type);

        // add itself to listeners
        Game.inputManager.keyListeners.add(this);

    }

    @Override
    public void tick() {
        this.sendKeyStates();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //region Mapping 1:

        if (inputMapping1 != null) {


            if (e.getKeyCode() == rightPressed1Mapping) {
                rightPressed1 = false;
            }


            if (e.getKeyCode() == leftPressed1Mapping) {
                leftPressed1 = false;
            }


            if (e.getKeyCode() == upPressed1Mapping) {
                upPressed1 = false;
            }


            if (e.getKeyCode() == blockPressed1Mapping) {
                blockPressed1 = false;
            }


            if (e.getKeyCode() == punchPressed1Mapping) {
                punchPressed1 = false;
            }


            if (e.getKeyCode() == kickPressed1Mapping) {
                kickPressed1 = false;
            }


            if (e.getKeyCode() == projectilePressed1Mapping) {
                projectilePressed1 = false;
            }

        }
        //endregion

        if (inputMapping2 != null) {

            if (e.getKeyCode() == rightPressed2Mapping) {
                rightPressed2 = false;
            }

            if (e.getKeyCode() == leftPressed2Mapping) {
                leftPressed2 = false;
            }

            if (e.getKeyCode() == upPressed2Mapping) {
                upPressed2 = false;
            }

            if (e.getKeyCode() == punchPressed2Mapping) {
                punchPressed2 = false;
            }

            if (e.getKeyCode() == kickPressed2Mapping) {
                kickPressed2 = false;
            }

            if (e.getKeyCode() == blockPressed2Mapping) {
                blockPressed2 = false;
            }

            if (e.getKeyCode() == projectilePressed2Mapping) {
                projectilePressed2 = false;
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

            if (e.getKeyCode() == rightPressed1Mapping) {
                rightPressed1 = true;
            }

            if (e.getKeyCode() == leftPressed1Mapping) {
                leftPressed1 = true;
            }

            if (e.getKeyCode() == upPressed1Mapping) {
                upPressed1 = true;
            }

            if (e.getKeyCode() == blockPressed1Mapping) {
                blockPressed1 = true;
            }

            if (e.getKeyCode() == punchPressed1Mapping) {
                punchPressed1 = true;
            }

            if (e.getKeyCode() == kickPressed1Mapping) {
                kickPressed1 = true;
            }

            if (e.getKeyCode() == projectilePressed1Mapping) {
                projectilePressed1 = true;
            }

        }
        //endregion

        //region Mapping 2:

        if (inputMapping2 != null) {
            if (e.getKeyCode() == rightPressed2Mapping) {
                rightPressed2 = true;
            }

            if (e.getKeyCode() == leftPressed2Mapping) {
                leftPressed2 = true;
            }

            if (e.getKeyCode() == upPressed2Mapping) {
                upPressed2 = true;
            }

            if (e.getKeyCode() == punchPressed2Mapping) {
                punchPressed2 = true;
            }

            if (e.getKeyCode() == kickPressed2Mapping) {
                kickPressed2 = true;
            }

            if (e.getKeyCode() == blockPressed2Mapping) {
                blockPressed2 = true;
            }

            if (e.getKeyCode() == projectilePressed2Mapping) {
                projectilePressed2 = true;
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
        if (projectilePressed1) {
            inputMapping1.keyProjectile();
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
}
