package com.pokernight.rule.basis;

import com.pokernight.rule.GameRules;
import com.pokernight.service.PlayerCommunicationService;
import com.pokernight.model.PlayerModel;

import java.util.List;

/**
 * Created by cdufresne on 1/2/16.
 */
public abstract class GameBasis {

    protected final GameRules gameRules;

    public GameBasis(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    abstract int postBlinds(List<PlayerModel> players, PlayerCommunicationService playerCommunicationService);
}
