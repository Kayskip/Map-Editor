package Renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Game_World.BeltSegment;
import Game_World.Door;
import Game_World.Floor;
import Game_World.Item;
import Game_World.Key;
import Game_World.Player;
import Game_World.Wall;

/**
 * Class acts as a JPanel which gets displayed on the appropriate JFrame.
 *
 * @author Drew Forrest 300423875
 *
 */
public class Render extends JPanel {

	private static final long serialVersionUID = 8434540262280271898L;
	private static int TILE_SIZE = 25;

	private Item[][] items;

	// Wall block image
	Image wallImage = new ImageIcon("src/Renderer/img/wall.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);

	// Images for keys and doors depending on orientation.
	Image keyImage = new ImageIcon("src/Renderer/img/key.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);
	Image doorImageVertical = new ImageIcon("src/Renderer/img/doorVertical.png").getImage().getScaledInstance(TILE_SIZE,
			TILE_SIZE, Image.SCALE_DEFAULT);
	Image doorImageHorizontal = new ImageIcon("src/Renderer/img/doorHorizontal.png").getImage()
			.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT);

	// Floor image for null spaces in the array
	Image floorImage = new ImageIcon("src/Renderer/img/floor.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);

	// Two sides of the belt object.
	Image belt1 = new ImageIcon("src/Renderer/img/beltSegment1.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);
	Image belt2 = new ImageIcon("src/Renderer/img/beltSegment2.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);

	/**
	 * Render constructor sets items field to input map parameter, sets JPanel size
	 * to 500*500 and makes it visible.
	 *
	 * @param map
	 *            - a 2D array of the items in the current location which determines
	 *            the render output.
	 */
	public Render(Item[][] map) {
		this.items = map;
		setSize(500, 500);
		setVisible(true);
	}

	/**
	 * Paint method uses a graphics object to display each item contained in the
	 * item array on the JPanel.
	 */
	@Override
	public void paint(Graphics g) {
		if (items == null) {
			throw new RuntimeException("No items array passed");
		}

		// Iterates through 2D Item array.
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++) {
				// If it's a wall, draw a wall image.
				if (items[x][y] instanceof Wall) {
					g.drawImage(wallImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
					// If it's a key, draw a key image on top of a floor image.
				} else if (items[x][y] instanceof Key) {
					g.drawImage(floorImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
					g.drawImage(keyImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
					// If it's a door, draw a door image on top of a floor image with a yellow
					// rectangle lock.
				} else if (items[x][y] instanceof Door) {
					// Create a door object
					Door d = (Door) items[x][y];
					g.drawImage(floorImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
					// Draw yellow lock.
					g.setColor(Color.yellow);
					g.fillRect(x * TILE_SIZE + 9, y * TILE_SIZE + 9, 7, 7);
					// If door is vertical draw door image vertical.
					if (d.getVert()) {
						g.drawImage(doorImageVertical, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
						// Else it's horizontal so draw draw image horizontal.
					} else {
						g.drawImage(doorImageHorizontal, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
					}
					// If it's a BeltSegment, draw a BeltSegment image.
				} else if (items[x][y] instanceof BeltSegment) {
					// Create a BeltSegment object.
					BeltSegment b = (BeltSegment) items[x][y];
					g.drawImage(floorImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
					// If it's the first half of the belt then draw image first half.
					if (b.getID() == 1) {
						g.drawImage(belt1, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
						// If it's the second half of the belt then draw image second half.
					} else {
						g.drawImage(belt2, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

					}
					// If it's a null, draw a floor image.
				} else if (items[x][y] instanceof Floor) {
					g.drawImage(floorImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
					// If it's the player, then check orientation and draw Player facing N, S, E or
					// W.
				} else if (items[x][y] instanceof Player) {
					Player p = (Player) items[x][y];
					g.drawImage(floorImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
					// Player is facing North.
					if (p.direction.equals("N")) {
						g.setColor(Color.red);
						g.fillOval(x * TILE_SIZE + 3, y * TILE_SIZE + 2, 8, 8);
						g.fillOval(x * TILE_SIZE + 14, y * TILE_SIZE + 2, 8, 8);
						g.setColor(Color.black);
						g.fillOval(x * TILE_SIZE + 4, y * TILE_SIZE + 4, 17, 17);
						g.setColor(Color.white);
						g.fillRect(x * TILE_SIZE + 10, y * TILE_SIZE + 8, 2, 2);
						g.fillRect(x * TILE_SIZE + 14, y * TILE_SIZE + 8, 2, 2);
						// Player is facing East.
					} else if (p.direction.equals("E")) {
						g.setColor(Color.red);
						g.fillOval(x * TILE_SIZE + 15, y * TILE_SIZE + 14, 8, 8);
						g.fillOval(x * TILE_SIZE + 15, y * TILE_SIZE + 3, 8, 8);
						g.setColor(Color.black);
						g.fillOval(x * TILE_SIZE + 4, y * TILE_SIZE + 4, 17, 17);
						g.setColor(Color.white);
						g.fillRect(x * TILE_SIZE + 15, y * TILE_SIZE + 10, 2, 2);
						g.fillRect(x * TILE_SIZE + 15, y * TILE_SIZE + 14, 2, 2);
						// Player is facing South.
					} else if (p.direction.equals("S")) {
						g.setColor(Color.red);
						g.fillOval(x * TILE_SIZE + 3, y * TILE_SIZE + 15, 8, 8);
						g.fillOval(x * TILE_SIZE + 14, y * TILE_SIZE + 15, 8, 8);
						g.setColor(Color.black);
						g.fillOval(x * TILE_SIZE + 4, y * TILE_SIZE + 4, 17, 17);
						g.setColor(Color.white);
						g.fillRect(x * TILE_SIZE + 10, y * TILE_SIZE + 15, 2, 2);
						g.fillRect(x * TILE_SIZE + 14, y * TILE_SIZE + 15, 2, 2);
						// Else player is facing West.
					} else {
						g.setColor(Color.red);
						g.fillOval(x * TILE_SIZE + 2, y * TILE_SIZE + 14, 8, 8);
						g.fillOval(x * TILE_SIZE + 2, y * TILE_SIZE + 3, 8, 8);
						g.setColor(Color.black);
						g.fillOval(x * TILE_SIZE + 4, y * TILE_SIZE + 4, 17, 17);
						g.setColor(Color.white);
						g.fillRect(x * TILE_SIZE + 8, y * TILE_SIZE + 10, 2, 2);
						g.fillRect(x * TILE_SIZE + 8, y * TILE_SIZE + 14, 2, 2);
					}
				}
			}
		}
	}
}
