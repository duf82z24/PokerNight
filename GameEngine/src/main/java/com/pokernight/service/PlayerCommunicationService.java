package com.pokernight.service;

import com.pokernight.model.PlayerAction;
import com.pokernight.model.PlayerGameStateView;
import pokernight.model.Card;
import pokernight.model.PlayerModel;

import java.util.List;

/**
 * Created by cdufresne on 1/2/16.
 */
public interface PlayerCommunicationService {

    PlayerAction promptPlayerForAction(PlayerModel player, int currentBet);

    void updatePlayerViewOfGame(PlayerModel player, PlayerGameStateView playerGameStateView);

    void notifyPlayersOfCardDealtToPlayer(PlayerModel player, Card card, boolean dealFacingUp);

    void notifyPlayersOfCardDealtToGrid(int x, int y, Card card, boolean dealFacingUp);

    void notifyPlayersOfAction(PlayerModel player, PlayerAction playerAction);

    void notifyPlayersOfWinners(List<PlayerModel> winningPlayers, int pot);
}
