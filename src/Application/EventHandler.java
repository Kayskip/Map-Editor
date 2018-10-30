package Application;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import Game_World.Game;

/**
 * @author Geordie Rogers | rogersgeor1 | 300416218
 * 
 *         This class handels all of the events that can happen in the GUI. This class is
 *         responsavle for deciding who needs to be notified that an action has occoured in the GUI.
 */
public class EventHandler {

  // The bounds for each button on the gamepad
  private static Rectangle PAD_RIGHT = new Rectangle(90, 60, 35, 35);
  private static Rectangle PAD_LEFT = new Rectangle(25, 55, 35, 35);
  private static Rectangle PAD_UP = new Rectangle(60, 30, 35, 35);
  private static Rectangle PAD_DOWN = new Rectangle(55, 100, 35, 35);

  private Game game;
  private AdventureGame app;

  /**
   * Create a new EventHandler that will notify the game of events and call on Application to
   * refresh once they have been acted on
   * 
   * @param game2 The Game_World instance
   * @param app The UI instance
   */
  public EventHandler(Game_World.Game game2, AdventureGame app) {
    this.game = game2;
    this.app = app;
  }

  /**
   * Control which actions are fired based on the location of the click. Asumes the click was on the
   * GamePad.
   * 
   * @param e The click event
   */
  public void clickedGamePad(MouseEvent e) {
    if (PAD_RIGHT.contains(e.getX(), e.getY())) {
      moveRight();
    } else if (PAD_LEFT.contains(e.getX(), e.getY())) {
      moveLeft();
    } else if (PAD_UP.contains(e.getX(), e.getY())) {
      moveUp();
    } else if (PAD_DOWN.contains(e.getX(), e.getY())) {
      moveDown();
    }
    app.gameGUI.repaint();
    app.gameGUI.setVisible(true);
  }

  /**
   * Controls which actions are fired based on what key was pressed.
   * 
   * @param e The KeyEvent
   */
  public void keyPressed(KeyEvent e) {
    char c = e.getKeyChar();
    if (c == 'W' || c == 'w')
      moveUp();
    if (c == 'A' || c == 'a')
      moveLeft();
    if (c == 'S' || c == 's')
      moveDown();
    if (c == 'D' || c == 'd')
      moveRight();
    app.gameGUI.repaint();
    app.gameGUI.setVisible(true);
  }

  // ----------
  // Notify Game Components
  // ----------

  /**
   * Notifies game of moveLeft event.
   */
  private void moveLeft() {
    game.left();
  }

  /**
   * Notifies game of moveRight event.
   */
  private void moveRight() {
    game.right();
  }

  /**
   * Notifies game of moveUp event.
   */
  private void moveUp() {
    game.up();
  }

  /**
   * Notifies game of moveDown event.
   */
  private void moveDown() {
    game.down();
  }

}
