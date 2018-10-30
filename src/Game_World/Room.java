package Game_World;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author morriscame
 * Student id: 300395467
 */
public class Room {
	Item[][] items;
	Location[] locations;
	int id;
	
	/**
	 * Fills a 2D Item array for the room
	 * 
	 * @param map
	 * @param id
	 */
	public Room(Item[][] map,int id) {
		items = new Item[20][20];
		locations = new Location[4];
		setupRoom(map);
		this.id = id;
	}
	
	
	/**
	 * For loading a map
	 * 
	 * @param map
	 */
	public void setupRoom(Item[][] map) {
		this.items = map;
	}
	
	/**
	 * Items getter
	 * 
	 * @return items
	 */
	public Item[][] getItems(){
		return items;
	}
	
	/**
	 * Items setter
	 * 
	 * @param items
	 */
	public void setItems(Item[][] items) {
		this.items = items;
	}
	
	/**
	 * Find X Coordiante of players position in the room
	 * 
	 * @return X coordinate
	 */
	public Integer findPlayerX(){
		for(int j = 0;j<items.length;j++) {
			for(int i = 0;i<items.length;i++) {
				if(items[i][j] instanceof Player) {
					return i;
				}
			}
		}
		return null;
	}
	
	/**
	 * Find Y Coordiante of players position in the room
	 * 
	 * @return Y coordinate
	 */
	public Integer findPlayerY(){
		for(int j = 0;j<items.length;j++) {
			for(int i = 0;i<items.length;i++) {
				if(items[i][j] instanceof Player) {
					return j;
				}
			}
		}
		return null;
	}
	
	/**
	 * Fills locations surrounding room
	 * 
	 * @param north
	 * @param east
	 * @param south
	 * @param west
	 */
	public void setupLocations(Location north,Location east,Location south,Location west) {
		locations[0] = north;
		locations[1] = east;
		locations[2] = south;
		locations[3] = west;
	}
	
	/**
	 * Adds a player to a playerless room
	 * 
	 * @param player
	 */
	public void addPlayer(Player p) {
		if(p.direction == "E") {
			for(int i = 0;i<items.length;i++) {
				for(int j = 0;j<items.length;j++) {
					if(items[i][j] instanceof Floor) {
						items[i][j] = p;
						p.setPoint(new Point(i,j));
						return;
					}
				}
			}
		} else if(p.direction == "W") {
			for(int i = 0;i<items.length;i++) {
				for(int j = 0;j<items.length;j++) {
					if(items[items.length-1-i][j] instanceof Floor) {
						items[items.length-1-i][j] = p;
						p.setPoint(new Point(items.length-1-i,j));
						System.out.println("i is " + i + ", j is " + j);
						return;
					}
				}
			}
		} else if(p.direction == "S") {
			for(int i = 0;i<items.length;i++) {
				for(int j = 0;j<items.length;j++) {
					if(items[j][i] instanceof Floor) {
						items[j][i] = p;
						p.setPoint(new Point(j,i));
						return;
					}
				}
			}
		} else if(p.direction == "N") {
			for(int i = 0;i<items.length;i++) {
				for(int j = 0;j<items.length;j++) { 
					if(items[j][items.length-1-i] instanceof Floor) {
						items[j][items.length-1-i] = p;
						p.setPoint(new Point(j,items.length-1-i));
						return;
					}
				}
			}
		}
	}
	
	
	/**
	 * Room to string
	 */
	@Override
	public String toString() {
		String s = "";
		for(int i = 0;i<items.length;i++) {
			for(int j = 0;j<items.length;j++) {
				if(items[i][j] instanceof Floor) {
					s = s + ".";
				} else if(items[i][j] instanceof Wall) {
				s = s + "W";
				} else if(items[i][j] instanceof Player) {
					s = s + "P";
				} else if(items[i][j] instanceof Door) {
					s = s + "D";
				} else {
					s = s + "?";
				}
			}
			s = s + "\n";
		}
		return s;
	}
	
	/**
	 * ID getter
	 * 
	 * @return id
	 */
	public int getID() {
		return id;
	}

	/**
	 * Locations getter
	 * 
	 * @return Locations surrounding the room
	 */
	public Location[] getLocations() {
		return locations;
	}
}
