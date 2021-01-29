/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Score window displays past scores.
 */
public class Scores extends JFrame implements KeyListener
{
	public static final int HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*.5);
	public static final int WIDTH = (int)(HEIGHT*1.3);
	
	public static Font titleFont = new Font("Agency FB", Font.ITALIC,(int)(45*Game.SCALE));
	public static Font opFont = new Font("Agency FB", Font.PLAIN, (int)(35*Game.SCALE));
	public static String savepath = "scores.dat";
	
	private JLabel title, cont; 
	private PriorityQueue<Integer> scores;
	private boolean playAgain;
	
	/**
	 * Constructs a Scores window with whether the player is playing again and the score
	 * @param again whether the player is playing again.
	 * @param score the score
	 * @param save whether to save scores
	 */
	public Scores(boolean again, long score, boolean save)
	{
		playAgain=again;
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Gravity Flux");
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(10,1));
		
		
		title = new JLabel("TOP SCORES");
		title.setForeground(Color.white);
		title.setFont(titleFont);
		title.setHorizontalAlignment(JLabel.CENTER);
		
		cont = new JLabel("PRESS ANY KEY...");
		cont.setForeground(Color.white);
		cont.setFont(titleFont);
		cont.setHorizontalAlignment(JLabel.CENTER);
		
		scores = new PriorityQueue<>();
		try{
			Scanner in = new Scanner(new File(savepath));
			while(in.hasNextInt()){
				scores.add(in.nextInt());
			}
		}catch(IOException e){}
		if(save)
			scores.add((int)score);
		try{
			PrintWriter write = new PrintWriter(savepath);
			for(int i=0;i<10;i++){
				String s="";
				if(!scores.isEmpty()){
					s=""+scores.poll();
					write.println(s);
				}
				JLabel l = new JLabel(s);
				l.setFont(opFont);
				l.setForeground(Color.white);
				l.setHorizontalAlignment(JLabel.CENTER);
				p.add(l);
			}
			write.close();
		}catch(IOException e){
			
		}
			
		
		getContentPane().setBackground(Color.black);
		p.setBackground(Color.BLACK);
		
		setLayout(new BorderLayout());
		add(title, BorderLayout.NORTH);
		add(cont, BorderLayout.SOUTH);
		add(p, BorderLayout.CENTER);
		addKeyListener(this);
		setVisible(true);
	}
	
	/**
	 * Called when key pressed
	 * @param e the KeyEvent
	 */
	public void keyPressed(KeyEvent e)
	{
		this.dispose();
		if(playAgain){
			new Game();
		}else
			System.exit(0);
	}
	/**
	 * Called when key released
	 * @param e the KeyEvent
	 */
	public void keyReleased(KeyEvent e){}
	/**
	 * Called when key typed
	 * @param e the KeyEvent
	 */
	public void keyTyped(KeyEvent e){}
}