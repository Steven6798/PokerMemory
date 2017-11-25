import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ComboLevel extends FlushLevel {

	// TRIO LEVEL: The goal is to find, on each turn, three cards with the same rank

	protected ComboLevel(TurnsTakenCounterLabel validTurnTime, ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		super.getTurnsTakenCounter().setDifficultyModeLabel("Combo Level");
	}
	
	protected void sortTurnedCards() {
		for (int i = 0; i < this.getTurnedCardsBuffer().size() - 1; i++) {
			for (int j = 0; j < (this.getTurnedCardsBuffer().size() - 1) - i; j++) {
				if (this.getTurnedCardsBuffer().get(j).getRankValue() > this.getTurnedCardsBuffer().get(j + 1).getRankValue()) {
					Card temp = this.getTurnedCardsBuffer().get(j);
					this.getTurnedCardsBuffer().set(j, this.getTurnedCardsBuffer().get(j + 1));
					this.getTurnedCardsBuffer().set(j + 1, temp);
				}
			}
		}
	}

	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) {
			// add the card to the list
			this.getTurnedCardsBuffer().add(card);
			if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp()) {
				// We are uncovering the last card in this turn
				// Record the player's turn
				this.getTurnsTakenCounter().increment();
				// sort cards
				sortTurnedCards();
				int score = PokerHand.checkHand(getTurnedCardsBuffer());
				setScore(score, card, getPokerHand(score));
			}
			return true;
		}
		return false;
	}
	
	protected String getPokerHand(int score) {

		String hand = null;
		
		if(score >= 3500) {
			hand = "Four of a Kind";
		}
		else if (score >= 2000) {
			hand = "Full House";
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
	
	protected void setScore(int score, Card card, String hand) {
		boolean userMadeChoice = false;
		while (!userMadeChoice) {
			int decision = JOptionPane.showOptionDialog(null, "Poker Hand: "
					+ hand +  "\nPoints worth: " + Integer.toString(score)
					+ "\nDo you want to keep the hand?",
					"UPRM Hackers memory", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, new String[] { "No", "Yes" }, "default");
			if (decision == 1) {
				userMadeChoice = true;
				this.getScoreCounter().increment(score);
				this.getTurnedCardsBuffer().clear();
			} 
			else if (decision == 0) {
				this.getScoreCounter().increment(-50);
				this.getTurnDownTimer().start();
				userMadeChoice = true;
			}
		}
	}

	public String getMode() {
		return "ComboMode";
	}
}