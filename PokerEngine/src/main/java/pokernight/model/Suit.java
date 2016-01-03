package pokernight.model;

public enum Suit {
    CLUBS("Clubs", 0x8000),
    DIAMONDS("Diamonds", 0x4000),
    HEARTS("Hearts", 0x2000),
    SPADES("Spades", 0x1000);

    private final String name;
    private final int value;

    Suit(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
