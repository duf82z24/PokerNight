package com.pokernight.model;

import com.pokernight.service.PlayerCommunicationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pokernight.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class GameModelTexasHoldemMicroTest {
    @Mock(name = "playerCommunicationService")
    PlayerCommunicationService mockPlayerCommunicationService;
    @InjectMocks
    private GameModel gameModel;
    private PlayerModel player1;
    private PlayerModel player2;

    @Before
    public void setUp() {
        initializeTexasHoldemGame();
        MockitoAnnotations.initMocks(this);
    }

    private void initializeTexasHoldemGame() {
        DeckModel deckModel = new DeckModel();
        deckModel.shuffle();
        List<PlayerModel> players = new ArrayList<>();
        player1 = new PlayerModel("Player1", 50);
        player2 = new PlayerModel("Player2", 50);
        players.add(player1);
        players.add(player2);

        gameModel = new GameModel(deckModel, players, "/Users/cdufresne/IdeaProjects/PokerNightJavaOnly/HoldemConfig.json");
    }

    @Test
    public void testBasicGameFlow() {
        //given
        boolean withNoFoldExpected = false;
        raiseCallBetting();

        //when-then
        verifyOpeningRound();
        verifyFlopRound(withNoFoldExpected);
        verifyTurnRound();
        verifyRiverRound();
        verifyShowdown();
    }

    @Test
    public void testWinByFold() {
        //given
        boolean withFoldExpected = true;
        raiseCallBetting();

        //when-then
        verifyOpeningRound();
        when(mockPlayerCommunicationService.promptPlayerForAction(eq(player1), anyInt())).thenReturn((new PlayerAction(PlayerAction.Action.RAISE, 10)));
        when(mockPlayerCommunicationService.promptPlayerForAction(eq(player2), anyInt())).thenReturn((new PlayerAction(PlayerAction.Action.FOLD)));
        verifyFlopRound(withFoldExpected);
    }

    @Test
    public void testCompleteGame() {
        //given
        raiseCallBetting();
        raiseCallBetting();
        raiseCallBetting();
        raiseCallBetting();

        verifyResults(gameModel.playGame());

    }

    private void raiseCallBetting() {
        when(mockPlayerCommunicationService.promptPlayerForAction(eq(player1), anyInt())).thenReturn((new PlayerAction(PlayerAction.Action.RAISE, 10)));
        when(mockPlayerCommunicationService.promptPlayerForAction(eq(player2), anyInt())).thenReturn((new PlayerAction(PlayerAction.Action.CALL, 10)));
    }

    private void verifyOpeningRound() {
        //when
        gameModel.playRound(0);

        //then
        verify(mockPlayerCommunicationService, times(4)).notifyPlayersOfCardDealtToPlayer(any(PlayerModel.class), any(Card.class), eq(false));
        verify(mockPlayerCommunicationService).promptPlayerForAction(same(player1), eq(0));
        verify(mockPlayerCommunicationService).promptPlayerForAction(same(player2), eq(10));


        assertPlayersHaveProperNumberOfCards();
        int cardCount = getGridCardCount();
        assertThat("Card Grid shouldn't have any cards, cardCount: " + cardCount, cardCount == 0);
    }

    private void verifyFlopRound(boolean expectFold) {

        //when
        PlayerModel winner = gameModel.playRound(1);
        if (expectFold) {
            assertThat("expected a winner by fold", winner, notNullValue());
        }

        //then
        assertPlayersHaveProperNumberOfCards();
        int cardCount = getGridCardCount();
        assertThat("Card Grid should have three cards, cardCount: " + cardCount, cardCount == 3);
        verify(mockPlayerCommunicationService).notifyPlayersOfCardDealtToGrid(eq(3), eq(1), any(Card.class), eq(true));
        verify(mockPlayerCommunicationService).notifyPlayersOfCardDealtToGrid(eq(3), eq(2), any(Card.class), eq(true));
        verify(mockPlayerCommunicationService).notifyPlayersOfCardDealtToGrid(eq(3), eq(3), any(Card.class), eq(true));
        verify(mockPlayerCommunicationService, times(4)).promptPlayerForAction(any(PlayerModel.class), anyInt());
    }

    private void verifyTurnRound() {

        //when
        gameModel.playRound(2);

        //then
        assertPlayersHaveProperNumberOfCards();
        int cardCount = getGridCardCount();
        assertThat("Card Grid should have four cards, cardCount: " + cardCount, cardCount == 4);
        verify(mockPlayerCommunicationService).notifyPlayersOfCardDealtToGrid(eq(3), eq(4), any(Card.class), eq(true));
        verify(mockPlayerCommunicationService, times(6)).promptPlayerForAction(any(PlayerModel.class), anyInt());
    }

    private void verifyRiverRound() {

        //when
        gameModel.playRound(3);

        //then
        assertPlayersHaveProperNumberOfCards();
        int cardCount = getGridCardCount();
        assertThat("Card Grid should have five cards, cardCount: " + cardCount, cardCount == 5);
        verify(mockPlayerCommunicationService).notifyPlayersOfCardDealtToGrid(eq(3), eq(5), any(Card.class), eq(true));
        verify(mockPlayerCommunicationService, times(8)).promptPlayerForAction(any(PlayerModel.class), anyInt());
    }

    private void verifyShowdown() {
        List<PlayerModel> winningPlayers = gameModel.showdown();
        verifyResults(winningPlayers);
    }

    private void verifyResults(List<PlayerModel> winningPlayers) {
        String winningHand = gameModel.getWinningHandName();

        assertThat("winningPlayers should not be null", winningPlayers != null);
        assertThat("winningPlayer should not be empty", !winningPlayers.isEmpty());
        assertThat("winningHand should not be null", winningHand != null);


        PlayerModel aWinningPlayer = winningPlayers.get(0);
        Card[] bestHand = getBestHand(aWinningPlayer.getHand());

        int bestValue = HandEvaluator.evaluateBestFiveCardHand(bestHand);
        gameModel.getPlayers().stream()
                .forEach(player ->
                        assertThat("There should be no hands better than winning hand",
                                HandEvaluator.evaluateBestFiveCardHand(getBestHand(player.getHand())) >= bestValue));
    }

    private Card[] getBestHand(Card[] cards) {
        Card[] bestHand = new Card[7];
        bestHand[0] = gameModel.getCardGrid().getCardFromGrid(3, 1);
        bestHand[1] = gameModel.getCardGrid().getCardFromGrid(3, 2);
        bestHand[2] = gameModel.getCardGrid().getCardFromGrid(3, 3);
        bestHand[3] = gameModel.getCardGrid().getCardFromGrid(3, 4);
        bestHand[4] = gameModel.getCardGrid().getCardFromGrid(3, 5);
        bestHand[5] = cards[0];
        bestHand[6] = cards[1];
        return bestHand;
    }


    private int getGridCardCount() {
        Card[][] cards = gameModel.getCardGrid().getCurrentCards();
        int cardCount = 0;
        for (Card[] card : cards)
            for (Card aCard : card) {
                if (aCard != null) {
                    cardCount++;
                }
            }
        return cardCount;
    }

    private void assertPlayersHaveProperNumberOfCards() {
        List<PlayerModel> players = gameModel.getPlayers();
        for (PlayerModel player : players) {
            assertThat("Player should have two cards, cardCount: " + player.getHand().length, player.getHand().length == 2);
        }
    }

}
