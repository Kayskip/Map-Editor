package Application;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import Application.Listeners.ClickListener;
import Application.Listeners.CloseListener;
import Application.Listeners.TypeListener;
import Application.Components.GamePanel;
import Application.Components.InventoryPanel;
import Game_World.Game;
import Game_World.Item;
import Game_World.Room;
import Persistence.MapSaveLoader;
import Renderer.RenderOutdoor;
import Renderer.Render;

/**
 * The application window for the adventure game. The application provides menus, buttons and
 * right-click windows allowing the user to perform actions in the game.
 * 
 * @author Geordie Rogers | rogersgeor1 | 300416218
 *
 */
public class AdventureGame {

  private JFrame adventureFrame;
  private JMenuBar menuBar;
  private JMenu menuFile, menuGame;
  private JMenuItem quit, newGame, save, load;
  private JPanel inventoryPanel;
  protected JPanel gameGUI;
  private JPanel controlPanel;
  private JLabel gamePad;
  private JButton mapEd;

  private Game game;
  private EventHandler evHandler;

  /**
   * Constructs a new adventure game window.
   */
  public AdventureGame() {
	this.game = new Game();  
	  
    setupFrame();
    setupMenu();
    setupGamePanels();
    setupGUI();
    setupListners();
    
    this.evHandler = new EventHandler(game, this);


    // Pack the frame and set it as visible so the user can interact with it
    adventureFrame.pack();
    adventureFrame.setMaximumSize(new Dimension(800, 500));
    adventureFrame.setPreferredSize(new Dimension(800, 500));
    adventureFrame.setMinimumSize(new Dimension(800, 500));
    adventureFrame.setVisible(true);
  }

  /**
   * Sets up the frame for the adventure game. The frame is where all of the swing components should
   * be placed.
   * 
   * This method should always be called first.
   */
  private void setupFrame() {
    adventureFrame = new JFrame("Adventure Game");
    adventureFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    // Creates a listener that will confirm exit when the user tries to quit
    adventureFrame.addWindowListener(new CloseListener() {

      @Override
      public void windowClosing(WindowEvent e) {
        if (promptUser()) {
          System.exit(0);
        }
      }
    });
  }

  /**
   * This method sets up all of the menus for the adventure game. The menus allow the user to quit,
   * save, load and create a new game.
   * 
   * Requires setupFrame() to be called first.
   */
  private void setupMenu() {
    this.menuBar = new JMenuBar();

    // Create the File Menu
    this.menuFile = new JMenu("File");

    // Create the Quit Menu Item
    this.quit = new JMenuItem("Quit");
    this.quit.addActionListener(e -> adventureFrame
        .dispatchEvent(new WindowEvent(adventureFrame, WindowEvent.WINDOW_CLOSING)));
    this.menuFile.add(quit);
    menuBar.add(menuFile);

    // Create the Game Menu
    this.menuGame = new JMenu("Game");
    menuBar.add(menuGame);

    // Create the New Game Menu Item
    this.newGame = new JMenuItem("New Game");
    this.newGame.addActionListener(e -> {
      if (promptUser()) {
        this.game = new Game();
        this.evHandler = new EventHandler(game,this);
//        this.adventureFrame.remove(gameGUI);
        this.gameGUI.setVisible(false);
        setupGUI();
        this.gameGUI.repaint();
        this.controlPanel.repaint();
      }

    });
    this.menuGame.add(newGame);

    // Create the Save Game Menu Item
    this.save = new JMenuItem("Save Game");
    this.save.addActionListener(e -> saveGame());
    this.menuGame.add(save);

    // Create the Load Game Menu Item
    this.load = new JMenuItem("Load Game");
    this.load.addActionListener(e -> loadGame());
    this.menuGame.add(load);

    // Create the Map Editor Menu Item
    this.mapEd = new JButton("Map Editor");
    this.mapEd.addActionListener(e -> {
      if (promptUser()) {
        try {
          new Map_Editor.Window();
        } catch (Exception e1) {
          e1.printStackTrace();
        }
        adventureFrame.setVisible(false);
        adventureFrame.dispose();
      }

    });
    this.menuBar.add(mapEd);

    // Set the Frame's menu to the one we just created
    adventureFrame.setJMenuBar(menuBar);
  }

  /**
   * Sets up the game panels. This is where inventory and movement keys will be stored.
   */
  private void setupGamePanels() {

    // Set up inventoryPanel
    try {
      this.inventoryPanel = new InventoryPanel("src/Application/img/controll.png");
    } catch (FileNotFoundException e) {
      fileNotFoundDialouge("src/Application/img/controll.png");
    }
    this.inventoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    this.inventoryPanel.setPreferredSize(new Dimension(150, 500));

    // Set Up Control Panel
    try {
      this.controlPanel = new GamePanel("src/Application/img/controll.png");
    } catch (FileNotFoundException e) {
      fileNotFoundDialouge("src/Application/img/controll.png");
    }
    this.controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    this.controlPanel.setPreferredSize(new Dimension(150, 500));

    // Set up the game pad
    ImageIcon padImage = new ImageIcon("src/Application/img/gamepad.png");
    this.gamePad = new JLabel(padImage);

    // Fix the pad size
    Dimension padSize = new Dimension(150, 150);
    this.gamePad.setMinimumSize(padSize);
    this.gamePad.setMaximumSize(padSize);
    this.gamePad.setPreferredSize(padSize);

    // Add the control panel elements
    this.controlPanel.add(gamePad);

    // Add to the window
    adventureFrame.add(inventoryPanel, BorderLayout.LINE_START);
    adventureFrame.add(controlPanel, BorderLayout.LINE_END);
  }

