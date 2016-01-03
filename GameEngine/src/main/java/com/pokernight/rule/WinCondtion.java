package com.pokernight.rule;

import pokernight.model.Rank;
import pokernight.model.Suit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WinCondtion {

    public enum Condition {
        HIGHHAND("HighHand"), LOWBALL("LowBall"), BACKTOBACK("BackToBack"), SUITHIGHCARD(
                "SuitHighCard"), SUITLOWCARD("SuitLowCard");

        private String name;

        Condition(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public final Condition condition; // Required
    public final int potPercentage; // Required
    public final Suit suit; // Optional
    public final Rank rank; // Optional
    public final boolean requiredDown; // Optional

    WinCondtion(@JsonProperty("condition") String condition,
                @JsonProperty("potPercentage") int potPercentage,
                @JsonProperty("Suit") String suit,
                @JsonProperty("Rank") String rank,
                @JsonProperty("RequiredDown") boolean requiredDown) {
        this.condition = Condition.valueOf(condition.toUpperCase());
        this.potPercentage = potPercentage;
        // Suit is optional, might be null
        if (suit != null)
            this.suit = Suit.valueOf(suit.toUpperCase());
        else
            this.suit = null;
        // Rank is optional, might be null
        if (rank != null)
            this.rank = Rank.valueOf(rank.toUpperCase());
        else
            this.rank = null;
        this.requiredDown = requiredDown;

    }

    @Override
    public String toString() {
        return "WinCondtion [condition=" + condition + ", suit=" + suit
                + ", rank=" + rank + ", requiredDown=" + requiredDown
                + ", potPercentage=" + potPercentage + "]";
    }

}
