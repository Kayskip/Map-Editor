package Application.Listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Simplifies the WindowListner to only listen for closing events.
 * 
 * @author Geordie Rogers | rogersgeor1 | 300416218
 *
 */
public abstract class CloseListener implements WindowListener {

  @Override
  public void windowOpened(WindowEvent e) {
    // Does nothing
  }

  @Override
  abstract public void windowClosing(WindowEvent e);

  @Override
  public void windowClosed(WindowEvent e) {
    // Does nothing
  }

  @Override
  public void windowIconified(WindowEvent e) {
    // Does nothing
  }

  @Override
  public void windowDeiconified(WindowEvent e) {
    // Does nothing
  }

  @Override
  public void windowActivated(WindowEvent e) {
    // Does nothing
  }

  @Override
  public void windowDeactivated(WindowEvent e) {
    // Does nothing
  }

}
