/**
 * Stores currently turned cards, allows only five cards to be uncovered on each turn.
 * Also handles turning cards back down after a delay if cards have different suits.
 * 
 * @author RUMHackers.java
 */

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;

public class FlushLevel extends FourOfAKindLevel {

	// FLUSH LEVEL: The goal is to find, on each turn, five cards with the same suit

	protected FlushLevel(TurnsTakenCounterLabel validTurnTime, ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Flush Level");
		this.setCardsToTurnUp(5);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
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
				// Sort the turned up cards.
				sortTurnedCards();
				if(PokerHand.isFlush(getTurnedCardsBuffer()) == true) {
					// The cards match, so set the score.
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
	
	@Override
	protected boolean  isGameOver() {
		// Initialize a dynamic array of strings.
		ArrayList<String> cardsDownList = new ArrayList<String>();
		for (int i = 0; i< this.getGrid().size(); i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				// Store every face-down card suit in the array.
				cardsDownList.add(this.getGrid().get(i).getSuit());
			}
		}
		// Sort the array
		Collections.sort(cardsDownList);
		for (int i = 0; i<cardsDownList.size(); i++) {
			int cardsWithSameSuit = 0;
			for (int j = 0; j<cardsDownList.size(); j++) {
				// Compare the selected card suit with the others
				if(cardsDownList.get(i).equals(cardsDownList.get(j))) {
					cardsWithSameSuit++;
					// If there are 5 cards with the same suit, then the game is not over
					if(cardsWithSameSuit == 5) {
						return false;
					}
				}
			}
		}
		// If the last five turned up cards have the same suit, then the game is over.
		if(this.getTurnedCardsBuffer().size() == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public String getMode() {
		return "FlushMode";
	}
}