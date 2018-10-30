package Application.Listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 * A simple MouseEvent Listner that only actions on a click
 * 
 * @author Geordie Rogers | rogersgeor1 | 300416218
 *
 */
public abstract class ClickListener implements MouseListener {

  public abstract void mouseClicked(MouseEvent e);

  @Override
  public void mousePressed(MouseEvent e) {
    // Does nothing

  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // Does nothing

  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // Does nothing

  }

  @Override
  public void mouseExited(MouseEvent e) {
    // Does nothing

  }

}
