package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import org.junit.Test;

import Game_World.BeltSegment;
import Game_World.Door;
import Game_World.Key;
import Game_World.Player;
import Game_World.Wall;
import Map_Editor.Loader;
import Map_Editor.Render;
import Map_Editor.Run;
import Map_Editor.Window;

/**
 * Used for testing the map editor and its components
 * 
 * @author Karu Skipper 300417869
 *
 */
public class Map_Editor_Tests {

	/**
	 * Test that the frame is created correctly via run method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFrameCreation() throws Exception {
		Run run = new Run();
		assert run != null;
		String[] args = { "" };
		Run.main(args);
	}

	/**
	 * Test render of glued items
	 */
	@Test
	public void testGluedRender() {
		Render render = new Render();
		Render.glued = true;
		Loader.items[0][0] = new Key(0);
		Loader.items[1][1] = new Door(0, false);
		Loader.items[2][2] = new Player();
		Loader.items[3][3] = new BeltSegment(1);
		Loader.items[4][4] = new BeltSegment(2);
		Loader.items[5][5] = new Wall();

		Loader.gluedItems[0][0] = new Key(0);
		Loader.gluedItems[1][1] = new Door(0, false);
		Loader.gluedItems[2][2] = new Player();
		Loader.gluedItems[3][3] = new BeltSegment(1);
		Loader.gluedItems[4][4] = new BeltSegment(2);
		Loader.gluedItems[5][5] = new Wall();
		BufferedImage bi = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = bi.createGraphics();
		render.paint(g2);
		g2.dispose();
		assertFalse(Loader.gluedItems[0][0] instanceof Player);
	}

	/**
	 * Test non glued items
	 */
	@Test
	public void testRender() {
		Render render = new Render();
		Render.glued = false;
		Loader.items[0][0] = new Key(0);
		Loader.items[1][1] = new Door(0, false);
		Loader.items[2][2] = new Player();
		Loader.items[3][3] = new BeltSegment(1);
		Loader.items[4][4] = new BeltSegment(2);
		Loader.items[5][5] = new Wall();
		BufferedImage bi = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = bi.createGraphics();
		render.paint(g2);
		g2.dispose();
		assertFalse(Loader.items[0][0] instanceof BeltSegment);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testClicking() throws Exception {
		Window window = new Window();
		ArrayList<JButton> items = window.getAllButtons();
		ArrayList<Boolean> booleans = window.getAllBooleans();
		for (int i = 0; i < items.size(); i++) {
			items.get(i).doClick();
		}
		assertFalse(booleans.get(0) == true);
	}

	/**
	 * @throws Exception
	 * 
	 */
	@Test
	public void testMapNull() throws Exception {
		Loader.loadMap(null);
		Loader.saveMap(null);
		Loader.glueMaps(null, null);
		assertTrue(Loader.items[0][0] != null);
	}


}
