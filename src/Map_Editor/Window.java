package Map_Editor;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Game_World.BeltSegment;
import Game_World.Door;
import Game_World.Floor;
import Game_World.Key;
import Game_World.Player;
import Game_World.Wall;
import Map_Editor.Window;

/**
 * The application window for the map editor. The application window provides
 * menus, buttons and click-able tiles to edit their adventure game.
 * 
 *
 * @author Karu Skipper 300417869
 *
 */
public class Window extends JFrame {

	private static final long serialVersionUID = -4389822650631792766L;
	private static JFrame mapFrame;
	private JMenuBar menuBar;
	private JMenu menuFile, menuGame, glueGame;
	private JPanel mapGUI, controlPanel;
	private JSplitPane split;
	private JMenuItem quit, save, clear, load, glue, single;

	private boolean lockedDoorSelected = false;
	private boolean keySelected = false;
	private boolean doorSelected = false;
	private boolean wallSelected = false;
	private boolean clearSelected = false;
	private boolean playerSelected = false;
	private boolean beltLeftSelected = false;
	private boolean beltRightSelected = false;
	private int keyDoorId = 0;

	private JButton key = new JButton(new ImageIcon(Render.keyImage));
	private JButton door = new JButton(new ImageIcon(Render.doorImage));
	private JButton wall = new JButton(new ImageIcon(Render.wallImage));
	private JButton erase = new JButton(new ImageIcon(Render.eraseImage));
	private JButton player = new JButton(new ImageIcon(Render.playerImage));
	private JButton beltLeft = new JButton(new ImageIcon(Render.beltLeftImage));
	private JButton beltRight = new JButton(new ImageIcon(Render.beltRightImage));

	/**
	 * Constructs a new adventure game window. Set the default split to be 80% GUI
	 * 20% control panel Setup the split frame, the gameGUI goes on top and the
	 * controlPanel on the bottom Pack the frame and set it as visible so the user
	 * can interact with it
	 *
	 * @throws Exception
	 */

	public Window() throws Exception {
		setupFrame();
		setupMenu();
		setupControl();
		setupGUI();
		setupSingleMap();
		Loader.blankMap();
		this.split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, mapGUI);

