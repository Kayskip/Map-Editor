package Application.Listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Simplifies the key press Listener.
 * 
 * @author Geordie Rogers | rogersgeor1 | 300416218
 *
 */
public abstract class TypeListener implements KeyListener {

  @Override
  public abstract void keyTyped(KeyEvent e);

  @Override
  public void keyPressed(KeyEvent e) {
    // Does Nothing

  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Does Nothing

  }

}
