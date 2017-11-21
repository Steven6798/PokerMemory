import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ComboLevel extends FlushLevel {

	// TRIO LEVEL: The goal is to find, on each turn, three cards with the same rank

	protected ComboLevel(TurnsTakenCounterLabel validTurnTime, ScoreCounterLabel scoreCounter, JFrame mainFrame) {
		super(validTurnTime, scoreCounter, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Combo Level");
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
				//sortTurnedCards();
				// get the other card (which was already turned up)
				//int score = PokerHand.checkHand(getTurnedCardsBuffer());
				//setScore(score, card, getPokerHand(score));
				this.getScoreCounter().increment(900);
			}
			return true;
		}
		return false;
	}

	public String getMode() {
		return "ComboMode";
	}
}