		mapFrame.add(split);
		mapFrame.pack();
		mapFrame.setVisible(true);
		JOptionPane.showMessageDialog(Window.mapFrame,
				"                      Welcome to the Map Editor!\n"
						+ "Click an item and start placing them on the map to begin\n"
						+ "                  Or load an already existing map");
	}

	/**
	 * Sets up the frame for the adventure game. The frame is where all of the swing
	 * components should be placed.
	 *
	 * This method should always be called first. Creates a listener that will
	 * confirm exit when the user tries to quit This contains a lot of abstract
	 * methods that are required to be here
	 */
	private void setupFrame() {
		mapFrame = new JFrame("Map Editor");
		mapFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mapFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure? You will loose any unsaved changes.", "Confirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		mapFrame.setMinimumSize(new Dimension(1160, 875));
	}

	/**
	 * This method sets up all of the menus for the adventure game. The menus allow
	 * the user to quit, save, load and create a new game.
	 *
	 * Requires setupFrame() to be called first.
	 *
	 * @throws Exception
	 */
	private void setupMenu() throws Exception {
		this.menuBar = new JMenuBar();
		this.menuFile = new JMenu("File");
		this.glueGame = new JMenu("Glue");
		this.quit = new JMenuItem("Quit");
		this.menuGame = new JMenu("Game");
		this.save = new JMenuItem("Save Game");
		this.single = new JMenuItem("Single Map");

		setupQuitMap();
		this.menuFile.add(quit);

		setupSaveMap();
		this.menuGame.add(this.save);

		setupLoadMap();
		this.menuGame.add(this.load);

		setupClearMap();
		this.menuGame.add(this.clear);

		setUpGlueMaps();
		this.glueGame.add(this.glue);
		this.glueGame.add(this.single);

		menuBar.add(menuFile);
		menuBar.add(menuGame);
		menuBar.add(glueGame);
		mapFrame.setJMenuBar(menuBar);
	}

	/**
	 * Sets up quit option
	 */
	private void setupQuitMap() {
		this.quit.addActionListener(e -> mapFrame.dispatchEvent(new WindowEvent(mapFrame, WindowEvent.WINDOW_CLOSING)));
	}

	/**
	 * Sets up save option and executes the loader save method when clicked
	 */
	private void setupSaveMap() {
		this.save.addActionListener(e -> {
			try {
				JFileChooser file_1_saver = new JFileChooser();
				file_1_saver.setAcceptAllFileFilterUsed(false);
				file_1_saver.addChoosableFileFilter(new FileNameExtensionFilter("xml files (*.xml)", "xml"));
				file_1_saver.showDialog(null, "Save");
				file_1_saver.setVisible(true);
				String file_1 = file_1_saver.getSelectedFile().getAbsolutePath();

				if (!file_1.contains(".xml"))
					file_1 = file_1 + ".xml";

				System.out.println("File saved to: " + file_1);

				Loader.saveMap(file_1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			mapFrame.repaint();
		});

	}

	private void setupSingleMap() {
		this.single.addActionListener(e -> {
			Render.glued = false;
			mapFrame.repaint();
		});

	}

	/**
	 * Sets up clear option for the screen
	 */
	private void setupClearMap() {
		this.clear = new JMenuItem("Clear Map");
		clear.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null, "Are you sure? You will loose any unsaved changes.", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				Loader.blankMap();
				mapFrame.repaint();
			}
			mapFrame.repaint();
		});
	}

	/**
	 * Sets up load option for map
	 * 
	 * @throws Exception
	 */
	private void setupLoadMap() throws Exception {
		this.load = new JMenuItem("Load Game");
		this.load.addActionListener(e -> {
			Render.glued = false;
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.addChoosableFileFilter(filter);
			jfc.showDialog(null, "Please Select the File");
			jfc.setVisible(true);
			String filename = jfc.getSelectedFile().toString();
			try {
				Loader.loadMap(filename);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			mapFrame.repaint();
		});

	}

	/**
	 * Setup to glue the maps together
	 */
	private void setUpGlueMaps() {
		this.glue = new JMenuItem("Glue Maps");
		this.glue.addActionListener(e -> {
			Render.glued = true;
			JFileChooser file_1 = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
			file_1.setAcceptAllFileFilterUsed(false);
			file_1.addChoosableFileFilter(filter);
			file_1.showDialog(null, "Please select the file");
			file_1.setVisible(true);
			String map1 = file_1.getSelectedFile().toString();

			JFileChooser file_2 = new JFileChooser();
			file_2.setAcceptAllFileFilterUsed(false);
			file_2.addChoosableFileFilter(filter);
			file_2.showDialog(null, "Please select the file");
			file_2.setVisible(true);
			String map2 = file_2.getSelectedFile().toString();
			try {
				Loader.glueMaps(map1, map2);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			mapFrame.repaint();
		});
	}

	/**
	 * Sets up the control panel, this is where the we will click icons to place on
	 * the screen. Adds the resized images to the control panel so we can then click
	 * and place them on the mapFrame
	 */
	private void setupControl() {
		this.controlPanel = new JPanel();
		this.controlPanel.setLayout(new FlowLayout());
		this.controlPanel.setSize(10, 10);

		this.controlPanel.add(key);
		this.controlPanel.add(door);
		this.controlPanel.add(wall);
		this.controlPanel.add(erase);
		this.controlPanel.add(player);
		this.controlPanel.add(beltLeft);
		this.controlPanel.add(beltRight);

		key.setBorder(BorderFactory.createEmptyBorder());
		door.setBorder(BorderFactory.createEmptyBorder());
		wall.setBorder(BorderFactory.createEmptyBorder());
		erase.setBorder(BorderFactory.createEmptyBorder());
		player.setBorder(BorderFactory.createEmptyBorder());
		beltLeft.setBorder(BorderFactory.createEmptyBorder());
		beltRight.setBorder(BorderFactory.createEmptyBorder());

		key.setContentAreaFilled(false);
		door.setContentAreaFilled(false);
		wall.setContentAreaFilled(false);
		erase.setContentAreaFilled(false);
		player.setContentAreaFilled(false);
		beltLeft.setContentAreaFilled(false);
		beltRight.setContentAreaFilled(false);

		key.addActionListener(e -> {
			this.keySelected = true;
			this.doorSelected = false;
			this.wallSelected = false;
			this.clearSelected = false;
			this.playerSelected = false;
			this.beltLeftSelected = false;
			this.beltRightSelected = false;

		});

		door.addActionListener(e -> {
			this.keySelected = false;
			this.doorSelected = true;
			this.wallSelected = false;
			this.clearSelected = false;
			this.playerSelected = false;
			this.beltLeftSelected = false;
			this.beltRightSelected = false;

		});
		wall.addActionListener(e -> {
			this.keySelected = false;
			this.doorSelected = false;
			this.wallSelected = true;
			this.clearSelected = false;
			this.playerSelected = false;
			this.beltLeftSelected = false;
			this.beltRightSelected = false;

		});

		erase.addActionListener(e -> {
			this.keySelected = false;
			this.doorSelected = false;
			this.wallSelected = false;
			this.clearSelected = true;
			this.playerSelected = false;
			this.beltLeftSelected = false;
			this.beltRightSelected = false;

		});
		player.addActionListener(e -> {
			this.keySelected = false;
			this.doorSelected = false;
			this.wallSelected = false;
			this.clearSelected = false;
			this.playerSelected = true;
			this.beltLeftSelected = false;
			this.beltRightSelected = false;

		});
		beltLeft.addActionListener(e -> {
			this.keySelected = false;
			this.doorSelected = false;
			this.wallSelected = false;
			this.clearSelected = false;
			this.playerSelected = false;
			this.beltLeftSelected = true;
			this.beltRightSelected = false;

		});

		beltRight.addActionListener(e -> {
			this.keySelected = false;
			this.doorSelected = false;
			this.wallSelected = false;
			this.clearSelected = false;
			this.playerSelected = false;
			this.beltLeftSelected = false;
			this.beltRightSelected = true;

		});

		this.controlPanel.setVisible(true);
	}

	/**
	 * Sets up the panel for the game GUI. Calls render which handles display of
	 * grid and images Also handles where the player clicks and makes items if the
	 * button is selected. Also handles if the player has placed a key you must
	 * place a door to match it.
	 * 
	 * Gluing enabled when the user has selected the button, first if statement
	 * accounts for white space between the maps If the mouseX value > 21 it means
	 * its on the second map, therefore set the x value to -22 which = 0 in the
	 * index, enabling it to be placed in the items and map array at specified
	 * co-ordinates
	 */
	private void setupGUI() {
		this.mapGUI = new Render();
		this.mapGUI.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				if (Render.glued) {
					if (x > 400 && x < 440)
						return;

					x = x / 20;
					y = y / 20;

					if (x > 21) {
						x = x - 22;
						if (keySelected) {
							Loader.gluedItems[y][x] = new Key(keyDoorId);
							keySelected = false;
							mapFrame.repaint();
							JOptionPane.showMessageDialog(Window.mapFrame,
									"You have place a key, a locked door is now selected to place which unlocks it");
							lockedDoorSelected = true;
						} else if (doorSelected) {
							Loader.gluedItems[y][x] = new Door(-1, false);
						} else if (wallSelected) {
							Loader.gluedItems[y][x] = new Wall();
						} else if (clearSelected) {
							Loader.gluedItems[y][x] = new Floor();
						} else if (lockedDoorSelected) {
							Loader.gluedItems[y][x] = new Door(keyDoorId, false);
							lockedDoorSelected = false;
							doorSelected = false;
							keyDoorId++;
						} else if (playerSelected) {
							Loader.gluedItems[y][x] = new Player();
						} else if (beltLeftSelected) {
							Loader.gluedItems[y][x] = new BeltSegment(1);
						} else if (beltRightSelected) {
							Loader.gluedItems[y][x] = new BeltSegment(2);
						}
					} else {
						if (keySelected) {
							Loader.items[y][x] = new Key(keyDoorId);
							keySelected = false;
							mapFrame.repaint();
							JOptionPane.showMessageDialog(Window.mapFrame,
									"You have place a key, a locked door is now selected to place which unlocks it");
							lockedDoorSelected = true;
						} else if (doorSelected) {
							Loader.items[y][x] = new Door(-1, false);
						} else if (wallSelected) {
							Loader.items[y][x] = new Wall();
						} else if (clearSelected) {
							Loader.items[y][x] = new Floor();
						} else if (lockedDoorSelected) {
							Loader.items[y][x] = new Door(keyDoorId, false);
							lockedDoorSelected = false;
							doorSelected = false;
							keyDoorId++;
						} else if (playerSelected) {
							Loader.items[y][x] = new Player();
						} else if (beltLeftSelected) {
							Loader.items[y][x] = new BeltSegment(1);
						} else if (beltRightSelected) {
							Loader.items[y][x] = new BeltSegment(2);
						}
					}

				} else {
					x = x / 40;
					y = y / 40;
					if (keySelected) {
						Loader.items[y][x] = new Key(keyDoorId);
						keySelected = false;
						mapFrame.repaint();
						JOptionPane.showMessageDialog(Window.mapFrame,
								"You have place a key, a locked door is now selected to place which unlocks it");
						lockedDoorSelected = true;
					} else if (doorSelected) {
						Loader.items[y][x] = new Door(-1, false);
					} else if (wallSelected) {
						Loader.items[y][x] = new Wall();
					} else if (clearSelected) {
						Loader.items[y][x] = new Floor();
					} else if (lockedDoorSelected) {
						Loader.items[y][x] = new Door(keyDoorId, false);
						lockedDoorSelected = false;
						doorSelected = false;
						keyDoorId++;
					} else if (playerSelected) {
						Loader.items[y][x] = new Player();
					} else if (beltLeftSelected) {
						Loader.items[y][x] = new BeltSegment(1);
					} else if (beltRightSelected) {
						Loader.items[y][x] = new BeltSegment(2);
					}
				}
				mapFrame.repaint();
			}
		});
		this.mapGUI.setVisible(true);
	}

	/**
	 * @return list of all the items for JUnit testing
	 */
	public ArrayList<JButton> getAllButtons() {
		ArrayList<JButton> items = new ArrayList<JButton>();
		items.add(key);
		items.add(door);
		items.add(wall);
		items.add(erase);
		items.add(player);
		items.add(beltLeft);
		items.add(beltRight);
		return items;
	}


	/**
	 * @return list of all the booleans for JUnit testing
	 */
	public ArrayList<Boolean> getAllBooleans() {
		ArrayList<Boolean> items = new ArrayList<Boolean>();
		items.add(lockedDoorSelected);
		items.add(keySelected);
		items.add(doorSelected);
		items.add(wallSelected);
		items.add(clearSelected);
		items.add(playerSelected);
		items.add(beltLeftSelected);
		items.add(beltRightSelected);
		return items;
	}

}
