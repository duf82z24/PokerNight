package com.pokernight.rule.basis;

import com.pokernight.exception.GameEngineException;
import com.pokernight.model.PlayerAction;
import com.pokernight.rule.GameRules;
import com.pokernight.service.PlayerCommunicationService;
import pokernight.model.PlayerModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cdufresne on 1/2/16.
 */
public class HoldEmBasis extends GameBasis {

    private int smallblind;
    private final int bigBlind;

    public HoldEmBasis(GameRules gameRules) {
        super(gameRules);
        smallblind = gameRules.smallBlindOrAnteAmount;
        bigBlind = smallblind * 2;
    }

    @Override
    int postBlinds(List<PlayerModel> players, PlayerCommunicationService playerCommunicationService) {


        List<PlayerModel> activePlayers = players.stream().filter(player -> !player.isSittingOut()).collect(Collectors.toList());
        if (activePlayers.size() < 2) {
            throw new GameEngineException("Not enough players");
        }

        boolean isHeadsUp = activePlayers.size() == 2;

        postBlinds(activePlayers, playerCommunicationService, isHeadsUp);
        return smallblind + bigBlind;
    }

    private boolean postBlinds(List<PlayerModel> activePlayers, PlayerCommunicationService playerCommunicationService, boolean isHeadsUp) {
        int firstBlind;
        int secondBlind;
        if (isHeadsUp) {
            firstBlind = bigBlind;
            secondBlind = smallblind;
        } else {
            firstBlind = smallblind;
            secondBlind = bigBlind;
        }

        int i = 0;

        while (!(postBlind(activePlayers.get(i), playerCommunicationService, firstBlind)) && (i < activePlayers.size() - 1)) {
            i++;
        }
        i++;
        while (!(postBlind(activePlayers.get(i), playerCommunicationService, secondBlind)) && (i < activePlayers.size())) {
            i++;
        }

        return i < activePlayers.size();
    }

    private boolean postBlind(PlayerModel player, PlayerCommunicationService playerCommunicationService, int blind) {
        if (player.isSittingOut()) {
            return false;
        } else if (player.getChipsLeft() >= blind) {
            player.bet(blind);
            playerCommunicationService.notifyPlayersOfAction(player, new PlayerAction(PlayerAction.Action.ANTE, blind));
            return true;
        } else {
            player.setSittingOut(true);
            playerCommunicationService.notifyPlayersOfDeactivatedPlayer(player);
            return false;
        }


    }
}
