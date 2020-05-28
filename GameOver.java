/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Game Over window for end of game
 */
public class GameOver extends JFrame implements MouseListener
{
	public static final int HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*.5);
	public static final int WIDTH = (int)(HEIGHT*1.3);
	
	public static Font titleFont = new Font("Agency FB", Font.ITALIC,(int)(45*GameObject.SCALE));
	public static Font opFont = new Font("Agency FB", Font.PLAIN, (int)(35*GameObject.SCALE));
	
	private JLabel title, playAgain, quit;
	private long s;
	private boolean saveScore;
	
	/**
	 * Constructs a Game over screen with whether the player won, their score, and the level achieved.
	 * @param won whether the player won
	 * @param score the score
	 * @param level the level achieved
	 * @param save whether to save scores
	 */
	public GameOver(boolean won, long score, int level, boolean save)
	{
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Gravity Flux");
		setFocusable(true);
		saveScore=won&&save;
		
		s=score;
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3,3));
		
		LinkedList<JLabel> panel = new LinkedList<>();
		
		panel.add(new JLabel());
		panel.add(title = new JLabel(won?"Victory!":"Game Over!"));
		panel.add(new JLabel());
		
		if(won){
			panel.add(new JLabel());
			panel.add(new JLabel("SCORE:\t"+score));
			panel.add(new JLabel());
		}
		else{
			panel.add(new JLabel());
			panel.add(new JLabel("LEVEL REACHED:    "+(level+1)));
			panel.add(new JLabel());
		}
		
		panel.add(playAgain = new JLabel("PlayAgain"));
		panel.add(new JLabel(""));
		panel.add(quit = new JLabel("Quit"));
		
		for(JLabel j:panel){
			j.setForeground(Color.white);
			j.setFont(j==title?titleFont:opFont);
			j.setHorizontalAlignment(JLabel.CENTER);
			j.addMouseListener(this);
			p.add(j);
		}
		p.setBackground(Color.BLACK);
		
		
		
		add(p);
		setVisible(true);
	}
	/**
	 * required by the MouseListener interface.  Invoked when the mouse is clicked.
	 * @param e the MouseEvent that triggered the method
	 */
	public void mouseClicked(MouseEvent e){}
	/**
	 * required by the MouseListener interface.  Invoked when the mouse is released.
	 * @param e the MouseEvent that triggered the method
	 */
	public void mouseReleased(MouseEvent e){}
	/**
	 * required by the MouseListener interface.  Invoked when no mouse buttons are clicked and 
	 * the mouse pointer enters a particular gui element.  In this implementation, if the mouse
	 * pointer enters one of the menu labels, the label is set to red.
	 * @param e the MouseEvent that triggered the method
	 */
	public void mouseEntered(MouseEvent e)
	{
		if(e.getSource() instanceof JLabel){
			JLabel label = (JLabel) e.getSource();
			String s = label.getText();
			if((label==playAgain||label==quit) && !s.isEmpty()){
				label.setText(">"+s);
				label.setForeground(Color.red);
			}
		}
	}
	/**
	 * required by the MouseListener interface.  Invoked when no mouse buttons are clicked and 
	 * the mouse pointer leaves a particular gui element.  In this implementation, if the mouse
	 * pointer leaves one of the menu labels, the label is set to back to black.
	 * @param e the MouseEvent that triggered the method
	 */
	public void mouseExited(MouseEvent e)
	{
		if(e.getSource() instanceof JLabel){
			JLabel label = (JLabel) e.getSource();
			String s = label.getText();
			if((label==playAgain||label==quit) && !s.isEmpty()){
				label.setText(s.substring(1));
				label.setForeground(Color.white);
			}
		}
	}
	/**
	 * required by the MouseListener interface.  Invoked when the left mouse button is pressed.
	 * In this implementation, if the mouse is pressed on a particular labels, that menu item
	 * is invoked.
	 * @param e the MouseEvent that triggered the method
	 */
	public void mousePressed(MouseEvent e) 
	{
    	if(e.getSource()==playAgain){
    		this.dispose();
    		new Scores(true,s, saveScore);
    	}
    	if(e.getSource()==quit){
    		this.dispose();
    		new Scores(false,s, saveScore);
    	}			    
	}
}