package Persistence;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import Game_World.BeltSegment;
import Game_World.Door;
import Game_World.Floor;
import Game_World.Game;
import Game_World.Item;
import Game_World.Key;
import Game_World.Location;
import Game_World.Player;
import Game_World.Room;
import Game_World.Wall;

/**
 * Methods for saving the game tate to an XML file,
 * and loading the gamestate from an XML file to an
 * object.
 * 
 * @author Sam Kearon 300425494
 *
 */
public class GameSaveLoader {
	
	/**
	 * Method for saving a game object to a gamestate XML file.
	 * 
	 * @param game
	 * @param filename
	 * @throws IOException 
	 */
	public static void saveGame(Game game, String filename) throws IOException {
		
		if(game == null)
			throw new IOException("Null game");
		if(filename == null)
			throw new IOException("Null filename");
		
		try {
			Document mapDoc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.newDocument();
			
			// Gamestate tag containing other tags
			Element gamestate = mapDoc.createElement("gamestate");
			mapDoc.appendChild(gamestate);
			
			// CHECKING ROOMS FOR ERRORS
			if(game.getRooms() == null)
				throw new IOException("Null room list");
			if(game.getRooms().size() < 1)
				throw new IOException("Empty room list");
			
			// SAVING ROOMS
			Element rooms = mapDoc.createElement("rooms");
			gamestate.appendChild(rooms);
			
			for(Room r : game.getRooms()) {
				Element room = mapDoc.createElement("room");
				rooms.appendChild(room);
				
				// ROOM ID
				Element id = mapDoc.createElement("id");
				room.appendChild(id);
				id.appendChild(mapDoc.createTextNode(""+r.getID()));
				
				if(r.getLocations() == null)
					throw new IOException("Null location list inside room");
				
				// LOCATION IDs
				Element locationIDs = mapDoc.createElement("location_ids");
				room.appendChild(locationIDs);
				
				String locationIDString = "";
				for(int i = 0; i < 4; i++) {
					if(r.getLocations()[i] == null)
						locationIDString += "null";
					else
						locationIDString += r.getLocations()[i].getID();
					if(i < 3)
						locationIDString += ",";
				}
				
				locationIDs.appendChild(mapDoc.createTextNode(locationIDString));
				
			}
			
			// CHECKING LOCATIONS FOR ERRORS
			if(game.getLocations() == null)
				throw new IOException("Null location list");
			if(game.getLocations().size() < 1)
				throw new IOException("Empty location list");
			
			// SAVING LOCATIONS
			Element locations = mapDoc.createElement("locations");
			gamestate.appendChild(locations);
			
			for(Location l : game.getLocations()) {
				
				Element location = mapDoc.createElement("location");
				locations.appendChild(location);
				
				// LOCATION ID
				Element locationID = mapDoc.createElement("location_id");
				location.appendChild(locationID);
				locationID.appendChild(mapDoc.createTextNode(""+l.getID()));
				
				// LEFT ROOM ID
				Element leftID = mapDoc.createElement("left_id");
				location.appendChild(leftID);
				leftID.appendChild(mapDoc.createTextNode(""+l.getLeftID()));
				
				// RIGHT ROOM ID
				Element rightID = mapDoc.createElement("right_id");
				location.appendChild(rightID);
				rightID.appendChild(mapDoc.createTextNode(""+l.getRightID()));
			}
			
			// CHECKING PLAYER FOR ERRORS
			if(game.getPlayer() == null)
				throw new IOException("Null player");
			if(game.getPlayer().getBackpack() == null)
				throw new IOException("Null player backpack");
			if(game.getPlayer().getPoint() == null)
				throw new IOException("Null point");
			
			// SAVING PLAYER
			Element player = mapDoc.createElement("player");
			gamestate.appendChild(player);
			
			// PLAYER LOCATION
			Element player_location = mapDoc.createElement("location");
			player.appendChild(player_location);
			if(game.getPlayer().getLocation() != null)
				player_location.appendChild(mapDoc.createTextNode(""+game.getPlayer().getLocation().getID()));
			else
				player_location.appendChild(mapDoc.createTextNode("null"));
			
			// PLAYER ROOM
			Element player_room = mapDoc.createElement("room");
			player.appendChild(player_room);
			if(game.getPlayer().getRoom() != null)
				player_room.appendChild(mapDoc.createTextNode(""+game.getPlayer().getRoom().getID()));
			else
				player_room.appendChild(mapDoc.createTextNode("null"));
			
			Element backpack = mapDoc.createElement("backpack");
			player.appendChild(backpack);
			
			for(Item i : game.getPlayer().getBackpack()) {
				if(i instanceof Key) {
					Element key = mapDoc.createElement("key");
					backpack.appendChild(key);
					key.appendChild(mapDoc.createTextNode(""+((Key) i).getID()));
				}
				
				else if(i instanceof BeltSegment){
					Element belt_segment = mapDoc.createElement("belt_segment");
					backpack.appendChild(belt_segment);
					belt_segment.appendChild(mapDoc.createTextNode(""+((BeltSegment) i).getID()));
				}
				else
					throw new IOException("Error on loading backpack item - not expected class");
			}
			
			Element point = mapDoc.createElement("point");
			player.appendChild(point);
			point.appendChild(mapDoc.createTextNode(game.getPlayer().getPoint().x + ","
												  + game.getPlayer().getPoint().y));
			
			
			// Write to xml file
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource(mapDoc);
						
			transformer.transform(source, new StreamResult(new File(filename)));
			
			
		} catch(Exception e) {throw new IOException(e.getMessage());}
	}
	
	
	/**
	 * Method for loading a game object from a gamestate XML file.
	 * 
	 * @param filename
	 * @throws IOException 
	 */
	public static Game loadGame(String filename) throws IOException {

		if(filename == null)
			throw new IOException("Null filename");
		
		try {
			
			Document doc = DocumentBuilderFactory.newInstance()
						   .newDocumentBuilder()
						   .parse(new File(filename));
			
			doc.normalize();
			
			Game out = new Game();
			ArrayList<Room> room_list = new ArrayList<Room>();
			HashMap<Room, String> room_to_loc = new HashMap<Room, String>();
			ArrayList<Location> location_list = new ArrayList<Location>();
			Player player = new Player();
			ArrayList<Item> backpack = new ArrayList<Item>();
			
			// Gamestate node containing all other nodes
			Node gamestate = doc.getFirstChild();
			if(!gamestate.getNodeName().equals("gamestate"))
				throw new IOException("Bad format @ gamestate: " + gamestate.getNodeName());
			
			// Populating rooms (incomplete, missing references to locations)
			Node rooms = gamestate.getFirstChild();
			Node room = rooms.getFirstChild();
			
			while(room != null) {
				Room r = new Room(null, Integer.parseInt(getNodeValue(room.getFirstChild())));
				room_list.add(r);
				room_to_loc.put(r, getNodeValue(room.getFirstChild().getNextSibling()));
				
				room = room.getNextSibling();
			}
			
			// Populating locations and adding references to rooms
			Node locations = rooms.getNextSibling();
			Node location = locations.getFirstChild();
			
			while(location != null) {
				
				Room[] location_rooms = new Room[2];
				Integer[] location_room_ids = {Integer.parseInt(getNodeValue(location.getFirstChild().getNextSibling())),
											   Integer.parseInt(getNodeValue(location.getFirstChild().getNextSibling().getNextSibling()))};
				
				for(int i = 0; i < 2; i++) {
					for(Room r : room_list)
						if(r.getID() == location_room_ids[i])
							location_rooms[i] = r;
					
					if(location_rooms[i] == null)
						throw new IOException("Expected room id in location to match existing room id");
				}
				
				location_list.add(new Location(location_rooms[0], location_rooms[1], Integer.parseInt(getNodeValue(location.getFirstChild()))));
				
				location = location.getNextSibling();
				
			}
			
			// Filling room's references to locations.
			
			for(Room r : room_list) {
				
				Location[] room_locations = new Location[4];
				String[] tokens = room_to_loc.get(r).split(",");
				
				for(int i = 0; i < 4; i++) {
					if(tokens[i].equals("null"))
						room_locations[i] = null;
					else {
						int id = Integer.parseInt(tokens[i]);
						for(Location l : location_list)
							if(l.getID() == id)
								room_locations[i] = l;
					}
				}
				
			}
			
			// Filling player information
			Node player_node = locations.getNextSibling();
			
			// Getting location if not null
			Node player_location = player_node.getFirstChild();
			if(!getNodeValue(player_location).equals("null")) {
				int id = Integer.parseInt(getNodeValue(player_location));
				for(Location l : location_list)
					if(l != null)
						if(l.getID() == id)
							player.setLocation(l);
			}
			System.out.println("Room");
			// Getting room if not null
			Node player_room = player_location.getNextSibling();
			if(!getNodeValue(player_room).equals("null")) {
				int id = Integer.parseInt(getNodeValue(player_room));
				System.out.println("0");
				for(Room r : room_list) {
					System.out.println("1");
					if(r != null) {
						System.out.println("2");
						if(r.getID() == id) {
							System.out.println("3");
							player.setRoom(r);
							System.out.println("4");
						}
					}
				}
			}
			System.out.println("out");
			if(player.getLocation() == null && player.getRoom() == null)
				throw new IOException("Unable to match player room/location ids to relative room/location");
			System.out.println("Backpack");
			// Populating backpack
			Node player_backpack = player_room.getNextSibling();
			Node backpack_item = player_backpack.getFirstChild();
			System.out.println("While");
			while(backpack_item != null) {
				switch(backpack_item.getNodeName()) {
				case "key":
					backpack.add(new Key(Integer.parseInt(getNodeValue(backpack_item))));
					break;
				case "belt_segment":
					backpack.add(new BeltSegment(Integer.parseInt(getNodeValue(backpack_item))));
					break;
				default:
					throw new IOException("Invalid item in backpack");
				}
				
				backpack_item = backpack_item.getNextSibling();
			}
			
			// Parsing point
			Node player_point = player_backpack.getNextSibling();
			player.setPoint(new Point(Integer.parseInt(getNodeValue(player_point).split(",")[0]),
									  Integer.parseInt(getNodeValue(player_point).split(",")[0])));
			
			player.setBackpack(backpack);
			
			out.setRooms(room_list);
			out.setLocations(location_list);
			out.setPlayer(player);
			
			
			return out;
			
		} 
		catch(Exception e) {throw new IOException(e.getMessage());}
		
	}
	
	/**
	 * Support method for loadMap to clean code
	 * 
	 * @param node
	 */
	private static String getNodeValue(Node n) {
		return n.getChildNodes().item(0).getNodeValue();
	}
	
}






