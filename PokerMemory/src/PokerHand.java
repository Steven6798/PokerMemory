/**
 * This class classify the turned up cards in a specific poker-hand.
 * 
 * @author RUMHackers.java
 */

import java.util.Vector;

public class PokerHand {
	
	/**
	 * Get the rank value of the five cards and calculates the sum.
	 * 
	 * @param turnedCardsBuffer.
	 * @return the sum of the ranks.
	 */
	public static int cardsRankSum(Vector<Card> turnedCardsBuffer) {

		int sumOfRanks = turnedCardsBuffer.get(0).getRankValue() + turnedCardsBuffer.get(1).getRankValue()
						 + turnedCardsBuffer.get(2).getRankValue() + turnedCardsBuffer.get(3).getRankValue()
						 + turnedCardsBuffer.get(4).getRankValue();
		return sumOfRanks;
	}
	
	/**
	 * Check if the hand is a Straight-Flush poker hand.
	 * 
	 * @param turnedCardsBuffer.
	 * @return true if the hand is a Straight-Flush poker hand.
	 */
	public static boolean isStraightFlush(Vector<Card> turnedCardsBuffer) {

		if (isStraight(turnedCardsBuffer) == true && isFlush(turnedCardsBuffer) == true) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the hand is a Four of a Kind poker hand.
	 * 
	 * @param turnedCardsBuffer.
	 * @return true if the hand is a Four of a Kind poker hand.
	 */
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
	
	/**
	 * Check if the hand is a Full House poker hand.
	 * 
	 * @param turnedCardsBuffer.
	 * @return true if the hand is a Full House poker hand.
	 */
	public static boolean isFullHouse(Vector<Card> turnedCardsBuffer) {

		if (isThreeOfAKind(turnedCardsBuffer) == true && isOnePair(turnedCardsBuffer) == true) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the hand is a Flush poker hand.
	 * 
	 * @param turnedCardsBuffer.
	 * @return true if the hand is a Flush poker hand.
	 */
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
	
	/**
	 * Check if the hand is a Straight poker hand.
	 * 
	 * @param turnedCardsBuffer.
	 * @return true if the hand is a Straight poker hand.
	 */
	public static boolean isStraight(Vector<Card> turnedCardsBuffer) {
		
		if(((turnedCardsBuffer.get(0).getRankValue() == (turnedCardsBuffer.get(1).getRankValue() - 1))
			&& (turnedCardsBuffer.get(1).getRankValue() == (turnedCardsBuffer.get(2).getRankValue() - 1))
			&& (turnedCardsBuffer.get(2).getRankValue() == (turnedCardsBuffer.get(3).getRankValue() - 1))
			&& (turnedCardsBuffer.get(3).getRankValue() == (turnedCardsBuffer.get(4).getRankValue() - 1)))) {
			return true;
		}
		else if(((turnedCardsBuffer.get(0).getRankValue() == (turnedCardsBuffer.get(1).getRankValue() - 1))
				&& (turnedCardsBuffer.get(1).getRankValue() == (turnedCardsBuffer.get(2).getRankValue() - 1))
				&& (turnedCardsBuffer.get(2).getRankValue() == (turnedCardsBuffer.get(3).getRankValue() - 1))
				&& (turnedCardsBuffer.get(3).getRankValue() == (turnedCardsBuffer.get(4).getRankValue() - 7)))) {
			if(turnedCardsBuffer.get(4).getRank().equals("a")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if the hand is a Three of a Kind poker hand.
	 * 
	 * @param turnedCardsBuffer.
	 * @return true if the hand is a Three of a Kind poker hand.
	 */
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
	
	/**
	 * Check if the hand is a Two Pair poker hand.
	 * 
	 * @param turnedCardsBuffer.
	 * @return true if the hand is a Two Pair poker hand.
	 */
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
	
	/**
	 * Check if the hand is a One Pair poker hand.
	 * 
	 * @param turnedCardsBuffer.
	 * @return true if the hand is a One Pair poker hand.
	 */
	public static boolean isOnePair(Vector<Card> turnedCardsBuffer) {

		if (turnedCardsBuffer.get(0).getRank().equals(turnedCardsBuffer.get(1).getRank())
			|| turnedCardsBuffer.get(1).getRank().equals(turnedCardsBuffer.get(2).getRank())
			|| turnedCardsBuffer.get(2).getRank().equals(turnedCardsBuffer.get(3).getRank())
			|| turnedCardsBuffer.get(3).getRank().equals(turnedCardsBuffer.get(4).getRank())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the hand have more than 1 Suit.
	 * 
	 * @param turnedCardsBuffer.
	 * @return true if the hand have more than 1 Suit.
	 */
	public static boolean isMoreThan1Suit(Vector<Card> turnedCardsBuffer) {
		
		int counter = 0;
		for (int i = 0; i < turnedCardsBuffer.size() -2; i++) {
			if(turnedCardsBuffer.get(i).getSuit().equals(turnedCardsBuffer.get(i+1).getSuit())) {
				counter++;
				if(counter == 4) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Set a certain score depending of the poker-hand.
	 * 
	 * @param turnedCardsBuffer.
	 * @return the score of the hand.
	 */
	public static int checkHand(Vector<Card> turnedCardsBuffer) {
		
		// Straight Flush
		if (isStraightFlush(turnedCardsBuffer)) {
			return 4500 + cardsRankSum(turnedCardsBuffer);
		}
		
		// Four of a Kind
		if (isFourOfAKind(turnedCardsBuffer)) {
			return 4000 + cardsRankSum(turnedCardsBuffer);
		}
		
		// Full House
		else if (isFullHouse(turnedCardsBuffer)) {
			return 3500 + cardsRankSum(turnedCardsBuffer);
		}
		
		// Straight
		else if (isStraight(turnedCardsBuffer) && isMoreThan1Suit(turnedCardsBuffer)) {
			return 1000 + (100 * turnedCardsBuffer.get(4).getRankValue()); 
		}
		
		// Flush
		else if (isFlush(turnedCardsBuffer)) {
			return 700 + cardsRankSum(turnedCardsBuffer);
		}
		
		// Three of a Kind
		else if (isThreeOfAKind(turnedCardsBuffer)) {
			return 500 + cardsRankSum(turnedCardsBuffer);
		}
		
		// Two Pair
		else if (isTwoPair(turnedCardsBuffer)) {
			return 200 + cardsRankSum(turnedCardsBuffer);
		}
		
		// One Pair
		else if (isOnePair(turnedCardsBuffer)) {
			return 100 + cardsRankSum(turnedCardsBuffer);
		}
		
		// None of the above
		else {
			return turnedCardsBuffer.get(4).getRankValue();
		}
	}	
}