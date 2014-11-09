package physgame;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseWheel implements MouseWheelListener
{
    public int notches;
    
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        notches = e.getWheelRotation();
    }
}
