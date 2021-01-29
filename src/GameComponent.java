/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 * Represents the main Game loop and canvas to draw on. Master control for the whole game.
 */
public class GameComponent extends JComponent implements Runnable
{
	public static final int FR = 60;
	public static final double SCROLL_POS=500, LEFT_SCROLL_POS=100;
	public static final double MIN_FALL_SPEED = 5*GameObject.SCALE;
	public static Font helpFont = new Font("Agency FB", Font.PLAIN, (int)(40*GameObject.SCALE));
	
	private SpaceMan sm;
	private Flag fl;
	private LinkedList<GameObject> gmObjects, hazards;
	private LinkedList<Platform> terrain;
	private Stack<Life> lives;
	private Thread t;
	private boolean[] keyDown = new boolean[4];
	private int level, numLevels;
	private LevelParser lp;
	private ArrayList<Level> levels;
	private double screenScroll, levelWidth;
	private Platform currPlat;
	private boolean help, custom, dev, running;
	private long time;
	private Game game;
	
	/**
	 * Constructs a GameComponent with given reference to the game window.
	 * @param g the Game
	 */
	public GameComponent(Game g)
	{
		game=g;
		setFocusable(true);
		addKeyListener(new KeyHandler());
		lp = new LevelParser();
		try{
			levels =lp.loadLevels();
		}catch(Exception e){e.printStackTrace();}
		level=0;
		numLevels=levels.size();
		loadLevelData();
		screenScroll=0;
		currPlat = null;
		lives = new Stack<Life>();
		lives.push(new Life(5,10));
		lives.push(new Life(30,10));
		lives.push(new Life(55,10));
		help=false;
		dev=false;
		start();
	}
	/**
	 * Constructs a GameComponent with given reference to the game window in help mode.
	 * @param h used for help mode
	 * @param g the Game
	 */
	public GameComponent(boolean h, Game g)
	{
		game=g;
		setFocusable(true);
		addKeyListener(new KeyHandler());
		lp = new LevelParser();
		try{
			if(h)
				levels =lp.loadHelp();
			else
				levels = lp.loadCustom();
		}catch(Exception e){e.printStackTrace();}
		level=0;
		loadLevelData();
		screenScroll=0;
		currPlat = null;
		lives = new Stack<Life>();
		lives.push(new Life(5,5));
		lives.push(new Life(30,5));
		lives.push(new Life(55,5));
		help=h;
		custom=!h;
		numLevels=levels.size();
		start();
	}
	/**
	 * Loads current level data based on level variable
	 */
	public void loadLevelData()
	{
		gmObjects = levels.get(level).getObjects();
		terrain = levels.get(level).getPlatforms();

		//testing purposes
		//terrain.push(new MovingPlatform(400, 550, 200, 10, 600, 550, 2, 0));
		//

		hazards = levels.get(level).getHazards();
		gmObjects.addAll(terrain);
		gmObjects.addAll(hazards);
		sm = levels.get(level).getSpaceMan();
		fl = levels.get(level).getFlag();
		levelWidth = levels.get(level).getWidth();
		screenScroll=0;

	}
	/**
	 * Starts the game loop
	 */
	public void start()
	{
		t = new Thread(this);
		t.start();
		time=0;
		running=true;
	}
	/**
	 * Stops the game loop.
	 */
	public void stop()
	{
		running=false;
	}
	/**
	 * Paints all graphics objects.
	 * @param g the graphics window.
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.fill(new Rectangle2D.Double(0, 0, Game.WIDTH, Game.HEIGHT));
		for(GameObject go : gmObjects){
			go.draw(g2);
		}
		
		fl.draw(g2);
		
		if(dev){
			g2.setColor(Color.white);
			g2.draw(sm.lookAheadX());
			g2.draw(sm.lookAheadY());
			g2.draw(new Line2D.Double(SCROLL_POS,0,SCROLL_POS,Game.HEIGHT));
			g2.draw(new Line2D.Double(LEFT_SCROLL_POS,0,LEFT_SCROLL_POS,Game.HEIGHT));
			g2.setColor(Color.red);
			g2.draw(sm.hurtBox());
	
		}
		g2.setColor(Color.black);
		g2.fillRect(0,0,Game.WIDTH, (int)(50*GameObject.SCALE));
		for(Life l:lives){
			l.draw(g2);
		}
		g2.setFont(helpFont);
		g2.setColor(Color.white);
		g2.fillRect(0,(int)(50*GameObject.SCALE),Game.WIDTH, 3);
		if(level+1==3){
			g2.drawString("You Can Flip Gravity Once in midair!", 
				(int)(400*GameObject.SCALE-screenScroll), (int)(350*GameObject.SCALE));
		}
		if(help){
			g2.drawString("Welcome to Gravity Flux!", 
				(int)(20*GameObject.SCALE-screenScroll), (int)(300*GameObject.SCALE));
			g2.drawString("Use arrow keys to move", 
				(int)(500*GameObject.SCALE-screenScroll), (int)(400*GameObject.SCALE));
			g2.drawString("======>", 
				(int)(600*GameObject.SCALE-screenScroll), (int)(500*GameObject.SCALE));
			g2.drawString("Jump with UP", 
				(int)(1000*GameObject.SCALE-screenScroll), (int)(400*GameObject.SCALE));
			g2.drawString("Pressing space will flip gravity!", 
				(int)(2800*GameObject.SCALE-screenScroll), (int)(400*GameObject.SCALE));
			g2.drawString("Spikes are Bad!", 
				(int)(3500*GameObject.SCALE-screenScroll), (int)(500*GameObject.SCALE));
			g2.drawString("Grab the Flag to move on!", 
				(int)(4500*GameObject.SCALE-screenScroll), (int)(300*GameObject.SCALE));
		}
		else		
			g2.drawString(""+time, 
				(int)(Game.WIDTH-100), (int)(40*GameObject.SCALE));
	}
	/**
	 * Called every frame to update game status.
	 */
	public void tick()
	{
		for(GameObject go:gmObjects)
				go.tick();
		if(keyDown[0] && !keyDown[3]){
			sm.setDirection(Direction.LEFT);
		}else if(keyDown[3] && !keyDown[0]){
			sm.setDirection(Direction.RIGHT);
		}
		else if(!keyDown[0]&&!keyDown[3]){
			sm.setDirection(Direction.STOP);
		}
		moveSM();
		repaint();
		if(!help)
			time++;
	}
	/**
	 * Scrolls the screen by the given amount
	 * @param scroll the amount to scroll
	 */
	public void scroll(double scroll)
	{
		if(screenScroll+scroll >= 0 && screenScroll+scroll+Game.WIDTH<=levelWidth){
			screenScroll+=scroll;
			for(GameObject go:gmObjects){
				go.scroll(-scroll);
			}
		}
	}
	/**
	 * Handles all collision detection involved with moving the player.
	 */
	public void moveSM()
	{
		if(!sm.isFlipping()){
			Rectangle2D look = sm.lookAheadX();
			boolean xhit = false;
			for(Platform pl:terrain){
				if(pl.getBounds().intersects(look)){
					xhit=true;
					break;
				}
			}
			if(!xhit && look.getX()>0 && look.getX()+look.getWidth()<Game.WIDTH){
				sm.incX();
			}
			if(sm.getX()<0)
				sm.setX(0);
			if(look.getX()+look.getWidth() >= SCROLL_POS && sm.getVelX()>=0 || look.getX()<=LEFT_SCROLL_POS && sm.getVelX()<=0){
				scroll(sm.getVelX());
			}
			
			
			look = sm.lookAheadY();
			boolean yhit = false;
			for(Platform pl:terrain)
			{
				if(pl.getBounds().intersects(look)){
					yhit=true;
					if(!sm.isFlipped() && sm.getY()<pl.getY() || sm.isFlipped() && sm.getY()>pl.getY()+pl.getH()){
						//above
						sm.stopJump();
						currPlat=pl;
					}
					else{
						sm.setVelY(0);
					}
					break;
				}
			}
			if(!yhit){
				sm.incY();
				sm.fall();
				if(look.getY()>Game.HEIGHT+10 || look.getY()+look.getHeight()<-10){
					hit();
				}
			}
			else{
				sm.setVelY(sm.getVelY()/2);
			}
			if(currPlat!=null && (sm.getX() < currPlat.getX() || sm.getX()+1 > currPlat.getX()+currPlat.getW())){
				if(keyDown[1])
					sm.startJump();
				else
					sm.fall();
			}
			
			look = sm.lookAhead();
			if(look.intersects(fl.getBounds())){
				nextLevel();
			}
			for(GameObject go:hazards){
				if(sm.hurtBox().intersects(go.getBounds())){
					hit();
				}
			}
			//Moving Platform Mechanics
			if(currPlat!=null && currPlat.isMoving()){
				sm.setPlatformSpeed(currPlat.getVelX());
			}
			else{
				sm.setPlatformSpeed(0);
			}
			if(sm.isJumping()){
				currPlat=null;
			}
		}
	}
	/**
	 * Advances the level
	 */
	public void nextLevel()
	{
		if(help){
			help=false;
			try{
				levels =lp.loadLevels();
			}catch(Exception e){e.printStackTrace();}
			level=0;
			numLevels=levels.size();
			loadLevelData();
		}
		else{
			level++;
			if(level==numLevels){
				win();
			}else{
				loadLevelData();
			}
		}
	}
	/**
	 * Damages the character.
	 */
	public void hit()
	{
		if(lives.isEmpty())
			gameOver();
		else{
			if(!help)
				lives.pop();
			scroll(-screenScroll);
			sm.reset();
			currPlat=null;
			screenScroll=0;
		}
	}
	/**
	 * Called if player loses
	 */
	public void gameOver()
	{
		stop();
		new GameOver(false, time, level, !custom);
	}
	/**
	 * Called if player wins.
	 */
	public void win()
	{
		stop();
		new GameOver(true, time, level, !custom);
	}
	/**
	 * Game loop, part of Runnable interface. Keeps a constant frame rate.
	 */
	public synchronized void run()
	{
		long millis = 1000/FR;
		while(!Thread.interrupted() && running){
			long time = System.currentTimeMillis();
			tick();
			long dur = System.currentTimeMillis()-time;
			try{
				Thread.sleep(millis-dur>0?millis-dur:0);
			}catch(InterruptedException e){}
		}
		game.dispose();
	}
	/**
	 * KeyHandler handles key input
	 */
	class KeyHandler extends KeyAdapter
	{
		/**
		 * Called when key is pressed
		 * @param ke KeyEvent that occured.
		 */
		public void keyPressed(KeyEvent ke)
		{
			switch(ke.getKeyCode()){
				case KeyEvent.VK_SPACE:
					sm.flip();
					break;
				case KeyEvent.VK_LEFT:
					keyDown[0]=true;
					sm.setDirection(Direction.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					keyDown[3]=true;
					sm.setDirection(Direction.RIGHT);
					break;
				case KeyEvent.VK_UP:
					keyDown[1]=true;
					if(!sm.isJumping())
						sm.startJump();
					break;
				case KeyEvent.VK_D:
					dev=!dev;
					break;
				case KeyEvent.VK_H:
					lives = new Stack<Life>();
					lives.push(new Life(5,5));
					lives.push(new Life(30,5));
					lives.push(new Life(55,5));
					break;
			}
		}
		/**
		 * Called when key is released
		 * @param ke KeyEvent that occured.
		 */
		public void keyReleased(KeyEvent ke)
		{
			switch(ke.getKeyCode()){
				case KeyEvent.VK_LEFT:
					keyDown[0]=false;
					if(!keyDown[3])
						sm.setDirection(Direction.STOP);
					break;
				case KeyEvent.VK_RIGHT:
					keyDown[3]=false;
					if(!keyDown[0])
						sm.setDirection(Direction.STOP);
					break;
				case KeyEvent.VK_UP:
					keyDown[1]=false;
					if(sm.isFlipped() && sm.getVelY()>MIN_FALL_SPEED){
						sm.setVelY(MIN_FALL_SPEED);
					}else if(sm.getVelY()<-MIN_FALL_SPEED){
						sm.setVelY(-MIN_FALL_SPEED);
					}
					break;
			}
		}
	}
}