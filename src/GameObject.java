/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * GameObject represents an object that is drawn and moved in the game
 */
public abstract class GameObject 
{
	public static final long NANO_TO_MILLIS = 1_000_000L;
	public static final double SCALE = (double)Game.HEIGHT / Game.BASE_HEIGHT;
	
	protected double x, y, velX, velY, aX, aY;
	protected double h, w;
	protected long startTime, time;
	protected long a_delay = 100;
	protected BufferedImage img;
	
	/**
	 * Creates a game object with given x, y, width, and height.
	 * @param x top left x
	 * @param y top left y
	 * @param w the width
	 * @param h the height
	 */
	public GameObject(double x, double y, double w, double h)
	{
		this.x=x*SCALE;
		this.y=y*SCALE;
		this.h=h*SCALE;
		this.w=w*SCALE;
		velX = velY = 0;
		aX = aY = 0;
		startTime = System.nanoTime();
	}
	/**
	 * Creates a game object with given x, y, width, height, and path for an image.
	 * @param x top left x
	 * @param y top left y
	 * @param w the width
	 * @param h the height
	 * @param imagePath the image path
	 */
	public GameObject(double x, double y, double w, double h, String imagePath)
	{
		this(x,y,w,h);
		try{
			img = ImageIO.read(getClass().getResource(imagePath));
		}catch(IOException e){}
	}
	/**
	 * Called when GameObject is drawn.
	 * @param g2 the Graphics window to draw on.
	 */
	public abstract void draw(Graphics2D g2);
	/**
	 * Called evey a delay milliseconds to use if object is animated.
	 */
	public abstract void animate();
	
	/**
	 * Translates the GameObject horizontally by the given amount.
	 */
	public void scroll(double tx)
	{
		x+=tx;
	}
	
	/**
	 * Called every frame in the game to update object.
	 */
	public void tick()
	{
		x+=velX;
		y+=velY;
		velX+=aX;
		velY+=aY;
		time = System.nanoTime();
		long delta = time-startTime;
		if(delta>=a_delay*NANO_TO_MILLIS){
			animate();
			startTime = System.nanoTime();
		}
	}
	
	
	/**
	 * Returns the x of the Game Object
	 * @return the x of the Game Object
	 */
	public double getX(){return x;}
	/**
	 * Returns the y of the Game Object
	 * @return the y of the Game Object
	 */
	public double getY(){return y;}
	/**
	 * Returns the width of the Game Object
	 * @return the width of the Game Object
	 */
	public double getW(){return w;}
	/**
	 * Returns the height of the Game Object
	 * @return the height of the Game Object
	 */
	public double getH(){return h;}
	/**
	 * Returns the x velocity of the Game Object
	 * @return the x velocity of the Game Object
	 */
	public double getVelX(){return velX;}
	/**
	 * Returns the y velocity of the Game Object
	 * @return the y velocity of the Game Object
	 */
	public double getVelY(){return velY;}
	/**
	 * Returns the x acceleration of the Game Object
	 * @return the x acceleration of the Game Object
	 */
	public double getAX(){return aX;}
	/**
	 * Returns the y acceleration of the Game Object
	 * @return the y acceleration of the Game Object
	 */
	public double getAY(){return aY;}
	/**
	 * Returns the bounds of the Game Object
	 * @return the bounds of the Game Object
	 */
	public Rectangle2D getBounds(){return new Rectangle2D.Double(x,y,w,h);}
	
	/**
	 * Sets the x to the given value.
	 * @param x the new value.
	 */
	public void setX(double x){this.x=x;}
	/**
	 * Sets the y to the given value.
	 * @param y the new value.
	 */
	public void setY(double y){this.y=y;}
	/**
	 * Sets the width to the given value.
	 * @param w the new value.
	 */
	public void setW(double w){this.w=w;}
	/**
	 * Sets the height to the given value.
	 * @param h the new value.
	 */
	public void setH(double h){this.h=h;}
	/**
	 * Sets the x velocity to the given value.
	 * @param velX the new value.
	 */
	public void setVelX(double velX){this.velX=velX;}
	/**
	 * Sets the y velocity to the given value.
	 * @param velY the new value.
	 */
	public void setVelY(double velY){this.velY=velY;}
	/**
	 * Sets the x acceleration to the given value.
	 * @param ax the new value.
	 */
	public void setAX(double ax){aX=ax;}
	/**
	 * Sets the y acceleration to the given value.
	 * @param ay the new value.
	 */
	public void setAY(double ay){aY=ay;}
}