package com.pokernight.rule;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameRules {
    public final String name;
    public final String basis;
    public final int smallBlindOrAnteAmount;
    public final int rounds;
    public final PlayerCardRule playerCardRule;
    public final CommunityCardRule communityCardRule;
    public final WinCondtion[] winCondtion;
    public final SpecialCardRule[] specialCardRules;

    private Logger logger = LoggerFactory.getLogger(GameRules.class);

    GameRules(
            @JsonProperty("name") String name,
            @JsonProperty("basis") String basis,
            @JsonProperty("smallBlindOrAnteAmount") int smallBlindOrAnteAmount,
            @JsonProperty("rounds") int rounds,
            @JsonProperty("playerCardRule") PlayerCardRule playerCardRule,
            @JsonProperty("communityCardRule") CommunityCardRule communityCardRule,
            @JsonProperty("winCondition") WinCondtion[] winCondtion,
            @JsonProperty("specialCardRules") SpecialCardRule[] specialCardRules) {
        this.name = name;
        this.basis = basis;
        this.smallBlindOrAnteAmount = smallBlindOrAnteAmount;
        this.rounds = rounds;
        this.playerCardRule = playerCardRule;
        this.communityCardRule = communityCardRule;
        this.winCondtion = winCondtion;
        this.specialCardRules = specialCardRules;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error("Unable to write JSON", e);
        }

        return "GameRules [name=" + name + ", basis=" + basis + ", rounds=" + rounds
                + ", playerCardRule=" + playerCardRule
                + ", communityCardRule=" + communityCardRule + ", WinCondtion="
                + Arrays.toString(winCondtion) + ", specialCardRules="
                + Arrays.toString(specialCardRules) + "]";
    }
}
