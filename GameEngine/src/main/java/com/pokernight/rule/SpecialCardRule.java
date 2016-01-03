package com.pokernight.rule;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpecialCardRule {

    public enum CardAction {
        WILD("Wild"), KILL("Kill"), BUY("Buy");

        private String name;

        CardAction(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public final CardAction cardAction; // Required
    public final RuleCard[] card; // Required
    public final float amount; // Optional

    SpecialCardRule(@JsonProperty("cardAction") String cardAction,
                    @JsonProperty("card") RuleCard[] card,
                    @JsonProperty("amount") float amount) {
        this.cardAction = CardAction.valueOf(cardAction.toUpperCase());
        this.card = card;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SpecialCardRule [cardAction=" + cardAction + ", card="
                + Arrays.toString(card) + ", amount=" + amount + "]";
    }
}
