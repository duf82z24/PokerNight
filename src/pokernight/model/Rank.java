package pokernight.model;

public enum Rank {
	TWO ("Two", 0x10002),
	THREE ("Three", 0x20003),
	FOUR ("Four", 0x40005),
	FIVE ("Five", 0x80007),
	SIX ("Six", 0x10000B),
	SEVEN ("Seven", 0x20000D),
	EIGHT ("Eight", 0x400011),
	NINE ("Nine", 0x800013),
	TEN ("Ten", 0x1000017),
	JACK ("Jack", 0x200001D),
	QUEEN ("Queen", 0x400001F),
	KING ("King", 0x8000025),
	ACE ("Ace", 0x10000029),
	JOKER ("Joker", 1);
	
	private final String name;
	private final int value;

	Rank(String name, int value) {
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
