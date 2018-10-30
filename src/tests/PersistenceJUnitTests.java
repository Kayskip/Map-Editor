package tests;


import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Game_World.*;
import Persistence.GameSaveLoader;
import Persistence.MapSaveLoader;

/**
 * Tests for both the MapSaveLoader methods and GameSaveLoader methods.
 * 
 * @author Sam
 *
 */
public class PersistenceJUnitTests {
	
	/**
	 * Basic test that saves a map, loads a map and checks that they contain
	 * the same objects afterwards.
	 */
	@Test
	public void mapTestSaveLoad() {
		
		String filename = "src/Persistence/Tests/mapTestSaveLoad.xml";
		
		Item[][] map = createTestMap();
		
		try {
			MapSaveLoader.saveMap(map, filename);
		} catch (IOException e) {
			System.err.println(e);
		}
		
		Item[][] after_map = null;
		
		try {
			after_map = MapSaveLoader.loadMap(filename);
		} catch (Exception e) {
			fail();
		}
		
		for(int i = 0; i < map.length; i++) {
			for(int x = 0; x < map[0].length; x++){
				if(!map[i][x].getClass().equals(after_map[i][x].getClass()))
					fail("Fail on "+i+","+x+" "+map[i][x].getClass()+" "+after_map[i][x].getClass());
			}
		}
		
	}
	
	/**
	 * Testing to make sure the correct exceptions are thrown when errors are presented.
	 */
	@Test
	public void mapSaveErrors() {
		
		String a = "", b = "", c = "", d = "", e = "", f = "";
		
		// A
		try {
			MapSaveLoader.saveMap(null, ".");
		} 
		catch (IOException ex) {a = ex.getMessage();}
		
		assertEquals("Null map", a);

		// B
		try {
			MapSaveLoader.saveMap(createTestMap(), null);
		}
		catch(IOException ex) {b = ex.getMessage();}
		
		assertEquals("Null filename", b);
		
		// C
		try {
			MapSaveLoader.saveMap(createTestMap(), "src/folder_that_doesnt_exist/file.xml");
		}
		catch(IOException ex) {c = ex.getMessage();}
		
		assert(c.startsWith("java.io.FileNotFoundException"));
		
		// D
		try {
			Item[][] map = createTestMap();
			map[10][10] = null;
			MapSaveLoader.saveMap(map, "src/Persistence/Tests/file.xml");
		}
		catch(IOException ex) {d = ex.getMessage();}
		
		assertEquals("Error on loading item array at 10,10", d);
		
		// E
		try {
			Item[][] map = {};
			MapSaveLoader.saveMap(map, "src/Persistence/Tests/file.xml");
		}
		catch(IOException ex) {e = ex.getMessage();}
		
		assertEquals("Invalid map size on cols", e);
		
		// F
		try {
			Item[][] map = {{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}};
			MapSaveLoader.saveMap(map, "src/Persistence/Tests/file.xml");
		}
		catch(IOException ex) {f = ex.getMessage();}
		
		assertEquals("Invalid map size on rows", f);
		
	}
	
	/**
	 * Testing to make sure the correct exceptions are thrown when errors are presented.
	 */
	@Test
	public void mapLoadErrors() {
		
		String a = "";
		
		// A
		try {
			MapSaveLoader.loadMap(null);
		} 
		catch (IOException ex) {a = ex.getMessage();}
		
		assertEquals("Null filename", a);
		
	}
	
