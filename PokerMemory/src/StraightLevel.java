
	import javax.swing.ImageIcon;
	import javax.swing.JFrame;

	public class StraightLevel extends FlushLevel {

		// STRAIGHT LEVEL: The goal is to find...

		protected StraightLevel(TurnsTakenCounterLabel validTurnTime, ScoreCounterLabel scoreCounter, JFrame mainFrame) {
			super(validTurnTime, scoreCounter, mainFrame);
			this.getTurnsTakenCounter().setDifficultyModeLabel("Straight Level");
			this.setCardsToTurnUp(5);
			this.setCardsPerRow(10);
			this.setRowsPerGrid(5);
		}

		@Override
		protected void makeDeck() {
			// In Straight Level the grid consists of ...

			//back card
			ImageIcon backIcon = this.getCardIcons()[this.getTotalCardsPerDeck()];

			int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
			for(int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++) {
				cardsToAdd[i] = i;
			}

			// randomize the order of the deck
			this.randomizeIntArray(cardsToAdd);

			// make each card object
			for(int i = 0; i < cardsToAdd.length; i++) {
				// number of the card, randomized
				int num = cardsToAdd[i];
				// make the card object and add it to the panel
				String rank = cardNames[num].substring(0, 1);
				String suit = cardNames[num].substring(1, 2);
				this.getGrid().add( new Card(this, this.getCardIcons()[num], backIcon, num, rank, suit));
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
					// get the other card (which was already turned up)
					Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
					Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
					Card otherCard3 = (Card) this.getTurnedCardsBuffer().get(2);
					Card otherCard4 = (Card) this.getTurnedCardsBuffer().get(3);
					
					
					if((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && (card.getSuit().equals(otherCard3.getSuit())) && (card.getSuit().equals(otherCard4.getSuit())))  {
						// Three cards match, so remove them from the list (they will remain face up)
						this.getTurnedCardsBuffer().clear();
						int rankValue1 = card.getRankValue();
						int rankValue2 = otherCard1.getRankValue();
						int rankValue3 = otherCard2.getRankValue();
						int rankValue4 = otherCard3.getRankValue();
						int rankValue5 = otherCard4.getRankValue();
						this.getScoreCounter().increment(700 + rankValue1 + rankValue2 + rankValue3 + rankValue4 + rankValue5);
						
						
						//Method to know if is in ascendent order
						/*if((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && (card.getSuit().equals(otherCard3.getSuit())) && (card.getSuit().equals(otherCard4.getSuit())))  {
							// Three cards match, so remove them from the list (they will remain face up)
							this.getTurnedCardsBuffer().clear();
							int rankValue1 = card.getRankValue();
							int rankValue2 = otherCard1.getRankValue();
							int rankValue3 = otherCard2.getRankValue();
							int rankValue4 = otherCard3.getRankValue();
							int rankValue5 = otherCard4.getRankValue();
							this.getScoreCounter().increment(700 + rankValue1 + rankValue2 + rankValue3 + rankValue4 + rankValue5);
							
						}
						*/
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
