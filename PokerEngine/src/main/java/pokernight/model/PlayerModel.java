package pokernight.model;

import java.util.LinkedHashSet;
import java.util.Set;

import pokernight.exception.IllegalBetException;

public class PlayerModel {
    private String name;
    private int chips;
    private Set<Card> hand;
    private boolean isFolded = false;
    private boolean sittingOut = false;

    public PlayerModel(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.hand = new LinkedHashSet<Card>();
    }

    public void receiveCard(Card card) {
        if (card != null)
            hand.add(card);
    }

    public void bet(int amount) throws IllegalBetException {
        if (chips - amount < 0)
            throw new IllegalBetException("Insufficient Chips!");
        else
            chips -= amount;
    }

    public int getChipsLeft() {
        return chips;
    }

    public Card[] getHand() {
        return hand.toArray(new Card[hand.size()]);
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

    public boolean isFolded() {
        return isFolded;
    }

    public void setFolded(boolean folded) {
        isFolded = folded;
    }

    public boolean isSittingOut() {
        return sittingOut;
    }

    public void setSittingOut(boolean sittingOut) {
        this.sittingOut = sittingOut;
    }
}
