/**
 * Stores currently turned cards, allows only five cards to be uncovered on each turn.
 * Also handles turning cards back down after a delay if cards are not consecutive in ranks.
 * 
 * @author RUMHackers.java
 */

import java.util.ArrayList;
import java.util.Collections;
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
	
	@Override
	protected boolean isGameOver() {
		// Sort turned up cards
		sortTurnedCards();
		ArrayList<Integer> cardsDownList = new ArrayList<Integer>();
		for (int i=0; i<this.getGrid().size(); i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				// Store every face-down card rank value on the array
				cardsDownList.add(this.getGrid().get(i).getRankValue());
			}
		}
		// Sort the array
		Collections.sort(cardsDownList);
		for (int i=0;i<cardsDownList.size();i++) {
			int consecutiveCards = 1;
			for (int j=0; j<cardsDownList.size(); j++) {
				// Compare the selected card rank value with the other rank values
				if(cardsDownList.get(i) == (cardsDownList.get(j) - consecutiveCards)) {
					consecutiveCards++;
					// If there are 5 consecutive cards, then the game is not over
					if (consecutiveCards == 5) {
						return false;
					}
				}
			}
		}
		// If the last five turned up cards were consecutive, then the game is over.
		if(this.getTurnedCardsBuffer().size() == 0) {
			return true;
		}
		return false;
	}
}