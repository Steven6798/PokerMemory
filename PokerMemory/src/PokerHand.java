import java.util.Vector;

public class PokerHand {
	
	public static int cardsRankSum(Vector<Card> turnedCardsBuffer) {

		int sumOfRanks = turnedCardsBuffer.get(0).getRankValue() + turnedCardsBuffer.get(1).getRankValue()
						 + turnedCardsBuffer.get(2).getRankValue() + turnedCardsBuffer.get(3).getRankValue()
						 + turnedCardsBuffer.get(4).getRankValue();
		return sumOfRanks;
	}
	
	public static boolean isFourOfAKind(Vector<Card> turnedCardsBuffer) {

		if ((turnedCardsBuffer.get(0).getRank().equals(turnedCardsBuffer.get(1).getRank()))
			&& (turnedCardsBuffer.get(1).getRank().equals(turnedCardsBuffer.get(2).getRank())) 
			&& (turnedCardsBuffer.get(2).getRank().equals(turnedCardsBuffer.get(3).getRank()))) {
			return true;	
		}
		else if ((turnedCardsBuffer.get(4).getRank().equals(turnedCardsBuffer.get(3).getRank()))
				&& (turnedCardsBuffer.get(3).getRank().equals(turnedCardsBuffer.get(2).getRank()))
				&& (turnedCardsBuffer.get(2).getRank().equals(turnedCardsBuffer.get(1).getRank()))) {
				return true;	
		}
		return false;
	}
	
	public static boolean isFullHouse(Vector<Card> turnedCardsBuffer) {

		if ((turnedCardsBuffer.get(0).getRank().equals(turnedCardsBuffer.get(1).getRank())
			&& turnedCardsBuffer.get(0).getRank().equals(turnedCardsBuffer.get(2).getRank()) 
			&& turnedCardsBuffer.get(3).getRank().equals(turnedCardsBuffer.get(4).getRank()))
			|| (turnedCardsBuffer.get(2).getRank().equals(turnedCardsBuffer.get(3).getRank())
			&& turnedCardsBuffer.get(2).getRank().equals(turnedCardsBuffer.get(4).getRank()) 
			&& turnedCardsBuffer.get(0).getRank().equals(turnedCardsBuffer.get(1).getRank()))) {
			return true;
		}
		return false;
	}
	
	public static boolean isFlush(Vector<Card> turnedCardsBuffer) {
		
		if((turnedCardsBuffer.get(0).getSuit().equals(turnedCardsBuffer.get(1).getSuit()))
			&& (turnedCardsBuffer.get(0).getSuit().equals(turnedCardsBuffer.get(2).getSuit()))
			&& (turnedCardsBuffer.get(0).getSuit().equals(turnedCardsBuffer.get(3).getSuit()))
			&& (turnedCardsBuffer.get(0).getSuit().equals(turnedCardsBuffer.get(4).getSuit()))) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isThreeOfAKind(Vector<Card> turnedCardsBuffer) {

		if ((turnedCardsBuffer.get(0).getRank().equals(turnedCardsBuffer.get(1).getRank()) 
			&& turnedCardsBuffer.get(0).getRank().equals(turnedCardsBuffer.get(2).getRank()))
			|| (turnedCardsBuffer.get(1).getRank().equals(turnedCardsBuffer.get(2).getRank()) 
			&& turnedCardsBuffer.get(1).getRank().equals(turnedCardsBuffer.get(3).getRank()))
			|| (turnedCardsBuffer.get(2).getRank().equals(turnedCardsBuffer.get(3).getRank())
			&& turnedCardsBuffer.get(2).getRank().equals(turnedCardsBuffer.get(4).getRank()))) {
			return true;
		}
		return false;
	}

	public static boolean isTwoPair(Vector<Card> turnedCardsBuffer) {

		if (turnedCardsBuffer.get(0).getRank().equals(turnedCardsBuffer.get(1).getRank())) {
			if (turnedCardsBuffer.get(2).getRank().equals(turnedCardsBuffer.get(3).getRank())) {
				return true;
			}
			else if (turnedCardsBuffer.get(3).getRank().equals(turnedCardsBuffer.get(4).getRank())) {
				return true;
			}
		}
		if (turnedCardsBuffer.get(1).getRank().equals(turnedCardsBuffer.get(2).getRank())
			&& turnedCardsBuffer.get(3).getRank().equals(turnedCardsBuffer.get(4).getRank())) {
			return true;
		}
		return false;
	}

	public static boolean isOnePair(Vector<Card> turnedCardsBuffer) {

		if (turnedCardsBuffer.get(0).getRank().equals(turnedCardsBuffer.get(1).getRank())
			|| turnedCardsBuffer.get(1).getRank().equals(turnedCardsBuffer.get(2).getRank())
			|| turnedCardsBuffer.get(2).getRank().equals(turnedCardsBuffer.get(3).getRank())
			|| turnedCardsBuffer.get(3).getRank().equals(turnedCardsBuffer.get(4).getRank())) {
			return true;
		}
		return false;
	}
	
	public static int checkHand(Vector<Card> turnedCardsBuffer) {
		
		// Four of a Kind
		if (PokerHand.isFourOfAKind(turnedCardsBuffer)) {
			return 3500;
		}
		
		// Full House
		else if (PokerHand.isFullHouse(turnedCardsBuffer)) {
			return 2000; 
		}
		
		// Flush
		else if (PokerHand.isFlush(turnedCardsBuffer)) {
			return 700 + PokerHand.cardsRankSum(turnedCardsBuffer);
		}
		
		// Three of a Kind
		else if (PokerHand.isThreeOfAKind(turnedCardsBuffer)) {
			return 500; 
		}
		
		// Two Pair
		else if (PokerHand.isTwoPair(turnedCardsBuffer)) {
			return 200;
		}
		
		// One Pair
		else if (PokerHand.isOnePair(turnedCardsBuffer)) {
			return 100 + PokerHand.cardsRankSum(turnedCardsBuffer);
		}
		
		// None of the above
		else {
			return turnedCardsBuffer.get(4).getRankValue();
		}
	}	
}