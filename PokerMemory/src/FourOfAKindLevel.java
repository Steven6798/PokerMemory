import javax.swing.JFrame;

public class FourOfAKindLevel extends ComboLevel {

	protected FourOfAKindLevel (TurnsTakenCounterLabel validTurnTime, ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		super.getTurnsTakenCounter().setDifficultyModeLabel("Four of a kind Level");
		this.setCardsPerRow(6);
		this.setRowsPerGrid(6);
		this.setCardsToTurnUp(4);
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
				// get the other card (which was already turned up)
				Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
				Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
				Card otherCard3 = (Card) this.getTurnedCardsBuffer().get(2);
				Card otherCard4 = (Card) this.getTurnedCardsBuffer().get(3);
				if ((otherCard1.getRank().equals(otherCard2.getRank()))
					&& (otherCard2.getRank().equals(otherCard3.getRank())) 
					&& (otherCard3.getRank().equals(otherCard4.getRank()))) {
					// Increases the score depending on the rank of the cards
					int sumOfRanks = otherCard1.getRankValue() + otherCard2.getRankValue()
									 + otherCard3.getRankValue() + otherCard4.getRankValue();
					this.getScoreCounter().increment(800 + sumOfRanks);
					//cardsFaceDown -= getCardsToTurnUp();
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
	
	public String getMode() {
		return "FourKindMode";
	}
}