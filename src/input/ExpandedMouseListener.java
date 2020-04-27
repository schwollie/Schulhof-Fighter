package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface ExpandedMouseListener extends MouseListener, MouseMotionListener {

    @Override
    default void mouseClicked(MouseEvent e) {}

    @Override
    default void mousePressed(MouseEvent e) {}

    @Override
    default void mouseReleased(MouseEvent e) {}

    @Override
    default void mouseEntered(MouseEvent e) {}

    @Override
    default void mouseExited(MouseEvent e) {}

    @Override
    default void mouseDragged(MouseEvent e) {}

    @Override
    default void mouseMoved(MouseEvent e) {}

}
