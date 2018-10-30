package Renderer;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Class acts as a JPanel which gets displayed on the appropriate JFrame.
 *
 * @author Drew Forrest 300423875
 *
 */
public class RenderOutdoor extends JPanel {

	private static final long serialVersionUID = 8434540262280271898L;
	private static int TILE_SIZE = 25;

	public int areaNum;

	// Desert Area Images
	Image sandImage = new ImageIcon("src/Renderer/img/sand.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);
	Image sandstoneImage = new ImageIcon("src/Renderer/img/sandstone.png").getImage().getScaledInstance(TILE_SIZE,
			TILE_SIZE, Image.SCALE_DEFAULT);
	Image sandTopImage = new ImageIcon("src/Renderer/img/sandTop.png").getImage().getScaledInstance(TILE_SIZE,
			TILE_SIZE, Image.SCALE_DEFAULT);

	// Forest Area Images
	Image dirtImage = new ImageIcon("src/Renderer/img/dirt.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);
	Image grassImage = new ImageIcon("src/Renderer/img/grass.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);

	// Volcano Area Images
	Image brick = new ImageIcon("src/Renderer/img/brick.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);

	// Player Images
	Image playerImage = new ImageIcon("src/Renderer/img/player.png").getImage().getScaledInstance(TILE_SIZE * 2,
			TILE_SIZE * 2, Image.SCALE_DEFAULT);
	Image playerBeltImage = new ImageIcon("src/Renderer/img/playerBelt.png").getImage().getScaledInstance(TILE_SIZE * 2,
			TILE_SIZE * 2, Image.SCALE_DEFAULT);

	// Building Block Images
	Image greenBlock = new ImageIcon("src/Renderer/img/green.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);
	Image yellowBlock = new ImageIcon("src/Renderer/img/yellow.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);
	Image orangeBlock = new ImageIcon("src/Renderer/img/orange.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);
	Image redBlock = new ImageIcon("src/Renderer/img/red.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);
	Image purpleBlock = new ImageIcon("src/Renderer/img/purple.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);
	Image blackBlock = new ImageIcon("src/Renderer/img/black.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);
	Image layer = new ImageIcon("src/Renderer/img/layer.png").getImage().getScaledInstance(TILE_SIZE, TILE_SIZE,
			Image.SCALE_DEFAULT);

	// Background Images
	Image background1 = new ImageIcon("src/Renderer/img/background1.png").getImage().getScaledInstance(TILE_SIZE * 20,
			TILE_SIZE * 20, Image.SCALE_DEFAULT);
	Image background2 = new ImageIcon("src/Renderer/img/background2.png").getImage().getScaledInstance(TILE_SIZE * 20,
			TILE_SIZE * 20, Image.SCALE_DEFAULT);
	Image background3 = new ImageIcon("src/Renderer/img/background3.png").getImage().getScaledInstance(TILE_SIZE * 20,
			TILE_SIZE * 20, Image.SCALE_DEFAULT);
	Image background4 = new ImageIcon("src/Renderer/img/background4.png").getImage().getScaledInstance(TILE_SIZE * 20,
			TILE_SIZE * 20, Image.SCALE_DEFAULT);
	Image background5 = new ImageIcon("src/Renderer/img/background5.png").getImage().getScaledInstance(TILE_SIZE * 20,
			TILE_SIZE * 20, Image.SCALE_DEFAULT);

	/**
	 * RenderOutdoor constructor sets areaNum field to input area parameter, sets
	 * JPanel size to 500*500 and makes it visible.
	 *
	 * @param area
	 *            - an integer between 1 and 5 which determines the rendered area
	 *            (defaults to 1)
	 */
	public RenderOutdoor(int area) {
		if (area < 1 || area > 5) {
			area = 1;
		}
		this.areaNum = area;
		setSize(500, 500);
		setVisible(true);
	}

	/**
	 * Paint method uses a graphics object to display different static images on the
	 * screen depending on which area the player is in.
	 */
	@Override
	public void paint(Graphics g) {

		// Renders background image.
		if (areaNum == 1) {
			g.drawImage(background1, 0, 0, TILE_SIZE * 20, TILE_SIZE * 20, this);
		} else if (areaNum == 2) {
			g.drawImage(background2, 0, 0, TILE_SIZE * 20, TILE_SIZE * 20, this);
		} else if (areaNum == 3) {
			g.drawImage(background3, 0, 0, TILE_SIZE * 20, TILE_SIZE * 20, this);
		} else if (areaNum == 4) {
			g.drawImage(background4, 0, 0, TILE_SIZE * 20, TILE_SIZE * 20, this);
		} else {
			g.drawImage(background5, 0, 0, TILE_SIZE * 20, TILE_SIZE * 20, this);
		}

		// Renders player image without belt if it's not been acquired and with belt if
		// it has.
		if (areaNum == 1 || areaNum == 2 || areaNum == 3) {
			g.drawImage(playerImage, 9 * TILE_SIZE, TILE_SIZE * 14, TILE_SIZE * 2, TILE_SIZE * 2, this);
		} else {
			g.drawImage(playerBeltImage, 9 * TILE_SIZE, TILE_SIZE * 14, TILE_SIZE * 2, TILE_SIZE * 2, this);
		}

		// Renders foreground blocks and entry/exit buildings.
		if (areaNum == 1) {
			for (int x = 0; x < 20; x++) {
				for (int y = 17; y < 20; y++) {
					g.drawImage(dirtImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
			for (int x = 0; x < 20; x++) {
				g.drawImage(grassImage, x * TILE_SIZE, TILE_SIZE * 16, TILE_SIZE, TILE_SIZE, this);
			}
			for (int x = 0; x < 3; x++) {
				for (int y = 13; y < 16; y++) {
					g.drawImage(greenBlock, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
			for (int x = 17; x < 20; x++) {
				for (int y = 13; y < 16; y++) {
					g.drawImage(yellowBlock, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
		} else if (areaNum == 2) {
			for (int x = 0; x < 20; x++) {
				for (int y = 17; y < 20; y++) {
					g.drawImage(dirtImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
			for (int x = 0; x < 20; x++) {
				g.drawImage(grassImage, x * TILE_SIZE, TILE_SIZE * 16, TILE_SIZE, TILE_SIZE, this);
			}
			for (int x = 0; x < 3; x++) {
				for (int y = 13; y < 16; y++) {
					g.drawImage(yellowBlock, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
			for (int x = 17; x < 20; x++) {
				for (int y = 13; y < 16; y++) {
					g.drawImage(orangeBlock, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
		} else if (areaNum == 3) {
			for (int x = 0; x < 20; x++) {
				for (int y = 17; y < 20; y++) {
					g.drawImage(sandstoneImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
			for (int x = 0; x < 20; x++) {
				g.drawImage(sandTopImage, x * TILE_SIZE, TILE_SIZE * 16, TILE_SIZE, TILE_SIZE, this);
			}
			for (int x = 0; x < 3; x++) {
				for (int y = 13; y < 16; y++) {
					g.drawImage(yellowBlock, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
			for (int x = 17; x < 20; x++) {
				for (int y = 13; y < 16; y++) {
					g.drawImage(redBlock, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
		} else if (areaNum == 4) {
			for (int x = 0; x < 20; x++) {
				for (int y = 17; y < 20; y++) {
					g.drawImage(sandstoneImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
			for (int x = 0; x < 20; x++) {
				g.drawImage(sandstoneImage, x * TILE_SIZE, TILE_SIZE * 16, TILE_SIZE, TILE_SIZE, this);
			}
			for (int x = 0; x < 3; x++) {
				for (int y = 13; y < 16; y++) {
					g.drawImage(yellowBlock, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
			for (int x = 17; x < 20; x++) {
				for (int y = 13; y < 16; y++) {
					g.drawImage(purpleBlock, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
		} else {
			for (int x = 0; x < 20; x++) {
				for (int y = 17; y < 20; y++) {
					g.drawImage(brick, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
			for (int x = 0; x < 20; x++) {
				g.drawImage(brick, x * TILE_SIZE, TILE_SIZE * 16, TILE_SIZE, TILE_SIZE, this);
			}
			for (int x = 0; x < 3; x++) {
				for (int y = 13; y < 16; y++) {
					g.drawImage(purpleBlock, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
			for (int x = 17; x < 20; x++) {
				for (int y = 13; y < 16; y++) {
					g.drawImage(blackBlock, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
				}
			}
		}

		// Renders overlay on entry/exit buildings.
		for (int x = 0; x < 3; x++) {
			for (int y = 13; y < 16; y++) {
				g.drawImage(layer, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
			}
		}
		for (int x = 17; x < 20; x++) {
			for (int y = 13; y < 16; y++) {
				g.drawImage(layer, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
			}
		}

		// Renders doorways on entry/exit buildings.
		g.drawImage(blackBlock, 1 * TILE_SIZE, 14 * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
		g.drawImage(blackBlock, 1 * TILE_SIZE, 15 * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

		g.drawImage(blackBlock, 18 * TILE_SIZE, 14 * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
		g.drawImage(blackBlock, 18 * TILE_SIZE, 15 * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
	}
}
