package pokernight.model;

public class Card {
	private final Suit suit;
	private final Rank rank;
	private final int value;
	private boolean up = true;

	Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
		this.value = rank.getValue() | suit.getValue();
	}

	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}

	public boolean getUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public int getValue() {
		return value;
	}

	public void flip() {
		up = !up;
	}
	
	public int compare(Card that) {
		// Could this be simplified by comparing this.value and that.getValue()? 
		if (this.rank.getValue() > that.getRank().getValue())
			return 1;
		else if (this.rank.getValue() < that.getRank().getValue())
			return -1;
		else {
			if (this.suit.getValue() > that.getSuit().getValue())
				return 1;
			else if (this.suit.getValue() < that.getSuit().getValue())
				return -1;
			else
				return 0;
		}
	}
	
	@Override
	public String toString() {
		return rank.toString() + "-" + suit.toString() + "-" + value + "-"
				+ (up ? "up" : "down");
	}
}
