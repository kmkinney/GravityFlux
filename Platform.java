/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Platforms are what the player walks on to complete each level.
 */
public class Platform extends GameObject
{
	private int platType;
	private Color color;
	
	/**
	 * Constructs a Platform with given x, y, width, and height.
	 * @param x the x position.
	 * @param y the y position.
	 * @param w the width.
	 * @param h the height.
	 */
	public Platform(double x, double y, double w, double h)
	{
		super(x,y,w,h);
		color=Color.LIGHT_GRAY;
	}
	/**
	 * Called when GameObject is drawn.
	 * @param g2 the Graphics window to draw on.
	 */
	public void draw(Graphics2D g2)
	{
		g2.setColor(color);
		g2.fill(new Rectangle2D.Double(x,y,w,h));
	}
	/**
	 * Called evey a delay milliseconds to use if object is animated.
	 */
	public void animate(){}
}