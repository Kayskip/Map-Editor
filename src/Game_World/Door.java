package Game_World;

/**
 * @author morriscame
 * Student id: 300395467
 */
public class Door implements Item{
	int id;
	public boolean isVertical;
	
	/**
	 * Constructs a door that links with a key
	 * 
	 * @param id 
	 * @param isVertical
	 */
	public Door(int id,boolean isVertical) {
		this.isVertical = isVertical;
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
	 * vertical Getter
	 * 
	 * @return isVertical
	 */
	public boolean getVert() {
		return isVertical;
	}
}
