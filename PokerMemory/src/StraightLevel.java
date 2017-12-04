/**
 * Stores currently turned cards, allows only five cards to be uncovered on each turn.
 * Also handles turning cards back down after a delay if cards are not consecutive in ranks.
 * 
 * @author UPRM Hackers.java
 */

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StraightLevel extends FlushLevel {

	// STRAIGHT LEVEL: The goal is to find, on each turn, five cards with consecutive ranks.

	protected StraightLevel(TurnsTakenCounterLabel validTurnTime, ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Straight Level");
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
				// Sort cards
				sortTurnedCards();
				// get the other card (which was already turned up)
				if(PokerHand.isStraight(getTurnedCardsBuffer()) == true) {
					int score = PokerHand.checkHand(getTurnedCardsBuffer());
					this.getScoreCounter().increment(score);
					this.getTurnedCardsBuffer().clear();
				}
				else {
					// The cards do not match, so start the timer to turn them down
					this.getTurnDownTimer().start();
					this.getScoreCounter().increment(-5);
				}
			}
			return true;
		}
		return false;
	}

}