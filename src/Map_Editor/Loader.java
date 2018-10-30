package Map_Editor;

import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import Game_World.Floor;
import Game_World.Item;

/**
 * 
 * @author Karu Skipper 300417869
 *
 */
public class Loader {
	/**
	 * Length of the arrays
	 */
	public static int LENGTH = 20;
	/**
	 * list of item objects that have been created 
	 */
	public static Item[][] items = new Item[LENGTH][LENGTH];
	/**
	 * list of glued item objects that have been created 
	 */
	public static Item[][] gluedItems = new Item[LENGTH][LENGTH];

	/**
	 * They have selected to load
	 *
	 * @param filename
	 * @throws Exception
	 */
	public static void loadMap(String filename) throws Exception {
		if (filename == null)
			return;
		blankMap();
		items = Persistence.MapSaveLoader.loadMap(filename);
	}

	/**
	 * Maps being parsed to be displayed
	 * 
	 * @param map1
	 * @param map2
	 * @throws Exception
	 */
	public static void glueMaps(String map1, String map2) throws Exception {
		if (map1 == null || map2 == null)
			return;
		items = Persistence.MapSaveLoader.loadMap(map1);
		gluedItems = Persistence.MapSaveLoader.loadMap(map2);
	}

	/**
	 * Saves the map as a text document, override a file or save your own Throws
	 * null pointer if nothing is selected Makes sure its saved as a text file, if
	 * its overriding a text file than it doesn't need the extension
	 * 
	 * @param file_1
	 *
	 * @throws IOException
	 */
	public static void saveMap(String file_1) throws IOException {
		if (file_1 == null)
			return;

		Persistence.MapSaveLoader.saveMap(items, file_1);

		if (Render.glued) {
			JFileChooser file_2_saver = new JFileChooser();
			file_2_saver.setAcceptAllFileFilterUsed(false);
			file_2_saver.addChoosableFileFilter(new FileNameExtensionFilter("xml files (*.xml)", "xml"));
			file_2_saver.showDialog(null, "Save");
			file_2_saver.setVisible(true);
			String file_2 = file_2_saver.getSelectedFile().getAbsolutePath();

			if (!file_2.contains(".xml"))
				file_2 = file_2 + ".xml";

			System.out.println("Map 1 file saved to: " + file_2);

			Persistence.MapSaveLoader.saveMap(gluedItems, file_2);
		}
	}

	/**
	 * Clears the map, used when the game is launched and cleared
	 */
	public static void blankMap() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (!Render.glued)
					items[i][j] = new Floor();
				else {
					items[i][j] = new Floor();
					gluedItems[i][j] = new Floor();
				}
			}
		}
	}
}
