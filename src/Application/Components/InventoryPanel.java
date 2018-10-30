package Application.Components;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import Application.AdventureGame;
import Game_World.Item;
import Game_World.Key;

/**
 * An inventory panel to diplay the users inventory.
 * 
 * @author Geordie Rogers | rogersgeor1 | 300416218
 *
 */
public class InventoryPanel extends GamePanel {

  private static final long serialVersionUID = -1735772738161413015L;

  private ArrayList<Item> inventory;

  /**
   * Create a new GamePanel to add the inventory on top of
   * 
   * @param fileName
   * @throws FileNotFoundException
   */
  public InventoryPanel(String fileName) throws FileNotFoundException {
    super(fileName);
    inventory = new ArrayList<>();
  }

  /**
   * Redraw the inventory panel with an updated inventory list
   * 
   * @param inv
   */
  public void updateInventory(List<Item> inv) {
    this.inventory.clear();
    if (inv == null)
      return;
    if (inv.isEmpty())
      return;
    this.inventory.addAll(inv);
    this.repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (inventory == null)
      return;
    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i) instanceof Key) {
        try {
          Image key = ImageIO.read(new File("src/Map_Editor/images/key.png"));
          g.drawImage(key.getScaledInstance(75, 75, Image.SCALE_SMOOTH), (i % 2) * 75, (i / 2) * 75,
              this);
        } catch (IOException e) {
          AdventureGame.fileNotFoundDialouge("src/Map_Editor/images/key.png");
        }
      }
    }
  }

}
