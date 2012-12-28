package pokernight.rule;

import pokernight.exception.RuleParseException;
import pokernight.model.Card;
import pokernight.model.Suit;
import pokernight.model.Rank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleCard {
	public enum CardType {
		SUIT, RANK, CARD;
	}
	
	public final Suit suit; // Optional
	public final Rank rank; // Optional
	private final CardType type;

	RuleCard(@JsonProperty("Suit") String suit,
			@JsonProperty("Rank") String rank) throws RuleParseException {

		if (suit == null && rank != null) {
			// Specific rank, any suit
			this.suit = null;
			this.rank = Rank.valueOf(rank.toUpperCase());
			this.type = CardType.RANK;
		} else if (suit != null && rank == null) {
			// Specific suit, any rank
			this.suit = Suit.valueOf(suit.toUpperCase());
			this.rank = null;
			this.type = CardType.SUIT;
		} else if (suit != null && rank != null) {
			// Specific card
			this.suit = Suit.valueOf(suit.toUpperCase());
			this.rank = Rank.valueOf(rank.toUpperCase());
			this.type = CardType.CARD;
		} else {
			// Both suit and rank were null, indicates invalid rules file
			throw new RuleParseException("SpecialCardRule.RuleCard: At least one of Rank or Suit must be defined");
		}
	}
	
	public boolean compareTo(Card card){
		// Returns true if this rule should apply to this card
		switch (type) {
			case SUIT:
				return (this.suit == card.getSuit());
			case RANK:
				return (this.rank == card.getRank());
			case CARD:
				return (this.suit == card.getSuit() && this.rank == card.getRank());
		}
		// Not really necessary, just here to shut Eclipse up:
		return false;
			
	}

	@Override
	public String toString() {
		return "TestCard [Rank=" + rank + ", Suit=" + suit + "]";
	}
}
