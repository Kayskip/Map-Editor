package Renderer;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Game_World.BeltSegment;
import Game_World.Door;
import Game_World.Floor;
import Game_World.Item;
import Game_World.Key;
import Game_World.Player;
import Game_World.Wall;

/**
 * Class creates a JPanel via the Render or RenderOutdoor classes and displays
 * it on a window for testing purposes.
 *
 * @author Drew Forrest 300423875
 *
 */
public class Panel {

	JFrame mapFrame;
	public JPanel panel;

	/**
	 * Constructor sets up application window then creates a JPanel via Render or
	 * RenderOutdoor and adds it to the frame.
	 */
	public Panel() {
		setupFrame();
		setupPanel();
		mapFrame.add(panel);
		mapFrame.pack();
		mapFrame.setVisible(true);
	}

	/**
	 * setupFrame() creates the application window that the JPanel is displayed on.
	 */
	private void setupFrame() {
		mapFrame = new JFrame("Test");
		mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapFrame.setMinimumSize(new Dimension(500, 500));
	}

	/**
	 * setupPanel() can create either a Render JPanel or an RenderOutdoor JPanel.
	 * For a Render JPanel it populates a 2D array with objects and passes it into
	 * the Render constructor. For a RenderOutdoor JPanel it passes in an area code
	 * parameter (between 1 and 5) into the RenderOutdoor constructor.
	 */
	public void setupPanel() {

		boolean indoor = false;

			if(indoor) {
				Item[][] tmp = new Item[20][20];
				for (int x = 0; x < 20; x++) {
					for (int y = 0; y < 20; y++) {
						tmp[x][y] = new Floor();
					}
				}
				// Populates 2D array with objects.
				for (int i = 0; i < 10; i++) {
					tmp[i][5] = new Wall();
				}
				for (int i = 0; i < 5; i++) {
					tmp[9][i] = new Wall();
				}
				tmp[5][5] = new Door(0, false);
				tmp[9][2] = new Door(0, true);
				tmp[4][1] = new Player();
				Player p1 = new Player();
				p1.direction = "N";
				tmp[5][2] = p1;
				Player p2 = new Player();
				p2.direction = "E";
				tmp[4][3] = p2;
				Player p3 = new Player();
				p3.direction = "W";
				tmp[3][2] = p3;
				tmp[7][2] = new Key('c');
				tmp[10][10] = new BeltSegment(1);
				tmp[12][10] = new BeltSegment(2);

				this.panel = new Render(tmp);
			}
			else {
				this.panel = new RenderOutdoor(1);
			}

		/*if (func.equals("RenderValid")) {
			Item[][] tmp = new Item[20][20];
			for (int x = 0; x < 20; x++) {
				for (int y = 0; y < 20; y++) {
					tmp[x][y] = new Floor();
				}
			}
			// Populates 2D array with objects.
			for (int i = 0; i < 10; i++) {
				tmp[i][5] = new Wall();
			}
			for (int i = 0; i < 5; i++) {
				tmp[9][i] = new Wall();
			}
			tmp[5][5] = new Door(0, false);
			tmp[9][2] = new Door(0, true);
			tmp[4][1] = new Player();
			Player p1 = new Player();
			p1.direction = "N";
			tmp[5][2] = p1;
			Player p2 = new Player();
			p2.direction = "E";
			tmp[4][3] = p2;
			Player p3 = new Player();
			p3.direction = "W";
			tmp[3][2] = p3;
			tmp[7][2] = new Key('c');
			tmp[10][10] = new BeltSegment(1);
			tmp[12][10] = new BeltSegment(2);

			this.panel = new Render(tmp);

		} else if (func.equals("RenderInvalid")) {
			Item[][] tmp = new Item[20][20];
			for (int x = 0; x < 20; x++) {
				for (int y = 0; y < 20; y++) {
					tmp[x][y] = null;
				}
			}
			this.panel = new Render(tmp);
		} else if (func.equals("RenderNull")) {
			this.panel = new Render(null);
		} else if (func.equals("RenderOutdoor1")) {
			this.panel = new RenderOutdoor(1);
		} else if (func.equals("RenderOutdoor2")) {
			this.panel = new RenderOutdoor(2);
		} else if (func.equals("RenderOutdoor3")) {
			this.panel = new RenderOutdoor(3);
		} else if (func.equals("RenderOutdoor4")) {
			this.panel = new RenderOutdoor(4);
		} else if (func.equals("RenderOutdoor5")) {
			this.panel = new RenderOutdoor(5);
		}*/

		this.panel.setVisible(true);
		this.panel.repaint();
	}

	/**
	 * Calls a new instance of Panel().
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// new Panel();
	}
}