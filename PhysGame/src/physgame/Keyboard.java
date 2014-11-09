/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Tripp
 */
public class Keyboard  implements KeyListener {
    boolean key[] = new boolean[500];
    boolean space = false;
    
    @Override
    public void keyTyped(KeyEvent ke) {
        key[ke.getKeyCode()] = true;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        key[ke.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        key[ke.getKeyCode()] = false;
    }
    
    public void update(){
        space = key[KeyEvent.VK_SPACE];
    }
}
