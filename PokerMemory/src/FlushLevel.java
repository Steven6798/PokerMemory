import javax.swing.JFrame;

public class FlushLevel extends RankTrioLevel {

	// TRIO LEVEL: The goal is to find, on each turn, three cards with the same rank

	protected FlushLevel(TurnsTakenCounterLabel validTurnTime, ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Flush Level");
		this.setCardsToTurnUp(5);
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
				sortTurnedCards();
				if(PokerHand.isFlush(getTurnedCardsBuffer()) == true) {
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
	public String getMode() {
		return "FlushMode";
	}
}