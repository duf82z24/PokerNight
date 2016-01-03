package pokernight.rule;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameRules {
    public final String name;                            // Required
    public final String basis;                            // Required
    public final int rounds;                            // Required
    public final PlayerCardRule playerCardRule;        // Required
    public final CommunityCardRule communityCardRule;    // Optional
    public final WinCondtion[] winCondtion;            // Required
    public final SpecialCardRule[] specialCardRules;    // Optional

    GameRules(
            @JsonProperty("name") String name,
            @JsonProperty("basis") String basis,
            @JsonProperty("rounds") int rounds,
            @JsonProperty("playerCardRule") PlayerCardRule playerCardRule,
            @JsonProperty("communityCardRule") CommunityCardRule communityCardRule,
            @JsonProperty("winCondition") WinCondtion[] winCondtion,
            @JsonProperty("specialCardRules") SpecialCardRule[] specialCardRules) {
        this.name = name;
        this.basis = basis;
        this.rounds = rounds;
        this.playerCardRule = playerCardRule;
        this.communityCardRule = communityCardRule;
        this.winCondtion = winCondtion;
        this.specialCardRules = specialCardRules;
    }

    @Override
    public String toString() {
        return "GameRules [name=" + name + ", basis=" + basis + ", rounds=" + rounds
                + ", playerCardRule=" + playerCardRule
                + ", communityCardRule=" + communityCardRule + ", WinCondtion="
                + Arrays.toString(winCondtion) + ", specialCardRules="
                + Arrays.toString(specialCardRules) + "]";
    }
}
