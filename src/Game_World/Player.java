package Game_World;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author morriscame
 * Student id: 300395467
 */
public class Player implements Item{
	Location location;
	Room room;
	List<Item> backpack;
	Point point;
	public String direction;
	
	/**
	 * Creates a new player
	 */
	public Player() {
		this.room = null;
		this.location = null;
		direction = "S";
		backpack = new ArrayList<Item>();
		point = new Point();
	}

	/**
	 * Sets room and adds player is player isn't in a room
	 * 
	 * @param room
	 */
	public void setRoom(Room room) {
		if(room == null) {
			this.room = room;
			room.addPlayer(this);
		}
		location = null;
		this.room = room;
		
	}
	
	/**
	 * Checks if player has key for a given door
	 * 
	 * @param door
	 * @return if player has key
	 */
	public boolean hasKey(Door door) {
		for(Item item: this.backpack) {
			if(item instanceof Key) {
				Key key = (Key)item;
				if(key.getID()==door.getID()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Location setter
	 * 
	 * @param loc
	 */
	public void setLocation(Location loc) {
		this.location = loc;
		room.getItems()[room.findPlayerX()][room.findPlayerY()] = new Floor();
		room = null;
	}
	
	/**
	 * Point setter
	 * 
	 * @param point
	 */
	public void setPoint(Point point) {
		this.point = point;
	}
	
	/**
	 * Room getter
	 * 
	 * @param room
	 */
	public Room getRoom() {
		return this.room;
	}
	
	/**
	 * Location getter
	 * 
	 * @param Location
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * Moves player up 
	 */
	public void moveUp() {
		int x = room.findPlayerX(); 
		int y = room.findPlayerY();
		this.room.getItems()[x][y] = new Floor();
		this.room.getItems()[x][y-1] = this;
		point.y = point.y - 1;
	}
	
	/**
	 * Moves player left
	 */
	public void moveLeft() {
		int x = room.findPlayerX();
		int y = room.findPlayerY();
		this.room.getItems()[x][y] = new Floor();
		this.room.getItems()[x-1][y] = this;
		point.x = point.x - 1;
	}
	
	/**
	 * Moves player down
	 */
	public void moveDown() {
		int x = room.findPlayerX();
		int y = room.findPlayerY();
		this.room.getItems()[x][y] = new Floor();
		this.room.getItems()[x][y+1] = this;
		point.y = point.y + 1;
	}
	
	/**
	 * Moves player right
	 */
	public void moveRight() {
		int x = room.findPlayerX();
		int y = room.findPlayerY();
		this.room.getItems()[x][y] = new Floor();
		this.room.getItems()[x+1][y] = this;
		point.x = point.x + 1;
	}

	/**
	 * backpack getter
	 * 
	 * @return items in backpack
	 */
	public List<Item> getBackpack() {
		return backpack;
	}

	/**
	 * Point getter
	 * 
	 * @return Point
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * Backpack setter
	 * 
	 * @param backpack2
	 */
	public void setBackpack(ArrayList<Item> backpack2) {
		this.backpack = backpack2;
	}
}
