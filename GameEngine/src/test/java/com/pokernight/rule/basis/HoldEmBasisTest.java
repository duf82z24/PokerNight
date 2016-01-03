package com.pokernight.rule.basis;

import com.pokernight.exception.GameEngineException;
import com.pokernight.model.PlayerAction;
import com.pokernight.rule.GameRules;
import com.pokernight.rule.RuleFileParser;
import com.pokernight.service.PlayerCommunicationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pokernight.model.PlayerModel;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by cdufresne on 1/2/16.
 */
public class HoldEmBasisTest {

    @Mock(name = "playerCommunicationService")
    PlayerCommunicationService mockPlayerCommunicationService;
    private PlayerModel player1;
    private PlayerModel player2;
    private PlayerModel player3;

    private List<PlayerModel> players;
    private HoldEmBasis holdEmBasis;

    @Before
    public void setUp() {
        players = new ArrayList<>();
        player1 = new PlayerModel("Player1", 50);
        player2 = new PlayerModel("Player2", 50);
        player3 = new PlayerModel("Player3", 50);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        MockitoAnnotations.initMocks(this);
        ClassLoader classLoader = getClass().getClassLoader();
        GameRules gameRules = RuleFileParser.parse(classLoader.getResource("HoldemConfig.json").getPath());
        holdEmBasis = new HoldEmBasis(gameRules);
    }

    @Test
    public void testPostBlinds() {

        holdEmBasis.postBlinds(players, mockPlayerCommunicationService);
        verify(mockPlayerCommunicationService).notifyPlayersOfAction(same(player1), eq(new PlayerAction(PlayerAction.Action.ANTE, 5)));
        verify(mockPlayerCommunicationService).notifyPlayersOfAction(same(player2), eq(new PlayerAction(PlayerAction.Action.ANTE, 10)));
        assertThat("Player 1 should have 45 chips left", player1.getChipsLeft(), equalTo(45));
        assertThat("Player 2 should have 40 chips left", player2.getChipsLeft(), equalTo(40));
        assertThat("Player 3 should have 50 chips left", player3.getChipsLeft(), equalTo(50));

    }

    @Test
    public void testHeadsUp() {
        player1.setSittingOut(true);

        holdEmBasis.postBlinds(players, mockPlayerCommunicationService);
        verify(mockPlayerCommunicationService).notifyPlayersOfAction(same(player3), eq(new PlayerAction(PlayerAction.Action.ANTE, 5)));
        verify(mockPlayerCommunicationService).notifyPlayersOfAction(same(player2), eq(new PlayerAction(PlayerAction.Action.ANTE, 10)));
        assertThat("Player 1 should have 45 chips left", player1.getChipsLeft(), equalTo(50));
        assertThat("Player 2 should have 40 chips left", player2.getChipsLeft(), equalTo(40));
        assertThat("Player 3 should have 50 chips left", player3.getChipsLeft(), equalTo(45));
    }


    //<editor-fold desc="negative>
    @Test
    public void testTooFewPlayers() {
        player1.setSittingOut(true);
        player2.setSittingOut(true);

        try {
            holdEmBasis.postBlinds(players, mockPlayerCommunicationService);
            fail("expected exception to be thrown");
        } catch (GameEngineException gee) {

        }


    }

    //</editor-fold>
}