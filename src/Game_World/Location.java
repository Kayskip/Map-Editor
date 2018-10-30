package Game_World;

/**
 * @author morriscame
 * Student id: 300395467
 */
public class Location {
	Room left;
	Room right;
	int id;
	
	/**
	 * Next location instance (static between rooms)
	 * 
	 * @param left
	 * @param right
	 * @param id
	 */
	public Location(Room left, Room right,int id){
		this.left = left;
		this.right = right;
		this.id = id;
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
	 * Left room ID getter
	 * 
	 * @return left rooms id
	 */
	public int getLeftID() {
		return left.id;
	}
	
	/**
	 * Right room ID getter
	 * 
	 * @return right rooms id
	 */
	public int getRightID() {
		return right.id;
	}
}
