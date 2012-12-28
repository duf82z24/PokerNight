package pokernight.model;

import java.util.LinkedHashSet;
import java.util.Set;

import pokernight.exception.IllegalBetException;
import pokernight.rule.SpecialCardRule;

public class PlayerModel {
	private String name;
	private float chips;
	private Set<Card> hand;

	public PlayerModel(String name, float chips) {
		this.name = name;
		this.chips = chips;
		this.hand = new LinkedHashSet<Card>();
	}

	public void receiveCard(Card card) {
		if (card != null)
			hand.add(card);
	}

	public void bet(float amount) throws IllegalBetException {
		if (chips - amount < 0)
			throw new IllegalBetException("Insufficient Chips!");
		else
			chips -= amount;
	}

	public float getChipsLeft() {
		return chips;
	}

	public Card[] getHand() {
		return hand.toArray(new Card[0]);
	}

	public String getHandAsString() {
		String handString = "";
		for (Card card : hand) {
			handString += card.toString() + " ";
		}
		return handString;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Name: " + name + " has " + chips;
	}


	public void applyAction(SpecialCardRule.CardAction action) {
		// TODO
	}

	 

}
