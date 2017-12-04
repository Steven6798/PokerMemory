/**
 * Inherits from JLabel and implements the score counter widget.
 *
 * @author UPRM Hackers.java
 */

import javax.swing.JLabel;

public class ScoreCounterLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	// Data fields
	private int score = 0;
	
	/**
	 * Default constructor, starts counter at 0.
	 */
	public ScoreCounterLabel() {
		super();
		reset();
	}
	
	public int getScore() {
		return this.score;
	}
	
	/**
	 * Update the text label with the current counter value.
	 */
	private void update() {
		this.setText("Score: " + this.getScore());
	}
	
	/**
	 * Increments the counter and updates the text label.
	 */
	public void increment(int points) {
		this.score += points;
		update();
	}
	
	/**
	 * Resets the counter to zero and updates the text label.
	 */
	public void reset() {
		this.score = 0;
		update();
	}
}