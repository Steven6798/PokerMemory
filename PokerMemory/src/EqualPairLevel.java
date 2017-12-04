/**
 * Stores currently turned cards, allows only two cards to be uncovered on each turn
 * Also handles turning cards back down after a delay if cards are different
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @author Modified by UPRM Hackers.java (UPRM)
 * @version Dic 2017
 */

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class EqualPairLevel extends EasyLevel {

	protected EqualPairLevel(TurnsTakenCounterLabel validTurnTime, ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Medium Level");
	}
	
	@Override
	protected void makeDeck() {
		// Creates a deck to fill the grid.  Each card appears twice in random places.
		ImageIcon backIcon = this.getCardIcons()[this.getTotalCardsPerDeck()];

		// Make an array of card numbers: 0, 0, 1, 1, 2, 2, ..., 7, 7
		// Duplicate the image in as many cards as the input imageClones
		int totalCardsInGrid = getRowsPerGrid() * getCardsPerRow();
		int totalUniqueCards = totalCardsInGrid/2;

		// Generate one distinct random card number for each unique card	
		int cardsToAdd[] = new int[totalCardsInGrid];
		boolean cardChosen[] = new boolean[getTotalCardsPerDeck()];

		int chosenCount = 0;
		Random rand = new Random();
		while (chosenCount < totalUniqueCards) {
			int nextCardNo = rand.nextInt(getTotalCardsPerDeck());
			if (!cardChosen[nextCardNo]) {
				cardChosen[nextCardNo] = true;
				cardsToAdd[2*chosenCount] = nextCardNo;
				cardsToAdd[2*chosenCount + 1] = nextCardNo;
				chosenCount++;
			}
		}

		// Randomize the order of the cards
		this.randomizeIntArray(cardsToAdd);

		// Make each card object and add it to the game grid
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
			this.getTurnedCardsBuffer().add(card);
			if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp()) {
				// There are two cards faced up
				// Record the player's turn
				this.getTurnsTakenCounter().increment();
				// Get the other card (which was already turned up)
				Card otherCard = (Card) this.getTurnedCardsBuffer().get(0);
				// The cards match, so remove them from the list (they will remain face up) and add 50 points
				if( otherCard.getNum() == card.getNum()) {
					this.getTurnedCardsBuffer().clear();
				    this.getScoreCounter().increment(50);
				}
				// The cards do not match, so start the timer to turn them down and decrease 5 points
				else {
					this.getScoreCounter().increment(-5);
					this.getTurnDownTimer().start();
				}
			}
			return true;
		}
		// There are already the number of EasyMode (two face up cards) in the turnedCardsBuffer
		return false;
	}

	@Override
	public String getMode() {
		return "MediumMode";
	}
}