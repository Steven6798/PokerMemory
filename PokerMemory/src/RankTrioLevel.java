/**
 * Stores currently turned cards, allows only three cards to be uncovered on each turn.
 * Also handles turning cards back down after a delay if cards have different ranks.
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @author Modified by RUMHackers.java (UPRM)
 * @version Dic 2017
 */

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class RankTrioLevel extends EqualPairLevel {

	// TRIO LEVEL: The goal is to find, on each turn, three cards with the same rank

	protected RankTrioLevel(TurnsTakenCounterLabel validTurnTime, ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Trio Level");
		this.setCardsToTurnUp(3);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
	}

	@Override
	protected void makeDeck() {
		// In Trio level the grid consists of distinct cards, no repetitions

		//Back card
		ImageIcon backIcon = this.getCardIcons()[this.getTotalCardsPerDeck()];

		int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
		for(int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++) {
			cardsToAdd[i] = i;
		}

		// Randomize the order of the deck
		this.randomizeIntArray(cardsToAdd);

		// Make each card object
		for(int i = 0; i < cardsToAdd.length; i++) {
			// Number of the card, randomized
			int num = cardsToAdd[i];
			// Make the card object and add it to the panel
			String rank = cardNames[num].substring(0, 1);
			String suit = cardNames[num].substring(1, 2);
			this.getGrid().add( new Card(this, this.getCardIcons()[num], backIcon, num, rank, suit));
		}
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
				// Get the other card (which was already turned up)
				Card card1 = (Card) this.getTurnedCardsBuffer().get(0);
				Card card3 = (Card) this.getTurnedCardsBuffer().get(2);
				if((card1.getRank().equals(card3.getRank()))) {
					// Three cards match, so remove them from the list (they will remain face up)
					this.getTurnedCardsBuffer().clear();
					int rankValue = card.getRankValue();
					this.getScoreCounter().increment(100 + (rankValue * 3));
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
	public String getMode() {
		return "TrioMode";
	}
	
	@Override
	protected boolean  isGameOver() {
		// Initialize a dynamic array of integers
		ArrayList<Integer> cardsDownList = new ArrayList<Integer>();
		for (int i = 0; i< this.getGrid().size(); i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				if(this.getGrid().get(i).getRankValue() <= 13) {
					// Store every face-down card rank value in the array except the A
					cardsDownList.add(this.getGrid().get(i).getRankValue());
				}
			}
		}
		for (int i = 0; i<cardsDownList.size(); i++) {
			int cardRepetition = 0;
			for (int j = 0; j<cardsDownList.size(); j++) {
				// Compare the selected card rank value with the other rank values
				if(cardsDownList.get(i) == cardsDownList.get(j)) {
					cardRepetition++;
					// If there is more than 1 card with the same value, then the game is not over
					if (cardRepetition > 1) {
						return false;
					}
				}
			}
		}
		return true;
	}
}