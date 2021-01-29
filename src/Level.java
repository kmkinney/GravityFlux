/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import java.io.*;
import java.util.*;

/**
 * Level stores the data for each level
 */
public class Level
{
	private int id;
	private double width;
	private LinkedList<Platform> world;
	private LinkedList<GameObject> objects;
	private LinkedList<GameObject> hazards;
	private SpaceMan sm;
	private Flag flag;
	
	/**
	 * Constructs a Level with given data
	 * @param lid the level number
	 * @param w the width of the level
	 * @param p the list of platforms
	 * @param h the list of hazards
	 * @param o the list of game objects
	 * @param s the SpaceMan
	 * @param f the flag
	 */
	public Level(int lid, double w, LinkedList<Platform> p, LinkedList<GameObject> h, LinkedList<GameObject> o, SpaceMan s, Flag f)
	{
		id=lid;
		world=p;
		objects=o;
		hazards=h;
		sm=s;
		width=w*GameObject.SCALE;
		flag=f;
	}
	/**
	 * Returns a LinkedList of the Platforms
	 * @return a LinkedList of the Platforms
	 */
	public LinkedList<Platform> getPlatforms()
	{
		return world;
	}
	/**
	 * Returns a LinkedList of the Objects
	 * @return a LinkedList of the Objects
	 */
	public LinkedList<GameObject> getObjects()
	{
		return objects;
	}
	/**
	 * Returns a LinkedList of the Hazards
	 * @return a LinkedList of the Hazards
	 */
	public LinkedList<GameObject> getHazards()
	{
		return hazards;
	}
	/**
	 * Returns the SpaceMan
	 * @return the SpaceMan
	 */
	public SpaceMan getSpaceMan()
	{
		return sm;
	}
	/**
	 * Returns the level number
	 * @return the level number
	 */
	public int getID(){return id;}
	/**
	 * Returns the Flag
	 * @return the Flag
	 */
	public Flag getFlag(){return flag;}
	/**
	 * Returns the width
	 * @return the width
	 */
	public double getWidth(){return width;}
}