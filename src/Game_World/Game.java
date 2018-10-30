package Game_World;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Game_World.Player;

import Application.AdventureGame;

/**
 * @author morriscame
 * Student id: 300395467
 */
public class Game {
	List<Location> locations = new ArrayList<Location>();
	List<Room> rooms = new ArrayList<Room>();
	Player player;
	int numRooms;
	
	 /** 
	 * Calls the setup method to set the initial game state
	 */
	public Game() {
		setupGame();
	}
	
	/**
	 * Sets up the game state.
	 */
	private void setupGame() {
		numRooms = 6;
		player = new Player();
		setupRooms();
		setupLocations();
	}
	
	/**
	 * Populates the locations between rooms.
	 */
	private void setupLocations() {
		locations.add(new Location(rooms.get(0),rooms.get(1),1));
		locations.add(new Location(rooms.get(2),rooms.get(1),2));
		locations.add(new Location(rooms.get(3),rooms.get(1),3));
		locations.add(new Location(rooms.get(1),rooms.get(4),4));
		locations.add(new Location(rooms.get(4),rooms.get(5),5));
		rooms.get(0).setupLocations(null, null, locations.get(0), null);
		rooms.get(2).setupLocations(null,null, null, locations.get(1));
		rooms.get(1).setupLocations(locations.get(0),locations.get(1), locations.get(3), locations.get(2));
		rooms.get(3).setupLocations(null,locations.get(2), null, null);
		rooms.get(4).setupLocations(locations.get(3), null, locations.get(4), null);
		rooms.get(5).setupLocations(locations.get(4), null, null, null);
	}
	
	/**
	 * Populates rooms from files created in the map editor.
	 */
	private void setupRooms() {
		for(int i = 0;i<numRooms;i++) {
			Item[][] map = new Item[20][20];
			try {
			map = Persistence.MapSaveLoader.loadMap("src/Images/MAP_"+i+".xml");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Room room = new Room(map,i);
			rooms.add(room);
		}
		player.setRoom(rooms.get(0));
	}
	
	/**
	 * Checks if the player can move up and moves it up accordingly
	 * 
	 * @return returns if the player has moved
	 */
	public boolean up() {
		if(player.getRoom()!=null) {
			Room room = player.getRoom();
			
			if(room.findPlayerY() == 0) {
				if(room.locations[0] != null) {
					player.setLocation(room.locations[0]);
					player.direction = "N";
					return true;
				} else {
					return false;
				}
			}
			else {
				Item item = room.getItems()[room.findPlayerX()][room.findPlayerY()-1];
				if(item instanceof Floor) {
					player.moveUp();
					player.direction = "N";
					return true;
				} else if(item instanceof Key || item instanceof BeltSegment) {
					player.backpack.add(item);
					player.moveUp();
					player.direction = "N";
					return true;
				} else if(item instanceof Door){
					if(player.hasKey((Door)item)) {
						player.moveUp();
						player.direction = "N";
						return true;
					}
				} 
			}
		} 
		return false;
	}
	
	/**
	 * Checks if the player can move down and moves it down accordingly
	 * 
	 * @return returns if the player has moved
	 */
	public boolean down() {
		if(player.getRoom()!=null) {
			Room room = player.getRoom();
			if(room.findPlayerY() == 19) {
				System.out.println("Found it!");
				if(room.locations[2] != null) {
					player.setLocation(room.locations[2]);
					player.direction = "S";
					return true;
				} else {
					return false;
				}
			}
			else {
				Item item = room.getItems()[room.findPlayerX()][room.findPlayerY()+1];
				if(item instanceof Floor) {
					player.moveDown();
					player.direction = "S";
					return true;
				} else if(item instanceof Key || item instanceof BeltSegment) {
					player.backpack.add(item);
					player.moveDown();
					player.direction = "S";
					return true;
				} else if(item instanceof Door){
					if(player.hasKey((Door)item)) {
						player.moveDown();
						player.direction = "S";
						return true;
					}
				} 
			}
		} 
		return false;
	}
	
	/**
	 * Checks if the player can move left and moves it left accordingly
	 * 
	 * @return returns if the player has moved
	 */
	public boolean left() {
		if(player.getRoom()!=null) {
			Room room = player.getRoom();
			
			if(room.findPlayerX() == 0) {
				if(room.locations[3] != null) {
					player.setLocation(room.locations[3]);
					player.direction = "W";
					return true;
				} else {
					return false;
				}
			}
			else {
				Item item = room.getItems()[room.findPlayerX()-1][room.findPlayerY()];
				if(item instanceof Floor) {
					player.moveLeft();
					player.direction = "W";
					return true;
				} else if(item instanceof Key || item instanceof BeltSegment) {
					player.backpack.add(item);
					player.moveLeft();
					player.direction = "W";
					return true;
				} else if(item instanceof Door){
					if(player.hasKey((Door)item)) {
						player.moveLeft();
						player.direction = "W";
						return true;
					}
				} 
			}
		} else { //At a location
			if(player.getLocation()!=null&&player.getLocation().left!=null) {
				player.setRoom(player.getLocation().left);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the player can move right and moves it right accordingly
	 * 
	 * @return returns if the player has moved
	 */
	public boolean right() {
		if(player.getRoom()!=null) {
			Room room = player.getRoom();
			
			if(room.findPlayerX() == 19) {
				if(room.locations[1] != null) {
					player.setLocation(room.locations[1]);
					player.direction = "E";
					return true;
				} else {
					return false;
				}
			} else {
				Item item = room.getItems()[room.findPlayerX()+1][room.findPlayerY()];
				if(item instanceof Floor) {
					player.moveRight();
					player.direction = "E";
					return true;
				} else if(item instanceof Key || item instanceof BeltSegment) {
					player.backpack.add(item);
					player.moveRight();
					player.direction = "E";
					return true;
				} else if(item instanceof Door){
					if(player.hasKey((Door)item)) {
						player.moveRight();
						player.direction = "E";
						return true;
					}
				} 
			}
		} else { //At a location
			if(player.getLocation()!=null&&player.getLocation().right!=null) {
				player.setRoom(player.getLocation().right);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Room getter
	 * 
	 * @return returns current room
	 */
	public Room getRoom() {
		return player.getRoom();
	}
	
	/**
	 * Location getter
	 * 
	 * @return returns current location
	 */
	public Location getLocation() {
		return player.getLocation();
	}

	/**
	 * Locations list getter
	 * 
	 * @return returns list of locations
	 */
	public List<Location> getLocations() {
		return locations;
	}

	/**
	 * Rooms list getter
	 * 
	 * @return returns list of rooms
	 */
	public List<Room> getRooms() {
		return rooms;
	}

	/**
	 * Player getter
	 * 
	 * @return returns player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Loads in the rooms
	 * 
	 * @param the list of rooms to set
	 */
	public void setRooms(ArrayList<Room> room_list) {
		this.rooms = room_list;
		
	}

	/**
	 * Loads in the locations
	 * 
	 * @oaram the list of locations to set
	 */
	public void setLocations(ArrayList<Location> location_list) {
		this.locations = location_list;
	}

	/**
	 * Sets the player
	 * 
	 * @param The player to set
	 */
	public void setPlayer(Player player2) {
		this.player = player2;
	}
	
	public Item[][] getItems(){
		return player.getRoom().getItems();
	}
	
	public List<Item> getInventory(){
		return player.backpack;
	}
}
