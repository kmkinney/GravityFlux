/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Spikes is a hazard that the player must avoid
 */
public class Spikes extends GameObject
{
	public static String spikeImgPath = "Spike.png";
	public static double h0=200, spikeWidth0 = 150;
	public static double scale = .167;
	public static double height=h0*scale, spikeWidth=spikeWidth0*scale;
	
	private int num;
	private double sw, radians;
	/**
	 * Constructs a Spikes witb given bottom left x and y and number of spikes.
	 * @param xp the x position.
	 * @param yp the y position.
	 * @param n the number of spikes.
	 */
	public Spikes(double xp, double yp, int n)
	{
		super(xp,yp-(height-2*GameObject.SCALE),n*spikeWidth,height,spikeImgPath);
		num=n;
		sw=(w/n);
		radians=0;
	}
	public Spikes(double xp, double yp, int n, boolean flipped)
	{
		this(xp,yp+(height-2*GameObject.SCALE),n);
		radians=Math.PI;
	}
	/**
	 * Called when GameObject is drawn.
	 * @param g2 the Graphics window to draw on.
	 */
	public void draw(Graphics2D g2)
	{
		for(int i=0;i<num;i++){
			AffineTransform old = new AffineTransform();
			AffineTransform trans = new AffineTransform();
		
			trans.rotate(radians, (x + sw*i)+sw/2, y + h/2);
			g2.setTransform(trans);
			g2.drawImage(img, (int)(x + sw*i), (int)y, (int)sw, (int)h, null);
			g2.setTransform(old);
		}
		
	}
	public void animate(){}
}