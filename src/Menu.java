/* Kevin Kinney
 * Mrs. Gallatin
 * 2nd period
 */
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Menu window for beginning of game
 */
public class Menu extends JFrame implements MouseListener
{
	public static final int HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*.5);
	public static final int WIDTH = (int)(HEIGHT*1.3);
	
	public static Font opFont = new Font("Agency FB", Font.PLAIN, (int)(30*Game.SCALE));
	public static Font titleFont = new Font("Eras Medium ITC", Font.PLAIN, (int)(35*Game.SCALE));
	
	private JLabel title, start, help, quit, custom;
	private Game game;
	
	/**
	 * Constructs a Menu frame with given game reference.
	 * @param g the Game.
	 */
	public Menu(Game g)
	{
		game=g;
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Gravity Flux");
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(5,3));
		
		LinkedList<JLabel> panel = new LinkedList<>();
		
		panel.add(new JLabel());
		panel.add(title = new JLabel("Gravity Flux"));
		panel.add(new JLabel());
		
		panel.add(new JLabel());
		panel.add(start = new JLabel("START"));
		panel.add(new JLabel());
		
		panel.add(new JLabel());
		panel.add(help = new JLabel("HELP"));
		panel.add(new JLabel());
		
		panel.add(new JLabel());
		panel.add(custom = new JLabel("CUSTOM"));
		panel.add(new JLabel());
		
		panel.add(new JLabel());
		panel.add(quit = new JLabel("QUIT"));
		panel.add(new JLabel());
		
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
			if(label!=title && !s.isEmpty()){
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
			if(label!=title && !s.isEmpty()){
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
    	if(e.getSource()==start){
    		game.startGame();
    	}else if(e.getSource()==help){
    		game.help();
    	}else if(e.getSource()==custom){
    		game.custom();
    	}else if(e.getSource()==quit){
    		System.exit(0);
    	}			    
	}
}