  /**
   * Sets up the panel for the game GUI.
   */
  private void setupGUI() {
    this.gameGUI = getGUI();
    this.gameGUI.repaint();
    this.gameGUI.setVisible(true);

    // Add to Window
    adventureFrame.add(gameGUI, BorderLayout.CENTER);
  }
  
  private JPanel getGUI() {
	  if(game.getRoom()!=null) {
		  return new Render(game.getRoom().getItems());
	  } else if(game.getLocation()!=null){ 
		  return new RenderOutdoor(1);
	  } else return null; //Should only reach if there is a bug.
  }

  /**
   * Set up Key and Mouse Listners for the GUI elements
   */
  private void setupListners() {
    // Listens for key events to move the player in game
    mapEd.addKeyListener(new TypeListener() {

      @Override
      public void keyTyped(KeyEvent e) {
        evHandler.keyPressed(e);

      }
    });

    // Listens for mouse events on the gamePad
    this.gamePad.addMouseListener(new ClickListener() {

      @Override
      public void mouseClicked(MouseEvent e) {
        evHandler.clickedGamePad(e);

      }

    });
  }


  /**
   * Choose a file to read or write to.
   * 
   * @param load True if you wish to read, False for write
   * @return The file opened
   */
  public static File chooseFile(boolean load) {
    JFileChooser chooser = new JFileChooser();

    // Create the file filter
    FileNameExtensionFilter filter = new FileNameExtensionFilter("XML FIles Only", "xml", "XML");
    chooser.setFileFilter(filter);

    // Set the current directory
    File workDir = new File(System.getProperty("user.dir"));
    chooser.setCurrentDirectory(workDir);

    if (load) { // Load the file and return it
      int failValue = chooser.showOpenDialog(null);
      if (failValue == JFileChooser.APPROVE_OPTION) {
        return chooser.getSelectedFile();
      }
      return null;
    } else { // Allow the user to create or select a file
      int failValue = chooser.showSaveDialog(null);
      if (failValue == JFileChooser.APPROVE_OPTION) {
        return chooser.getSelectedFile();
      }
      return null;
    }
  }

  /**
   * Loads the current game state from a file.
   */
  private void loadGame() {
    if (!promptUser())
      return;
    // TODO: This needs to call the persistance package to create a new game
    File gameSave = chooseFile(true);
    System.out.println(gameSave.getName());
    try {
		Game gameState = Persistence.GameSaveLoader.loadGame(gameSave.getName());
		this.game = gameState;
		int count = 0;
		for(Room room: game.getRooms()) {
			Item[][] items = MapSaveLoader.loadMap("saveFile"+count);
			room.setItems(items);
			count++;
		}
    } catch (IOException e) {
		System.err.println(e.getMessage());
		e.printStackTrace();
	}
    // Check for errors
    if (gameSave == null) {
      System.out.println("Failed to load save game");
      return;
    }
    System.out.println("You chose to open this file: " + gameSave.getName());
  }

  /**
   * Save the current game state to a file.
   */
  private void saveGame() {
    // TODO: This needs to call the persistance package and pass it the game to save
    File gameSave = chooseFile(false);
    try {
		Persistence.GameSaveLoader.saveGame(game, gameSave.toString());
		int count = 0;
		for(Room room: game.getRooms()) {
			MapSaveLoader.saveMap(room.getItems(),"saveFile"+count);
			count++;
		}
    } catch (IOException e) {
		e.printStackTrace();
	}
    // Check for errors
    if (gameSave == null) {
      System.out.println("Failed to save game. Couldn't write to the selected file.");
      return;
    }
    System.out.println("You chose to open this file: " + gameSave.getName());
  }

  /**
   * Prompt the user before they make an action that could loose progress in the game.
   */
  public static boolean promptUser() {
    return JOptionPane.showConfirmDialog(null, "Are you sure? You will loose any unsaved changes.",
        "Confirm", JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
  }

  /**
   * Create a popup that asks the user if a file is missing or has been moved. This is used when an
   * image can't be found.
   * 
   * @param path The path to the file that couldn't be found.
   */
  public static void fileNotFoundDialouge(String path) {
    JOptionPane.showMessageDialog(null, "Couldn't load " + path + "\nHas it been moved?");
  }


  /**
   * Closes the game without asking the user to save. This should only be used for testing.
   * 
   * @param prompt If the user should be asked before quit
   */
  public void close(Boolean prompt) {
    if (prompt) {
      adventureFrame.dispatchEvent(new WindowEvent(adventureFrame, WindowEvent.WINDOW_CLOSING));
    } else {
      adventureFrame.setVisible(false);
      adventureFrame.dispose();
    }
  }

  /**
   * Test if all of the elements were created correctly
   * 
   * @return
   */
  public boolean creationValid() {
    // If any of the UI elements have not been created return false
    if (adventureFrame == null || menuBar == null || menuFile == null || menuGame == null
        || quit == null || newGame == null || save == null || load == null || inventoryPanel == null
        || gameGUI == null || controlPanel == null || gamePad == null || mapEd == null)
      return false;
    // If any of the game logic variables haven't been initialised return false
    if (game == null || evHandler == null)
      return false;
    return true;
  }

  /**
   * Creates a new AdventureGame window and sets it up.
   * 
   * @param args This variable is not used
   */
  public static void main(String[] args) {
    new AdventureGame();
  }
}

