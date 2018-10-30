package Game_World;

/**
 * @author morriscame
 * Student id: 300395467
 */
public class BeltSegment implements Item{
	int id;
	
	/**
	 * Constructs a new belt with an id
	 * 
	 * @param the belts id 
	 */
	public BeltSegment(int id) {
		this.id = id;
	}

	/**
	 * ID getter
	 * 
	 * @return returns ID
	 */
	public int getID() {
		return id;
	}
}
