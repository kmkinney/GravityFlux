/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import java.awt.*;

/**
 * Life represents the current amount of lives the character has.
 */
public class Life extends GameObject
{
	public static String lifeImgPath = "SpaceMan.png";
	public static double height = 30, width = 30;
	
	/**
	 * Constructs a life with given x and y.
	 * @param x the x position.
	 * @param y the y position.
	 */
	public Life(double x, double y)
	{
		super(x,y,width,height,lifeImgPath);	
	}
	/**
	 * Called when GameObject is drawn.
	 * @param g2 the Graphics window to draw on.
	 */
	public void draw(Graphics2D g2)
	{
		g2.drawImage(img, (int)x, (int)y, (int)w, (int)h, null);
	}
	/**
	 * Called evey a delay milliseconds to use if object is animated.
	 */
	public void animate(){}
}