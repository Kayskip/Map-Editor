package tests;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.junit.jupiter.api.Test;
import Application.AdventureGame;
import Application.EventHandler;
import Application.Components.GamePanel;
import Application.Components.InventoryPanel;
import Application.Listeners.ClickListener;
import Game_World.Game;
import Game_World.Item;
import Game_World.Key;

/**
 * A suite of tests to ensure the classes in the Application package are working as intended.
 * 
 * @author geordier
 */
public class ApplicationTests {

  /**
   * Test that the frame is created correctly
   */
  @Test
  public void testFrameCreation() {
    AdventureGame av = new AdventureGame();
    assert av != null;
    assert av.creationValid();
    String[] args = {""};
    AdventureGame.main(args);
  }

  /**
   * Check that the GamePanel works and throws exceptions as intended.
   */
  @Test
  public void testGamePanels() {

    // Check creation works as intended
    try {
      JPanel test = new GamePanel("src/Application/img/controll.png");
    } catch (FileNotFoundException e) {
      assert false;
    }

    // Check that an error is thrown
    try {
      JPanel test = new GamePanel("thisIsNotAFileString");
      assert false;
    } catch (FileNotFoundException e) {
      assert true;
    }

  }

  /**
   * Test each area of the gamePad is working when clicked
   */
  @Test
  public void testGamePad() {
    EventHandler ev = new EventHandler(new Game(), new AdventureGame());

    MouseEvent right = new MouseEvent(new JPanel(), 0, 0, 0, 95, 65, 1, false);
    ev.clickedGamePad(right);

    MouseEvent left = new MouseEvent(new JPanel(), 0, 0, 0, 30, 85, 1, false);
    ev.clickedGamePad(left);

    MouseEvent up = new MouseEvent(new JPanel(), 0, 0, 0, 70, 40, 1, false);
    ev.clickedGamePad(up);

    MouseEvent down = new MouseEvent(new JPanel(), 0, 0, 0, 65, 120, 1, false);
    ev.clickedGamePad(down);

  }

  /**
   * Test key based navigation
   */
  @Test
  public void testKeyInput() {
    EventHandler ev = new EventHandler(new Game(), new AdventureGame());
    
    KeyEvent right = new KeyEvent(new JPanel(), 0, 0, 0, KeyEvent.VK_D, 'd');
    ev.keyPressed(right);

    KeyEvent left = new KeyEvent(new JPanel(), 0, 0, 0, KeyEvent.VK_A, 'a');
    ev.keyPressed(left);

    KeyEvent up = new KeyEvent(new JPanel(), 0, 0, 0, KeyEvent.VK_W, 'w');
    ev.keyPressed(up);

    KeyEvent down = new KeyEvent(new JPanel(), 0, 0, 0, KeyEvent.VK_S, 's');
    ev.keyPressed(down);
  }

  /**
   * Test the file opener closes properly when you select cancel.
   */
  @Test
  public void testFilesCanceled() {
    AdventureGame av = new AdventureGame();
    assert av.chooseFile(true) == null;
    assert av.chooseFile(false) == null;
    av.close(false);
  }

  /**
   * Test the pop up dialouges are running correctly.
   */
  @Test
  public void testPrompts() {

    AdventureGame.fileNotFoundDialouge("Made up path");
    AdventureGame.promptUser();
  }

  /**
   * 
   */
  @Test
  public void testInventoryPanel() {
    try {
      InventoryPanel ip = new InventoryPanel("src/Application/img/controll.png");

      // Should not throw an error when adding null
      ip.updateInventory(null);

      // Should not throw an error when adding an empty list
      ArrayList<Item> tmp = new ArrayList<>();
      ip.updateInventory(tmp);

      // Should not throw an error when passing a list of items
      tmp.add(new Key('e'));
      tmp.add(new Key('e'));
      tmp.add(new Key('e'));
      ip.updateInventory(tmp);

      // Check that it redraws without an error
      ip.repaint();

    } catch (Exception e) {
      assert false;
    }
  }
}