	@Test
	public void gameTestSaveLoad(){
		String filename = "src/Persistence/Tests/gamestateTestSaveLoad.xml"; 
		
		Game game = createTestGamestate();
		Game game_after = null;
		
		try {
			GameSaveLoader.saveGame(game, filename);
		} catch (IOException e) {
			fail();
		}
		
		try {
			game_after = GameSaveLoader.loadGame(filename);
		} catch (IOException e) {
			fail();
		}
		
		if(game_after == null)
			fail("Game after shouldn't be null");
		
		// TESTING LOCATIONS ARE EQUAL
		if(game.getLocations().size() != game_after.getLocations().size())
			fail("Different number of locations, " + game.getLocations().size()
					+ " vs " + game_after.getLocations().size());
		
		for(int i = 0; i < game.getLocations().size(); i++) {
			Location a = game.getLocations().get(i);
			Location b = game_after.getLocations().get(i);
			if(a.getID() != b.getID())
				fail("location ids not equal");
			if(a.getLeftID() != b.getLeftID())
				fail("location left ids not equal");
			if(a.getRightID() != b.getRightID())
				fail("location right ids not equal");
		}
		
		// TESTING ROOMS ARE EQUAL
		for(int i = 0; i < game.getRooms().size(); i++) {
			Room a = game.getRooms().get(i);
			Room b = game_after.getRooms().get(i);
			if(a.getID() != b.getID())
				fail("room ids not equal");
		}
		
		//TESTING PLAYERS ARE EQUAL
		Player pa = game.getPlayer();
		Player pb = game_after.getPlayer();
		if(pa.getRoom() == null || pb.getRoom() == null) {
			if(pa.getRoom() != null || pb.getRoom() != null)
				fail("before and after player rooms should both be null");
		}
		else
			if(pa.getRoom().getID() != pb.getRoom().getID())
				fail("before and after player room IDs not matching");
			
		if(pa.getLocation() == null || pb.getLocation() == null) {
			if(pa.getLocation() != null || pb.getLocation() != null)
				fail("before and after player locations should both be null");
		}
		else
			if(pa.getLocation().getID() != pb.getLocation().getID())
				fail("before and after player location IDs not matching");
		
		for(int i = 0; i < pa.getBackpack().size(); i++) {
			if(pa.getBackpack().get(i).getClass() != pa.getBackpack().get(i).getClass())
				fail("Item in backpacks not matching");
		}
		
		if(pa.getPoint().equals(pb.getPoint()))
			fail("player points not matching");
		
	}
	
	@Test
	public void gameSaveErrors() {
		String filename = "src/Persistence/Tests/gamestateTestErrors.xml"; 
		
		String err = "";
		
		// A
		try {
			Game game = createTestGamestate();
			game.getPlayer().getBackpack().add(new Floor());
			GameSaveLoader.saveGame(game, filename);
		} catch (IOException e) {
			err = e.getMessage();
		}
		assertEquals("Error on loading backpack item - not expected class", err);
		
		// B
		try {
			Game game = createTestGamestate();
			game.getPlayer().setLocation(game.getLocations().get(0));
			GameSaveLoader.saveGame(game, filename);
		} catch (IOException e) {}
		
	}
	
	// -------------------------------- HELPER METHODS --------------------------------
	
	/**
	 * Helper method that creates an item array and populates it with
	 * useful items for testing.
	 * @return Item[][]
	 */
	private Item[][] createTestMap(){
		Item[][] map = new Item[20][20];
		
		for(int i = 0; i < map.length; i++) {
			for(int x = 0; x < map[0].length; x++){
				if(i == 0 || i == 19 || x == 0 || x == 19)
					map[i][x] = new Wall();
				else
					map[i][x] = new Floor();
			}
		}
		
		map[1][1] = new Door(0, false);
		map[3][1] = new Key(1);
		map[18][18] = new Player();
		map[2][18] = new BeltSegment(0);
		
		return map;
	}
	
	/**
	 * Helper method that creates a game object and populates it with
	 * rooms, locations and a player.
	 * @return
	 */
	private static Game createTestGamestate() {
		Game game = new Game();
		
		ArrayList<Room> rooms = new ArrayList<Room>();
		rooms.add(new Room(null, 0));
		rooms.add(new Room(null, 1));
		rooms.add(new Room(null, 2));
		rooms.add(new Room(null, 3));
		rooms.add(new Room(null, 4));
		rooms.add(new Room(null, 5));
		
		ArrayList<Location> locations = new ArrayList<Location>();
		locations.add(new Location(rooms.get(0), rooms.get(1), 0));
		locations.add(new Location(rooms.get(2), rooms.get(1), 1));
		locations.add(new Location(rooms.get(1), rooms.get(3), 2));
		locations.add(new Location(rooms.get(1), rooms.get(4), 3));
		locations.add(new Location(rooms.get(4), rooms.get(5), 4));
		
		Player player = new Player();
		player.setRoom(rooms.get(0));
		player.getBackpack().add(new Key(0));
		player.getBackpack().add(new Key(1));
		player.getBackpack().add(new BeltSegment(0));
		player.setPoint(new Point(1, 3));
		
		game.setLocations(locations);
		game.setRooms(rooms);
		game.setPlayer(player);
		
		
		return game;
	}
	
}
