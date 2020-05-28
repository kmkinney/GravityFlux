/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Flag represents the end goal of each level
 */
public class Flag extends GameObject
{
	public static String img_path = "Flag.png";
	public static double height=100, width=50;
	
	/**
	 * Constructs a flag with given bottom left x and y.
	 * @param x the x position.
	 * @param y the y position.
	 */
	public Flag(double x, double y)
	{
		super(x,y-height,width,height, img_path);
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