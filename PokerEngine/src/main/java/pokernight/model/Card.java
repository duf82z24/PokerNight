package pokernight.model;

public class Card implements Comparable<Card> {
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

    @Override
    public String toString() {
        return rank.toString() + "-" + suit.toString() + "-" + value + "-"
                + (up ? "up" : "down");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (value != card.value) return false;
        if (suit != card.suit) return false;
        return rank == card.rank;

    }

    @Override
    public int hashCode() {
        int result = suit.hashCode();
        result = 31 * result + rank.hashCode();
        result = 31 * result + value;
        return result;
    }

    @Override
    public int compareTo(Card that) {
        return this.value - that.value;
    }
}
