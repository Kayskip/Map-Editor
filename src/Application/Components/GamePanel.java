package Application.Components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A sidepanel with a custom backgound image for our adventureGame.
 * 
 * @author Geordie Rogers | rogersgeor1 | 300416218
 *
 */
public class GamePanel extends JPanel {

  private static final long serialVersionUID = 6772291726111332483L;
  private static BufferedImage BG_IMG;

  /**
   * Creates a new JPanel with a custom image as the background.
   * 
   * @throws FileNotFoundException
   */
  public GamePanel(String fileName) throws FileNotFoundException {
    try {
      BG_IMG = ImageIO.read(new File(fileName));
    } catch (Exception e) {
      throw new FileNotFoundException();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);
    g.drawImage(BG_IMG, 0, 0, null);
  }

}
