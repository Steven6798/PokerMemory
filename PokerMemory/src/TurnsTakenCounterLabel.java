/**
 * Inherits from JLabel and implements the turn counter widget.
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @version Sept 2017
 */

import javax.swing.JLabel;

public class TurnsTakenCounterLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	
	// Data fields
	private int numTurns = 0;
	private String DESCRIPTION;
	
	/**
	 * Default constructor, starts counter at 0.
	 */
	public TurnsTakenCounterLabel() {
		super();
		reset();
	}
	
	public void setDifficultyModeLabel(String difficultyMode) {
		DESCRIPTION = "Turns taken: ";
		setHorizontalTextPosition(JLabel.LEFT);
	}
	
	public int getNumOfTurns() {
		return this.numTurns;
	}
	
	/**
	 * Update the text label with the current counter value.
	 */
	private void update() {
		this.setText(DESCRIPTION + this.getNumOfTurns());
	}
	
	/**
	 * Increments the counter and updates the text label.
	 */
	public void increment() {
		this.numTurns++;
		update();
	}
	
	/**
	 * Resets the counter to zero and updates the text label.
	 */
	public void reset() {
		this.numTurns = 0;
		update();
	}
}