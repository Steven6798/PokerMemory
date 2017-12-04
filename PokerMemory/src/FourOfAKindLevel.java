/**
 * Stores currently turned cards, allows only four cards to be uncovered on each turn.
 * Also handles turning cards back down after a delay if cards have different ranks.
 * 
 * @author UPRM Hackers.java
 */

import javax.swing.JFrame;

public class FourOfAKindLevel extends RankTrioLevel {
	
	// FOUR KIND LEVEL: The goal is to find, on each turn, four cards with the same rank

	protected FourOfAKindLevel (TurnsTakenCounterLabel validTurnTime, ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		super.getTurnsTakenCounter().setDifficultyModeLabel("Four of a kind Level");
		this.setCardsPerRow(8);
		this.setRowsPerGrid(6);
		this.setCardsToTurnUp(4);
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
				// Sort the turned up cards
				sortTurnedCards();
				// Get the other card (which was already turned up)
				Card card1 = (Card) this.getTurnedCardsBuffer().get(0);
				Card card4 = (Card) this.getTurnedCardsBuffer().get(3);
				if ((card1.getRank().equals(card4.getRank()))) {
					// Increases the score depending on the rank of the cards
					int sumOfRanks = card.getRankValue() * 4;
					this.getScoreCounter().increment(800 + sumOfRanks);
					this.getTurnedCardsBuffer().clear();						
				}
				else { 
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
		for (int i =0; i< this.getGrid().size();i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				return false;
			}
		}
		return true;
	}
	
	public String getMode() {
		return "FourKindMode";
	}
}