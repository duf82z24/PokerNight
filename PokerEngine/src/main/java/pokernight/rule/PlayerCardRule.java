package pokernight.rule;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerCardRule {
    public final String[] numberOfCardsPerDeal; // Required
    public final String[] numberOfCardsDrawPerRound; // Optional
    public final String[] numberOfCardsDroppedPerRound; // Optional

    PlayerCardRule(
            @JsonProperty("numberOfCardsPerDeal") String[] numberOfCardsPerDeal,
            @JsonProperty("numberOfCardsDrawPerRound") String[] numberOfCardsDrawPerRound,
            @JsonProperty("numberOfCardsDroppedPerRound") String[] numberOfCardsDroppedPerRound) {
        this.numberOfCardsPerDeal = numberOfCardsPerDeal;
        this.numberOfCardsDrawPerRound = numberOfCardsDrawPerRound;
        this.numberOfCardsDroppedPerRound = numberOfCardsDroppedPerRound;
    }

    public int[] getNumberToDealForRound(int round) {
        int[] numToDeal = new int[]{0, 0};
        if (round < numberOfCardsPerDeal.length) {
            String[] tokens = numberOfCardsPerDeal[round].split(",");
            for (String roundToken : tokens) {
                String[] dealTokens = roundToken.split("-");

                int number = Integer.parseInt(dealTokens[0]);
                if (number != 0) {
                    String condition = dealTokens[1];
                    if ("d".equals(condition))
                        numToDeal[0] += number;
                    else if ("u".equals(condition))
                        numToDeal[1] += number;
                    else
                        System.out
                                .println("A condtion other than 'd' or 'u' found in player Rules!");
                }
            }
        }
        return numToDeal;
    }

    @Override
    public String toString() {
        return "PlayerCardRule [numberOfCardsPerDeal="
                + Arrays.toString(numberOfCardsPerDeal)
                + ", numberOfCardsDrawPerRound="
                + Arrays.toString(numberOfCardsDrawPerRound)
                + ", numberOfCardsDroppedPerRound="
                + Arrays.toString(numberOfCardsDroppedPerRound) + "]";
    }
}