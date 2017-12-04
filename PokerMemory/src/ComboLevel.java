/**
 * Stores currently turned cards, allows only five cards to be uncovered on each turn.
 * Also handles turning cards back down after a delay if the user don't want the uncovered hand.
 * 
 * @author UPRM Hackers.java
 */

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ComboLevel extends StraightLevel {

	// Combo Level: The goal is to find the best poker hands

	protected ComboLevel(TurnsTakenCounterLabel validTurnTime, ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		super.getTurnsTakenCounter().setDifficultyModeLabel("Combo Level");
	}

	@Override
	protected boolean turnUp(Card card) {
		// The card may be turned
		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) {
			// Add the card to the list
			this.getTurnedCardsBuffer().add(card);
			if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp()) {
				// We are uncovering the last card in this turn
				// Record the player's turn
				this.getTurnsTakenCounter().increment();
				// Sort turned up cards
				sortTurnedCards();
				card.faceUp();
				// Check hand
				int score = PokerHand.checkHand(getTurnedCardsBuffer());
				setScore(score, getPokerHand(score));
			}
			return true;
		}
		return false;
	}
	
	protected String getPokerHand(int score) {

		String hand = null;
		
		if(score >= 4500) {
			hand = "Straight Flush";
		}		
		else if(score >= 4000) {
			hand = "Four of a Kind";
		}
		else if (score >= 3500) {
			hand = "Full House";
		}
		else if (score >= 1000) {
			hand = "Straight";
		}
		else if (score >= 700) {
			hand = "Flush";
		}
		else if (score >= 500) {
			hand = "Three of a Kind";
		}
		else if (score >= 200) {
			hand = "Two Pair";
		}
		else if (score >= 100) {
			hand = "One Pair";
		}
		else {
			hand = "High Card";
		}
		return hand;
	}
	/**
	 * Ask the user if he wants to keep the hand and set the score depending of the decision.
	 * 
	 * @param score the score of the poker hand.
	 * @param hand the name of the poker hand.
	 */
	protected void setScore(int score, String hand) {
		boolean userMadeChoice = false;
		while (!userMadeChoice) {
			int decision = JOptionPane.showOptionDialog(null, "Poker Hand: "
						   + hand +  "\nPoints worth: " + Integer.toString(score)
					       + "\nDo you want to keep the hand?",
					       "UPRM Hackers.java Poker Memory", JOptionPane.OK_CANCEL_OPTION,
					       JOptionPane.INFORMATION_MESSAGE, null, new String[] { "No", "Yes" }, "default");
			if (decision == 1) {
				userMadeChoice = true;
				this.getScoreCounter().increment(score);
				this.getTurnedCardsBuffer().clear();
			} 
			else if (decision == 0) {
				this.getScoreCounter().increment(-25);
				this.getTurnDownTimer().start();
				userMadeChoice = true;
			}
		}
	}

	public String getMode() {
		return "ComboMode";
	}
}