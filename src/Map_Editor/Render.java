package Map_Editor;

import java.awt.*;
import javax.swing.*;

import Game_World.BeltSegment;
import Game_World.Door;
import Game_World.Key;
import Game_World.Player;
import Game_World.Wall;

/**
 * @author Karu Skipper 300417869
 *
 */
public class Render extends JPanel {
	private static final long serialVersionUID = 1806055759201845690L;
	/**
	 * Tile size which is altered once the map is in glued mode
	 */
	public static final int TILE_SIZE = 40;
	/**
	 * Map offset to the left
	 */
	public static final int MAP_OFFSET = 440;
	/**
	 * Glue option has been selected
	 */
	public static boolean glued = false;
	/**
	 * Image of a key, scaled nicely to fit on the screen, used in Window as image
	 * icon to display button
	 */
	public static Image keyImage = new ImageIcon("src/images/key.png").getImage()
			.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT);
	/**
	 * Image of a door, scaled nicely to fit on the screen, used in Window as image
	 * icon to display button
	 */
	public static Image doorImage = new ImageIcon("src/images/door_bak.png").getImage()
			.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT);
	/**
	 * Image of a wall, scaled nicely to fit on the screen, used in Window as image
	 * icon to display button
	 */
	public static Image wallImage = new ImageIcon("src/images/wall.png").getImage()
			.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT);
	/**
	 * Image of a eraser, scaled nicely to fit on the screen, used in Window as
	 * image icon to display button
	 */
	public static Image eraseImage = new ImageIcon("src/images/eraser.png").getImage()
			.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT);

	/**
	 * Image of a player, scaled nicely to fit on the screen, used in Window as
	 * image icon to display button
	 */
	public static Image playerImage = new ImageIcon("src/images/player.png").getImage()
			.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT);

	/**
	 * Image of a beltLeftSegement, scaled nicely to fit on the screen, used in
	 * Window as image icon to display button
	 */
	public static Image beltLeftImage = new ImageIcon("src/images/belt_left.png").getImage()
			.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT);
	/**
	 * Image of a beltRightSegement, scaled nicely to fit on the screen, used in
	 * Window as image icon to display button
	 */
	public static Image beltRightImage = new ImageIcon("src/images/belt_right.png").getImage()
			.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_DEFAULT);

	/**
	 * Constructor which sets the size of the grid
	 */
	public Render() {
		setSize(600, 600);
		setVisible(true);

	}

	@Override
	public void paint(Graphics g) {
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++) {

				if (!glued) {
					g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

					if (Loader.items[x][y] instanceof Wall) {
						g.drawImage(wallImage, y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

					} else if (Loader.items[x][y] instanceof Key) {
						g.drawImage(keyImage, y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

					} else if (Loader.items[x][y] instanceof Door) {
						g.drawImage(doorImage, y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

					} else if (Loader.items[x][y] instanceof Player) {
						g.drawImage(playerImage, y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

					} else if (Loader.items[x][y] instanceof BeltSegment) {
						BeltSegment belt = (BeltSegment) Loader.items[x][y];
						if (belt.getID() == 1)
							g.drawImage(beltLeftImage, y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
						else if (belt.getID() == 2) {
							g.drawImage(beltRightImage, y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
						}
					}
				} else {
					this.glued(x, y, g);
				}
			}
		}

	}

	/**
	 * Handles gluing render of the maps, the first draw is the first map, the
	 * second is the second map Mouse clicks have been offset to work on second map
	 * 
	 * @param x increment
	 * @param y
	 * @param g
	 */
	public void glued(int x, int y, Graphics g) {

		g.drawRect(x * TILE_SIZE / 2, y * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2);

		if (Loader.items[x][y] instanceof Wall) {
			g.drawImage(wallImage, y * TILE_SIZE / 2, x * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2, this);

		} else if (Loader.items[x][y] instanceof Key) {
			g.drawImage(keyImage, y * TILE_SIZE / 2, x * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2, this);

		} else if (Loader.items[x][y] instanceof Door) {
			g.drawImage(doorImage, y * TILE_SIZE / 2, x * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2, this);
		} else if (Loader.items[x][y] instanceof Player) {
			g.drawImage(playerImage, y * TILE_SIZE / 2, x * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2, this);

		} else if (Loader.items[x][y] instanceof BeltSegment) {
			BeltSegment belt = (BeltSegment) Loader.items[x][y];
			if (belt.getID() == 1)
				g.drawImage(beltLeftImage, y * TILE_SIZE / 2, x * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2, this);
			else if (belt.getID() == 2) {
				g.drawImage(beltRightImage, y * TILE_SIZE / 2, x * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2, this);
			}
		}

		g.drawRect(x * TILE_SIZE / 2 + MAP_OFFSET, y * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2);

		if (Loader.gluedItems[x][y] instanceof Wall) {
			g.drawImage(wallImage, y * TILE_SIZE / 2 + MAP_OFFSET, x * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2,
					this);

		} else if (Loader.gluedItems[x][y] instanceof Key) {
			g.drawImage(keyImage, y * TILE_SIZE / 2 + MAP_OFFSET, x * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2,
					this);

		} else if (Loader.gluedItems[x][y] instanceof Door) {
			g.drawImage(doorImage, y * TILE_SIZE / 2 + MAP_OFFSET, x * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2,
					this);
		} else if (Loader.gluedItems[x][y] instanceof Player) {
			g.drawImage(playerImage, y * TILE_SIZE / 2 + MAP_OFFSET, x * TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE / 2,
					this);

		} else if (Loader.gluedItems[x][y] instanceof BeltSegment) {
			BeltSegment belt = (BeltSegment) Loader.gluedItems[x][y];
			if (belt.getID() == 1)
				g.drawImage(beltLeftImage, y * TILE_SIZE / 2 + MAP_OFFSET, x * TILE_SIZE / 2, TILE_SIZE / 2,
						TILE_SIZE / 2, this);

			else if (belt.getID() == 2) {
				g.drawImage(beltRightImage, y * TILE_SIZE / 2 + MAP_OFFSET, x * TILE_SIZE / 2, TILE_SIZE / 2,
						TILE_SIZE / 2, this);

			}
		}

	}

}
