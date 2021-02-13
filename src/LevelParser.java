/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import org.w3c.dom.ls.*;
import javax.xml.xpath.*;
import org.xml.sax.SAXException;

/**
 * Level Parser parses xml data into an arraylist of levels
 */
public class LevelParser
{
	public static final String FILE_PATH = "levels.xml";
	public static final String HELP_PATH = "help.xml";
	public static final String CUSTOM_PATH = "custom.xml";
	
	/**
	 * Loads game levels
	 * @return an ArrayList of Levels
	 */
	public ArrayList<Level> loadLevels() throws SAXException, XPathExpressionException, IOException, ParserConfigurationException
	{
		return loadLevels(FILE_PATH);
	}
	/**
	 * Loads help level
	 * @return an ArrayList of one level, the help level
	 */
	public ArrayList<Level> loadHelp() throws SAXException, XPathExpressionException, IOException, ParserConfigurationException
	{
		return loadLevels(HELP_PATH);
	}
	/**
	 * Loads help level
	 * @return an ArrayList of one level, the help level
	 */
	public ArrayList<Level> loadCustom() throws SAXException, XPathExpressionException, IOException, ParserConfigurationException
	{
		return loadLevels(CUSTOM_PATH);
	}
	/**
	 * Loads an ArrayList of levels from the given xml file path
	 * @param filePath the file path
	 * @return an ArrayList of levels
	 */
	public ArrayList<Level> loadLevels(String filePath) throws SAXException, XPathExpressionException, IOException, ParserConfigurationException
	{
		ArrayList<Level> levels = new ArrayList<>();
		InputStream f = getClass().getClassLoader().getResourceAsStream(filePath);
		if(filePath==CUSTOM_PATH){
			try{
				f = new FileInputStream(filePath);
			}catch(IOException e){}
		}
		
		DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuild = docFac.newDocumentBuilder();
		Document doc = docBuild.parse(f);
		
		XPathFactory xpFac = XPathFactory.newInstance();
		XPath path = xpFac.newXPath();
		
		int numLevels = Integer.parseInt(path.evaluate("count(/levels/level)", doc));
		for(int i=1;i<=numLevels;i++){
			String levelPath = "levels/level["+i+"]";
			double width = Double.parseDouble(path.evaluate(levelPath+"/@width", doc));
			
			double sx = Double.parseDouble(path.evaluate(levelPath+"/spaceman/xpos", doc));
			double sy = Double.parseDouble(path.evaluate(levelPath+"/spaceman/ypos", doc));
			SpaceMan sm = new SpaceMan(sx,sy);
			
			double fx = Double.parseDouble(path.evaluate(levelPath+"/flag/xpos", doc));
			double fy = Double.parseDouble(path.evaluate(levelPath+"/flag/ypos", doc));
			Flag fl = new Flag(fx,fy);
			
			LinkedList<Platform> terrain = new LinkedList<>();
			LinkedList<GameObject> ob = new LinkedList<>();
			LinkedList<GameObject> hazards = new LinkedList<>();
			ob.add(sm);
			ob.add(fl);
			int numObjects = Integer.parseInt(path.evaluate("count("+levelPath+"/objects/object)", doc));
			for(int j=1;j<=numObjects;j++){
				String oPath = levelPath+"/objects/object["+j+"]";
				String oType = path.evaluate(oPath+"/@type", doc);
				
				double ox = Double.parseDouble(path.evaluate(oPath+"/xpos", doc));
				double oy = Double.parseDouble(path.evaluate(oPath+"/ypos", doc));
				switch(oType){
					case "space-ship":
						ob.add(new SpaceShip(ox, oy));
						break;
				}
			}	

			int numHazards = Integer.parseInt(path.evaluate("count("+levelPath+"/hazards/hazard)", doc));
			
			for(int j=1;j<=numHazards;j++){
				String hPath = levelPath+"/hazards/hazard["+j+"]";
				String hType = path.evaluate(hPath+"/@type", doc);
				
				double hx = Double.parseDouble(path.evaluate(hPath+"/xpos", doc));
				double hy = Double.parseDouble(path.evaluate(hPath+"/ypos", doc));
				
				if(hType.equals("spike")){
					int sw = Integer.parseInt(path.evaluate(hPath+"/width", doc));
					hazards.add(new Spikes(hx,hy,sw));
				}else if(hType.equals("spike-flipped")){
					int sw = Integer.parseInt(path.evaluate(hPath+"/width", doc));
					hazards.add(new Spikes(hx,hy,sw, true));
				}
			}
			
			
			
			int numPlats = Integer.parseInt(path.evaluate("count("+levelPath+"/platforms/platform)", doc));
			
			for(int j=1;j<=numPlats;j++)
			{
				String platPath = levelPath+"/platforms/platform["+j+"]";
				String pType = path.evaluate(platPath+"/@type", doc);

				double xp = Double.parseDouble(path.evaluate(platPath+"/xpos", doc));
				double yp = Double.parseDouble(path.evaluate(platPath+"/ypos", doc));
				double w = Double.parseDouble(path.evaluate(platPath+"/width", doc));
				double h = Double.parseDouble(path.evaluate(platPath+"/height", doc));
				
				//Moving Platform
				if(pType!=null && pType.equals("moving")){
					double endX = Double.parseDouble(path.evaluate(platPath+"/xend", doc));
					double speed = Double.parseDouble(path.evaluate(platPath+"/speed", doc));
					terrain.add(new MovingPlatform(xp, yp, w, h, endX, speed));
				}
				//Non-moving Platform
				else{
					terrain.add(new Platform(xp,yp,w,h));
				}
			}
			levels.add(new Level(i,width,terrain,hazards,ob,sm,fl));
		}
		return levels;
	}
	
}