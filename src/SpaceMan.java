/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SpaceMan is the character the player controls.
 */
public class SpaceMan extends GameObject
{
	public static final int SPRITE_W = 100, SPRITE_H=160, NUM_COLS=4, NUM_ROWS=2;
	
	public static final double WALK_SPEED = 6*GameObject.SCALE;
	public static final double JUMP_SPEED = 15*GameObject.SCALE;
	public static final double MAX_FALL_SPEED=7*GameObject.SCALE;
	public static final double G = 1*GameObject.SCALE;
	
	public static final double WALK_ACC_TIME=10;
	public static final long WALK_DELAY = 200;
	public static final double SPIN_INC=.2;
	
	
	
	public static String imgPath = "SpaceManWalk_SpriteSheet.png";
	public static double h0 = 159, w0 = 100;
	public static double scale = 0.4;
	public static double walk_a = (WALK_SPEED/WALK_ACC_TIME)*GameObject.SCALE;
	
	private BufferedImage[][] sprites;
	private BufferedImage curr;
	private int sprRow, sprCol;
	private int walkAccDelay;
	private Direction dir;
	private boolean jumping, flipping, flipped, canFlip, canJump;
	private double radians;
	private double x0, y0;
	
	/**
	 * Constructs a SpaceMan with given bottom left x and y.
	 * @param xp the x position.
	 * @param yp the y position.
	 */
	public SpaceMan(double xp, double yp)
	{
		super(xp, yp-(h0*scale-1), w0*scale, h0*scale, imgPath);
		x0=x;
		y0=y;
		sprites=new BufferedImage[NUM_ROWS][NUM_COLS];
		for(int i=0;i<NUM_ROWS;i++)
		{
			for(int j=0;j<NUM_COLS;j++){
				sprites[i][j] = img.getSubimage(j*SPRITE_W, i*SPRITE_H, SPRITE_W, SPRITE_H-2);
			}
		}
		sprRow=0;
		sprCol=1;
		dir = Direction.STOP;
		a_delay=WALK_DELAY;
		curr = sprites[0][1];
		jumping=false;
		flipping=false;
		flipped=false;
		canFlip=true;
		canJump=true;
		radians=0;
	}
	/**
	 * Sets the direction to the given value.
	 * @param d the new Direction.
	 */
	public void setDirection(Direction d)
	{
		if(!flipping){
			if(d==Direction.RIGHT){
				sprRow=flipped?1:0;
				if(dir!=d){
					setAX(walk_a);
					sprCol=0;
				}
				curr=sprites[sprRow][sprCol];	
			}
			else if(d==Direction.LEFT){
				sprRow=flipped?0:1;
				if(dir!=d){
					setAX(-walk_a);
					sprCol=0;
				}
				curr=sprites[sprRow][sprCol];
			}
			else if(d==Direction.STOP){
				setVelX(0);
				setAX(0);
				sprCol = 1;
				curr=sprites[sprRow][sprCol];
			}
			dir=d;	
		}
	}
	/**
	 * Called every frame in the game to update object.
	 */
	public void tick()
	{
		if(!flipping){
			velX+=aX;
			velY+=aY;
		}   
		if(Math.abs(velX)>=WALK_SPEED){
			setAX(0);
		}
		if(!flipped && velY>MAX_FALL_SPEED || flipped && velY<-MAX_FALL_SPEED){
			velY-=aY;
			setAY(0);
		}
		if(flipping){
			radians+=SPIN_INC;
			if(flipped && radians>=Math.PI || !flipped&&radians>=2*Math.PI){
				flipping=false;
				if(flipped)
					radians=Math.PI;
				else
					radians=0;
			}
		}
		time = System.nanoTime();
		long delta = time-startTime;
		if(delta>=a_delay*NANO_TO_MILLIS){
			animate();
			startTime = System.nanoTime();
		}
	}
	/**
	 * Called when GameObject is drawn.
	 * @param g2 the Graphics window to draw on.
	 */
	public void draw(Graphics2D g2)
	{
		AffineTransform old = new AffineTransform();
		AffineTransform trans = new AffineTransform();
		
		trans.rotate(radians, x + w/2, y + h/2 );
		g2.setTransform(trans);
		
		g2.drawImage(curr, (int)x, (int)y, (int)w, (int)h, null);
		
		g2.setTransform(old);
	}
	/**
	 * Called evey a delay milliseconds to use if object is animated.
	 */
	public synchronized void animate()
	{
		if(!jumping && dir!=Direction.STOP){
			sprCol=(sprCol+1)%NUM_COLS;
			curr=sprites[sprRow][sprCol];
		}
	}
	/**
	 * Starts a jump.
	 */
	public void startJump()
	{
		if(!jumping){
			jumping=true;
			setVelY(flipped?JUMP_SPEED:-JUMP_SPEED);
			setAY(flipped?-G:G);
		}
	}
	/**
	 * Starts a fall of a platform.
	 */
	public void fall()
	{
		aY=flipped?-G:G;
	}
	/**
	 * Stops a jump.
	 */
	public void stopJump()
	{
		canJump=true;
		jumping=false;
		canFlip=true;
		setVelY(0);
		setAY(0);
	}
	/**
	 * Flips gravity.
	 */
	public void flip()
	{
		if(canFlip){
			velY=0;
			flipping=true;
			flipped=!flipped;
			jumping=true;
			canFlip=false;
			aY=flipped?G:-G;
			sprRow = (sprRow+1)%2;
		}
	}
	/**
	 * Resets to the beginning of the level conditions.
	 */
	public void reset()
	{
		canFlip=true;
		jumping=false;
		x=x0;
		y=y0;
		velX=0;
		velY=0;
		aX=0;
		aY=0;
		if(flipped){
			flip();
		}
		radians=0;
		sprRow=0;
		sprCol=1;
	}
	
