/**
 * Represents a card, keeps Icons for front and back, handles mouse clicks.
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @author Modified by UPRM Hackers.java (UPRM)
 * @version Dic 2017
 */

import java.awt.event.*;
import javax.swing.*;

public class Card extends JLabel implements MouseListener {
	private static final long serialVersionUID = 1L;

	private GameLevel turnedManager;
	private Icon faceIcon;
	private Icon backIcon;
	private boolean faceUp = false;   			// Card is initially face down
	private int num;
	private String suit;
	private String rank;
	private int iconWidthHalf, iconHeightHalf; 	// Half the dimensions of the back face icon

	/**
	 * Constructor.
	 * 
	 * @param turnedManager object that manages list of currently turned cards.
	 * @param face the face of the card, only one other card has this face.
	 * @param back the back of the card, same as all other cards.
	 * @param num unique number associated with face icon, used to compare cards for equality.
	 * @param rank card rank in String form (e.g. "2", "q", "a", etc.).
	 * @param suit card suit in String form could be one of "c" (clubs), "d" (diamonds), "h" (hearts), or "s" (spades). 
	 */
	public Card(GameLevel turnedManager, Icon face, Icon back, int num, String rank, String suit) {
		// Initially show face down
		super(back);
		// Save parameters
		this.turnedManager = turnedManager;
		this.faceIcon = face;
		this.backIcon = back;
		this.num = num;
		this.suit = suit;
		this.rank = rank;
		// Catch mouse clicks and events
		this.addMouseListener(this);
		// Store icon dimensions for mouse click testing
		this.iconWidthHalf = back.getIconWidth() / 2;
		this.iconHeightHalf = back.getIconHeight() / 2;
	}
	
	/**
	 * Get the rank value of the card and convert it to an integer.
	 * 
	 * @return the rank value of the card as an integer.
	 */
	public int getRankValue() {
		if (this.rank.equals("a")) {
			return 20;
		}
		else if (this.rank.equals("k")) {
			return 13;
		}
		else if (this.rank.equals("q")) {
			return 12;
		}
		else if (this.rank.equals("j")) {
			return 11;
		}
		else if (this.rank.equals("t")) {
			return 10;
		}
		else {
			return Integer.valueOf(this.rank);
		}
	}

	// Getters

	public int getNum() 	 { return num; }
	public String getRank () { return rank; }
	public String getSuit () { return suit; }

	// Instance methods
	
	/**
	 * Try to turn card face up.
	 */
	public void turnUp() {
		MemoryGame.dprintln("Card["+num+"].turnUp()");
		// The card is already face up
		if(this.faceUp) return;
		// Ask manager to allow turn
		this.faceUp = this.turnedManager.turnUp(this);
		// Allowed to turn, so change the picture
		if(this.faceUp) this.setIcon(this.faceIcon);
	}

	/**
	 * Turn card back over, face down. Usually after timer expires.
	 */
	public void turnDown() {
		MemoryGame.dprintln("Card["+num+"].turnDown()");
		if(!this.faceUp) return;
		this.setIcon(this.backIcon);
		this.faceUp = false;
	}
	
	/**
	 * This method forces the card to flip face up.
	 * Can be used to guarantee that a selected card flips face up before a modal dialog pops up.
	 */
	public void faceUp() {
		this.faceUp = true;
		this.setIcon(this.faceIcon);
	}

	/**
	 * Check if the coordinates are over the icon.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @return true if coordinates are over icon, otherwise false.
	 */
	private boolean overIcon(int x, int y) {
		// Calculate the distance from the center of the label
		int distX = Math.abs(x - (this.getWidth() / 2));
		int distY = Math.abs(y - (this.getHeight() / 2));
		// Outside icon region
		if(distX > this.iconWidthHalf || distY > this.iconHeightHalf ) {
			return false;
		}
		// Inside icon region
		return true;
	}

	public boolean isFaceUp() {
		return faceUp;
	}

	/** Methods to implement MouseListener interface **********************/

	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on a component.
	 * 
	 * @param e object holding information about the button click.
	 */
	public void mouseClicked(MouseEvent e) {
		// Over icon, so try to turn up the card
		System.out.println("Mouse clicked");
		if(overIcon(e.getX(), e.getY())) this.turnUp();
	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 *
	 * @param e object holding information about the button press.
	 */
	public void mousePressed(MouseEvent e) {}

	/**
	 * Invoked when a mouse button has been released on a component.
	 *
	 * @param e object holding information about the button release.
	 */
	public void mouseReleased(MouseEvent e) {}

	/**
	 * Invoked when the mouse enters a component.
	 *
	 * @param e object holding information about the mouse pointer.
	 */
	public void mouseEntered(MouseEvent e) {}

	/**
	 * Invoked when the mouse exits a component.
	 *
	 * @param e object holding information about the mouse pointer.
	 */
	public void mouseExited(MouseEvent e) {}
	
}