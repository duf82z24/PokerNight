package pokernight.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.fail;

/**
 * Created by cdufresne on 12/29/15.
 */
public class HandEvaluatorMicroTest {

    @Test
    public void rankBetterHandsHigherThanWorseHands() {
        Card[] hand1 = new Card[5];
        Card[] hand2 = new Card[5];

        hand1[0] = new Card(Suit.CLUBS, Rank.TWO);
        hand1[1] = new Card(Suit.CLUBS, Rank.THREE);
        hand1[2] = new Card(Suit.CLUBS, Rank.FOUR);
        hand1[3] = new Card(Suit.CLUBS, Rank.FIVE);
        hand1[4] = new Card(Suit.DIAMONDS, Rank.SEVEN);

        hand2[0] = new Card(Suit.SPADES, Rank.ACE);
        hand2[1] = new Card(Suit.SPADES, Rank.KING);
        hand2[2] = new Card(Suit.SPADES, Rank.QUEEN);
        hand2[3] = new Card(Suit.SPADES, Rank.JACK);
        hand2[4] = new Card(Suit.SPADES, Rank.TEN);

        int hand1Value = HandEvaluator.evaluateBestFiveCardHand(hand1);
        int hand2Value = HandEvaluator.evaluateBestFiveCardHand(hand2);

        assertThat("Hand 2 rank should be greater (lower value) than hand 1", hand2Value < hand1Value);
    }
}