	/**
	 * Returns a rectangle representing the next state given current position and velocity.
	 * @return a rectangle representing the next state given current position and velocity.
	 */
	public Rectangle2D lookAhead()
	{
		return new Rectangle2D.Double(x+velX, y+velY, w, h-2);
	}
	/**
	 * Returns a rectangle representing the next state given current x position and x velocity.
	 * @return a rectangle representing the next state given current x position and x velocity.
	 */
	public Rectangle2D lookAheadX()
	{
		return new Rectangle2D.Double(x+velX, y, w, h-2);
	}
	/**
	 * Returns a rectangle representing the next state given current y position and y velocity.
	 * @return a rectangle representing the next state given current y position and y velocity.
	 */
	public Rectangle2D lookAheadY()
	{
		return new Rectangle2D.Double(x, y+velY, w, h-2);
	}
	/**
	 * Returns a rectangle representing the hurt box.
	 * @return a rectangle representing the hurt box.
	 */
	public Rectangle2D hurtBox()
	{
		return new Rectangle2D.Double(x+w*.1, y, w*.6, h-2);
	}
	/**
	 * Returns the direction.
	 * @return the direction.
	 */
	public Direction getDirection(){return dir;}
	/**
	 * Returns whether the SpaceMan is flipped.
	 * @return whether the SpaceMan is flipped.
	 */
	public boolean isFlipped(){return flipped;}
	/**
	 * Returns whether the SpaceMan is flipping.
	 * @return whether the SpaceMan is flipping.
	 */
	public boolean isFlipping(){return flipping;}
	/**
	 * Returns whether the SpaceMan is jumping.
	 * @return whether the SpaceMan is jumping.
	 */
	public boolean isJumping(){return jumping;}
	/**
	 * Returns whether the SpaceMan is jumping.
	 * @return whether the SpaceMan is jumping.
	 */
	public boolean canJump(){return !jumping&&canJump;}
	/**
	 * Returns whether the SpaceMan is jumping.
	 * @return whether the SpaceMan is jumping.
	 */
	public void setCanJump(boolean a){canJump=a;}
	/**
	 * Steps in x direction.
	 */
	public void incX(){x+=velX;}
	/**
	 * Steps in y direction.
	 */
	public void incY(){y+=velY;}
}