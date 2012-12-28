package pokernight.model;

import pokernight.exception.IllegalGridLocationException;

public class CardGridModel {
	private final int width;
	private final int height;
	private Card[][] cardGrid;

	public CardGridModel(int width, int height) {
		this.width = width;
		this.height = height;
		this.cardGrid = new Card[width][height];
		System.out.println("Created Card Grid");
	}

	public boolean isValidLocation(int x, int y) {
		if (x < 0 || x > width || y < 0 || y > height)
			return false;
		else
			return true;
	}

	public void dealCardToGrid(int x, int y, Card card, boolean up)
			throws IllegalGridLocationException {
		if (!isValidLocation(x, y))
			throw new IllegalGridLocationException("Illegal Grid Position");
		cardGrid[x][y] = card;
		cardGrid[x][y].setUp(up);
	}

	public void flipCard(int x, int y) throws IllegalGridLocationException {
		if (!isValidLocation(x, y))
			throw new IllegalGridLocationException("Illegal Grid Position");
		if (cardGrid[x][y] != null)
			cardGrid[x][y].flip();
	}

	public Card[][] getCurrentCards() {
		return cardGrid;
	}

	public String getCurrentStateAsString() {
		String currentGridState = "";

		for (Card[] row: cardGrid) {
			for (Card card: row) {
				String cardString = card != null ? card.toString() : "Empty";
				String tabs = card != null ? "\t" : "\t\t\t";
				currentGridState = currentGridState + cardString + tabs;
			}
			currentGridState = currentGridState + "\n";
		}

		return currentGridState;
	}

	public Card getCardFromGrid(int x, int y)
			throws IllegalGridLocationException {
		if (!isValidLocation(x, y))
			throw new IllegalGridLocationException("Illegal Grid Position");
		return cardGrid[x][y];
	}

	@Override
	public String toString() {
		return getCurrentStateAsString();
	}
}