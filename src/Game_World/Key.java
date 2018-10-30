package Game_World;

/**
 * @author morriscame
 * Student id: 300395467
 */
public class Key implements Item{
	int id;
	
	 /**
	  * Sets key id to the same as a door
	  */
	public Key(int i) {
		this.id = i;
	}
	
	/**
	 * returns item id
	 * 
	 * @return id
	 */
	public int getID() {
		return id;
	}
}
