package pokernight.model;

import java.util.Arrays;

public class HandEvaluator {
	private static final int STRAIGHT_FLUSH = 1;
	private static final int FOUR_OF_A_KIND = 2;
	private static final int FULL_HOUSE = 3;
	private static final int FLUSH = 4;
	private static final int STRAIGHT = 5;
	private static final int THREE_OF_A_KIND = 6;
	private static final int TWO_PAIR = 7;
	private static final int ONE_PAIR = 8;
	private static final int HIGH_CARD = 9;

	public static int findFast(int value) {
		// println("ValuePassed: " + (/* 0xFFFFFFFF ^*/ value) )
		int u = (value + 0x00000000e91aaa35);
		// println("u1: " + u)
		// printIntBinary(0x00000000e91aaa35);
		// printIntBinary(u);
		// println("u >>>> 16: " + (u >>> 16))
		u = u ^ (u >>> 16);
		// println("u2: " + u)
		u = u + (u << 8);
		// println("u3: " + u)
		u = u ^ (u >>> 4);
		// println("u4: " + u)
		int b = ((u >>> 8) & 0x1ff);
		// println("b: " + b)
		int a = (u + (u << 2)) >>> 19;
		// println("a: " + a)
		// println("HashAdjust[b]: " + HandHashTable.hashAdjust(b.toInt))
		int r = (a ^ HandHashTable.hashAdjust[b]);
		// println("r: " + r)
		return r;
	}

	public static int evaluate5HandFast(int[] hand) {
		int c1 = hand[0];
		// println("C1:")
		// printIntBinary(c1)
		int c2 = hand[1];
		// println("C2:")
		// printIntBinary(c2)
		int c3 = hand[2];
		// println("C3:")
		// printIntBinary(c3)
		int c4 = hand[3];
		// println("C4:")
		// printIntBinary(c4)
		int c5 = hand[4];
		// println("C5:")
		// printIntBinary(c5)
		int q = (c1 | c2 | c3 | c4 | c5) >>> 16;
		// println("OR: " + (c1 | c2 | c3 | c4 | c5))
		// printIntBinary((c1 | c2 | c3 | c4 | c5))
		// println("Shifted: " + q)
		int s = UniqueArray.unique5[q];
		// println("s: " + s)
		int handValue = c1 & c2 & c3 & c4 & c5 & 0xf000;
		// println("HandValue: " + handValue)
		if (handValue != 0)
			return FlushArray.flushes[q]; // check for flushes and straight
											// flushes
		if (s != 0)
			return s; // check for straights and high card hands
		// println("Card Values: " + (c1 & 0xff) + " " + (c2 & 0xff) + " " + (c3
		// & 0xff) + " " + (c4 & 0xff) + " " + (c5 & 0xff) + " = " + ((c1 &
		// 0xff) * (c2 & 0xff) * (c3 & 0xff) * (c4 & 0xff) * (c5 & 0xff)))
		int handHashIndex = findFast((c1 & 0xff) * (c2 & 0xff) * (c3 & 0xff)
				* (c4 & 0xff) * (c5 & 0xff));
		// println("handHashIndex: " + handHashIndex)
		// println("hashValuesLength: " + HashValueTable.hashValues.length)
		int totalValue = HashValueTable.hashValues[handHashIndex];
		// println("Total Value: " + totalValue)
		return totalValue;
	}

	public static int getHandRank(int value) {
		if (value > 6185)
			return (HIGH_CARD); // 1277 high card
		if (value > 3325)
			return (ONE_PAIR); // 2860 one pair
		if (value > 2467)
			return (TWO_PAIR); // 858 two pair
		if (value > 1609)
			return (THREE_OF_A_KIND); // 858 three-kind
		if (value > 1599)
			return (STRAIGHT); // 10 straights
		if (value > 322)
			return (FLUSH); // 1277 flushes
		if (value > 166)
			return (FULL_HOUSE); // 156 full house
		if (value > 10)
			return (FOUR_OF_A_KIND); // 156 four-kind
		return (STRAIGHT_FLUSH); // 10 straight-flushes
	}

	public static String getHandRankAsString(int index) {
		switch (index) {
		case STRAIGHT_FLUSH:
			return "straight flush";
		case FOUR_OF_A_KIND:
			return "four of a kind";
		case FULL_HOUSE:
			return "full house";
		case FLUSH:
			return "flush";
		case STRAIGHT:
			return "straight";
		case THREE_OF_A_KIND:
			return "three of a kind";
		case TWO_PAIR:
			return "two pair";
		case ONE_PAIR:
			return "one pair";
		case HIGH_CARD:
			return "high card";
		}
		return "";
	}

	public static int evaluateBestFiveCardHand(Card[] hand) {
		// Get all possible 5 card hands
		//System.out.println("Hand length: " + hand.length);
		Card[] debugBestCards = new Card[5]; 
		
		int[] cardValues = new int[5];
		int bestHandValue = 8000; // worst than worst
		for (int i = 0; i < hand.length - 4; i++) {
			for (int j = i + 1; j < hand.length - 3; j++) {
				for (int k = j + 1; k < hand.length - 2; k++) {
					for (int l = k + 1; l < hand.length - 1; l++) {
						for (int m = l + 1; m < hand.length; m++) {
							cardValues[0] = hand[i].getValue();
							cardValues[1] = hand[j].getValue();
							cardValues[2] = hand[k].getValue();
							cardValues[3] = hand[l].getValue();
							cardValues[4] = hand[m].getValue();
							int returnedValue = evaluate5HandFast(cardValues);
							//System.out.println("Returned Value: " + returnedValue + " Cards: " + hand[i] + " " + hand[j] + " " + hand[k] + " " + hand[l] + " " + hand[m]);
							if (returnedValue < bestHandValue){
								bestHandValue = returnedValue;
								// debug
								debugBestCards[0] = hand[i];
								debugBestCards[1] = hand[j];
								debugBestCards[2] = hand[k];
								debugBestCards[3] = hand[l];
								debugBestCards[4] = hand[m];										
							}
						}
					}
				}
			}
		}

		System.out.println("Best Hand Value: " + bestHandValue + "\tHand name: " + HandNameTable.handNames[bestHandValue-1] + "\tCards: " + Arrays.toString(debugBestCards));
		return bestHandValue;
	}

	public static void printIntBinary(long value) {
		// TODO: Stub (needed for debug only?)
	}

}
