/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * Main runner and window for the game.
 */
public class Game extends JFrame 
{
	public static final int BASE_HEIGHT = 700;
	public static final int BASE_WIDTH = 1000;
	public static final int S_HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*.7);
	public static final int S_WIDTH = (int)(S_HEIGHT*((double)BASE_WIDTH/BASE_HEIGHT));
	public static final int HEIGHT = (int)((double)S_HEIGHT * Toolkit.getDefaultToolkit().getScreenResolution()/100);
	public static final int WIDTH = (int)(HEIGHT*((double)BASE_WIDTH/BASE_HEIGHT));
	public static final double SCALE = (double)Game.S_HEIGHT / Game.BASE_HEIGHT;
	
	private GameComponent comp;
	
	/**
	 * Starting point. Makes a menu.
	 */
	public Game()
	{
		new Menu(this);
	}
	/**
	 * Starts actual game.
	 */
	public void startGame()
	{
		setSize(S_WIDTH, S_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		comp = new GameComponent(this);
		add(comp);
		
		setVisible(true);
	}
	/**
	 * Starts game in help mode.
	 */
	public void help()
	{
		setSize(S_WIDTH, S_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		comp = new GameComponent(true, this);
		add(comp);
		
		setVisible(true);
	}
	/**
	 * Starts game in help mode.
	 */
	public void custom()
	{
		setSize(S_WIDTH, S_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		comp = new GameComponent(false, this);
		add(comp);
		
		setVisible(true);
	}
	/**
	 * Runner entry point.
	 */
	public static void main (String[] args) 
	{
		new Game();
	}
}