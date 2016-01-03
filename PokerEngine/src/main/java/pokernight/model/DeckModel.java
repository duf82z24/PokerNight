package pokernight.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DeckModel {
    private Set<Card> deck;

    public DeckModel() {
        deck = new LinkedHashSet<>();
        deck.add(new Card(Suit.CLUBS, Rank.TWO));
        deck.add(new Card(Suit.CLUBS, Rank.THREE));
        deck.add(new Card(Suit.CLUBS, Rank.FOUR));
        deck.add(new Card(Suit.CLUBS, Rank.FIVE));
        deck.add(new Card(Suit.CLUBS, Rank.SIX));
        deck.add(new Card(Suit.CLUBS, Rank.SEVEN));
        deck.add(new Card(Suit.CLUBS, Rank.EIGHT));
        deck.add(new Card(Suit.CLUBS, Rank.NINE));
        deck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.add(new Card(Suit.CLUBS, Rank.JACK));
        deck.add(new Card(Suit.CLUBS, Rank.QUEEN));
        deck.add(new Card(Suit.CLUBS, Rank.KING));
        deck.add(new Card(Suit.CLUBS, Rank.ACE));
        deck.add(new Card(Suit.DIAMONDS, Rank.TWO));
        deck.add(new Card(Suit.DIAMONDS, Rank.THREE));
        deck.add(new Card(Suit.DIAMONDS, Rank.FOUR));
        deck.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        deck.add(new Card(Suit.DIAMONDS, Rank.SIX));
        deck.add(new Card(Suit.DIAMONDS, Rank.SEVEN));
        deck.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
        deck.add(new Card(Suit.DIAMONDS, Rank.NINE));
        deck.add(new Card(Suit.DIAMONDS, Rank.TEN));
        deck.add(new Card(Suit.DIAMONDS, Rank.JACK));
        deck.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        deck.add(new Card(Suit.DIAMONDS, Rank.KING));
        deck.add(new Card(Suit.DIAMONDS, Rank.ACE));
        deck.add(new Card(Suit.HEARTS, Rank.TWO));
        deck.add(new Card(Suit.HEARTS, Rank.THREE));
        deck.add(new Card(Suit.HEARTS, Rank.FOUR));
        deck.add(new Card(Suit.HEARTS, Rank.FIVE));
        deck.add(new Card(Suit.HEARTS, Rank.SIX));
        deck.add(new Card(Suit.HEARTS, Rank.SEVEN));
        deck.add(new Card(Suit.HEARTS, Rank.EIGHT));
        deck.add(new Card(Suit.HEARTS, Rank.NINE));
        deck.add(new Card(Suit.HEARTS, Rank.TEN));
        deck.add(new Card(Suit.HEARTS, Rank.JACK));
        deck.add(new Card(Suit.HEARTS, Rank.QUEEN));
        deck.add(new Card(Suit.HEARTS, Rank.KING));
        deck.add(new Card(Suit.HEARTS, Rank.ACE));
        deck.add(new Card(Suit.SPADES, Rank.TWO));
        deck.add(new Card(Suit.SPADES, Rank.THREE));
        deck.add(new Card(Suit.SPADES, Rank.FOUR));
        deck.add(new Card(Suit.SPADES, Rank.FIVE));
        deck.add(new Card(Suit.SPADES, Rank.SIX));
        deck.add(new Card(Suit.SPADES, Rank.SEVEN));
        deck.add(new Card(Suit.SPADES, Rank.EIGHT));
        deck.add(new Card(Suit.SPADES, Rank.NINE));
        deck.add(new Card(Suit.SPADES, Rank.TEN));
        deck.add(new Card(Suit.SPADES, Rank.JACK));
        deck.add(new Card(Suit.SPADES, Rank.QUEEN));
        deck.add(new Card(Suit.SPADES, Rank.KING));
        deck.add(new Card(Suit.SPADES, Rank.ACE));
    }

    public void shuffle() {
        List<Card> tempList = new ArrayList<Card>(deck);
        Random rnd = new Random(System.currentTimeMillis());
        Collections.shuffle(tempList, rnd);
        deck.clear();
        deck.addAll(tempList);
    }

    public Card drawCard() {
        Card drawnCard = null;
        if (!deck.isEmpty()) {
            Iterator<Card> deckIter = deck.iterator();
            drawnCard = deckIter.next();
            deckIter.remove();
        }
        return drawnCard;
    }

    public void returnCard(Card card) {
        deck.add(card);
    }

    public void returnCards(Collection<Card> cards) {
        deck.addAll(cards);
    }

    public void addJoker(Suit suit) {
        deck.add(new Card(suit, Rank.JOKER));
    }

    public void removeJoker(Suit suit) {
        for (Card card : deck) {
            if (card.getRank() == Rank.JOKER && card.getSuit() == suit) {
                deck.remove(card);
            }
        }
    }

    public void removeAllJokers() {
        for (Card card : deck) {
            if (card.getRank() == Rank.JOKER) {
                deck.remove(card);
            }
        }
    }

    @Override
    public String toString() {
        return deck.toString();
    }
}
