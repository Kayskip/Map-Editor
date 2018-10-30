package Persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Game_World.*;

/**
 * Methods for saving a map to XML file and loading a map
 * from an XML file.
 * 
 * @author Sam Kearon 300425494
 *
 */
public class MapSaveLoader {
	
	/**
	 * Method for saving map to an XML file from character array.
	 * @param filename
	 * @param map
	 * @throws IOException 
	 */
	public static void saveMap(Item[][] map, String filename) throws IOException {
		
		// Basic checks for valid files
		if(filename == null)
			throw new IOException("Null filename");
		if(map == null)
			throw new IOException("Null map");
		if(map.length != 20)
			throw new IOException("Invalid map size on cols");
		if(map[0].length != 20)
			throw new IOException("Invalid map size on rows");
		
		try {
			Document mapDoc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.newDocument();
			
			// Map data tag, contains the char array
			Element map_data = mapDoc.createElement("map_data");
			mapDoc.appendChild(map_data);
			
			for(int i = 0; i < map.length; i++) {
				
				// Row tag, containing a row of items
				Element row = mapDoc.createElement("row");
				map_data.appendChild(row);
				
				for(int j = 0; j < map[0].length; j++) {
					
					// Item tag, contains one item
					Element item = mapDoc.createElement("item");
					row.appendChild(item);
					
					// Checking for different types of item and treating them accordingly
					if(map[i][j] instanceof Key) {
						Element key = mapDoc.createElement("key");
						item.appendChild(key);
						key.appendChild(mapDoc.createTextNode(""+((Key) map[i][j]).getID()));
					}
					else if(map[i][j] instanceof Door) {
						Element door = mapDoc.createElement("door");
						item.appendChild(door);
						door.appendChild(mapDoc.createTextNode(((Door) map[i][j]).getID() + "," + String.valueOf(((Door) map[i][j]).getVert())));
					}
					else if(map[i][j] instanceof Player)
						item.appendChild(mapDoc.createElement("player"));
					else if(map[i][j] instanceof Wall)
						item.appendChild(mapDoc.createElement("wall"));
					else if(map[i][j] instanceof Floor)
						item.appendChild(mapDoc.createElement("floor"));
					else if(map[i][j] instanceof BeltSegment){
						Element belt_segment = mapDoc.createElement("belt_segment");
						item.appendChild(belt_segment);
						belt_segment.appendChild(mapDoc.createTextNode(""+((BeltSegment) map[i][j]).getID()));
					}
					else
						throw new IOException("Error on loading item array at " + i + "," + j);
					
				}
				
			}
			
			// Write to xml file
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource(mapDoc);
			
			
			transformer.transform(source, new StreamResult(new File(filename)));
			
			
		}
		catch(FileNotFoundException e) {throw new IOException("Bad file (likely filename)");}
		catch (ParserConfigurationException e) {throw new IOException(e.getMessage());}
		catch (TransformerConfigurationException e) {throw new IOException(e.getMessage());}
		catch (TransformerFactoryConfigurationError e) {throw new IOException(e.getMessage());}
		catch (TransformerException e) {throw new IOException(e.getMessage());}
		
	}
	
	
	/**
	 * Method for loading map from XML file, returns character array.
	 * @param filename
	 * @throws Exception 
	 */
	public static Item[][] loadMap(String filename) throws IOException {
		
		if(filename == null)
			throw new IOException("Null filename");
		
		try {
			
			Document doc = DocumentBuilderFactory.newInstance()
						   .newDocumentBuilder()
						   .parse(new File(filename));
			
			doc.normalize();
			
			Item[][] out = new Item[20][20];
			
			Node map_data = doc.getFirstChild();
			
			if(!map_data.getNodeName().equals("map_data"))
				throw new IOException("Bad format @ map_data: " + map_data.getNodeName());
			
			
			Node row = map_data.getFirstChild();
			
			int i = 0, j = 0;
			
			// Iterating through row nodes
			while(row != null) {
				
				if(!row.getNodeName().equals("row"))
					throw new IOException("Bad format @ row: " + row.getNodeName());
				
				Node item = row.getFirstChild();
				
				// Iterating through item nodes in a given row
				while(item != null) {
					
					if(!item.getNodeName().equals("item"))
						throw new IOException("Bad format @ item: " + row.getNodeName());
					
					Node item_child = item.getFirstChild();
					
					if(item_child == null)
						throw new IOException("Null item");
					
					// Checking for each different type of item and treating them appropriately
					switch(item_child.getNodeName()) {
					case("key"):
						out[i][j] = new Key(Integer.parseInt(getNodeValue(item_child)));
						break;
					case("door"):
						out[i][j] = new Door(Integer.parseInt    (getNodeValue(item_child).split(",")[0]),
											 Boolean.parseBoolean(getNodeValue(item_child).split(",")[1]));
						break;
					case("wall"):
						out[i][j] = new Wall();
						break;
					case("floor"):
						out[i][j] = new Floor();
						break;
					case("player"):
						out[i][j] = new Player();
						break;
					case("belt_segment"):
						out[i][j] = new BeltSegment(Integer.parseInt(getNodeValue(item_child)));
						break;
					default:
						throw new IOException("Invalid item name: " + out[i][j].getClass());
					}
					
					item = item.getNextSibling();
					j++;
				}
				
				row = row.getNextSibling();
				i++;
				j=0;
			}
			
			return out;
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Fail
		return null;
	}
	
	
	/**
	 * Support method for loadMap to clean code
	 * 
	 * @param node
	 */
	private static String getNodeValue(Node n) {
		return n.getChildNodes().item(0).getNodeValue();
	}
	
	
	
	/**
	 * ------------------------------ BELOW ARE METHODS USED IN TESTING ------------------------------
	 */
	/*
	public static void main(String argv[]) {
		String filename = "C:\\Users\\Sam\\Desktop\\SWEN225\\Group Project\\testfile.xml";
		
		testLoad(filename);
		//testSave(filename);

	}
	
	
	
	public static void testLoad(String filename) {
		Item[][] map = null;
		try {
			map = loadMap(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(map == null)
			System.err.println("Null");
		
		for(int i = 0; i < map.length; i++) {
			for(int x = 0; x < map[0].length; x++) {
				System.out.print(map[i][x].getClass()+" ");
			}
			System.out.println();
		}
		
	}
	
	
	
	public static void testSave(String filename) {
		Item[][] items = new Item[20][20];
		for(int i = 0; i < items.length; i++) {
			for(int x = 0; x < items[0].length; x++) {
				items[i][x] = new Door(i, true);
			}
		}
			
		
		try {
			saveMap(items, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
}
