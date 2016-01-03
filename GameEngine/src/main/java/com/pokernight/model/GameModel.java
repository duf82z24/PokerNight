package com.pokernight.model;

import java.util.*;
import java.util.stream.Collectors;
import javax.inject.Inject;

import com.pokernight.service.PlayerCommunicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pokernight.exception.IllegalBetException;
import com.pokernight.rule.*;
import pokernight.model.*;

import static com.pokernight.model.PlayerAction.Action.*;

public class GameModel {
    private Logger logger = LoggerFactory.getLogger(GameModel.class);

    private CardGridModel cardGrid;
    private Card[] wilds = null;
    private DeckModel deck;
    private List<PlayerModel> players;
    private GameRules gameRules;
    private PlayerCardRule playerCardRule;
    private CommunityCardRule communityCardRule;
    private SpecialCardRule[] specialCardRules;
    private WinCondtion[] winConditions;
    private int numberOfRounds;
    private String winningHandName;
    private int playersRemaining;
    private int pot = 0;
    //private String playerState = "";
    //private String state = "";

    @Inject
    private PlayerCommunicationService playerCommunicationService;

    public GameModel(DeckModel deck, List<PlayerModel> players, String ruleFile) {
        initializeRules(ruleFile);
        cardGrid = new CardGridModel(7, 7);
        this.deck = deck;
        this.players = players;
        playersRemaining = players.size();
    }

    public void setPlayerCommunicationService(PlayerCommunicationService playerCommunicationService) {
        this.playerCommunicationService = playerCommunicationService;
    }

    protected PlayerCommunicationService getPlayerCommunicationService() {
        return playerCommunicationService;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public CardGridModel getCardGrid() {
        return cardGrid;
    }

    public String getWinningHandName() {
        return winningHandName;
    }

    private void initializeRules(String ruleFile) {
        gameRules = RuleFileParser.parse(ruleFile);
        logger.debug("Game Rules Parsed Successfully");
        playerCardRule = gameRules.playerCardRule;
        initializeCommunityCardRules();
        initializeSpecialCardRules();
        winConditions = gameRules.winCondtion;
        numberOfRounds = gameRules.rounds;
    }

    private void initializeCommunityCardRules() {
        if (gameRules.communityCardRule != null) {
            communityCardRule = gameRules.communityCardRule;
            logger.debug("Community Card Rules: " + communityCardRule.toString());
        }
    }

    private void initializeSpecialCardRules() {
        if (gameRules.specialCardRules != null) {
            specialCardRules = gameRules.specialCardRules;
            if (logger.isDebugEnabled()) {
                logger.debug("Special Card Rules: ");
                for (SpecialCardRule specialCardRule : specialCardRules)
                    logger.debug(specialCardRule.toString());
            }
        }
    }

    public List<PlayerModel> playGame() {
        deck.shuffle();
        players.stream().forEach(player -> player.setFolded(false));
        int i = 0;
        PlayerModel winner = null;
        while (i < numberOfRounds && winner == null) {
            winner = playRound(i++);
            logger.debug(getGameState());
        }

        List<PlayerModel> winners;

        if (winner == null) {
            winners = showdown();
            playerCommunicationService.notifyPlayersOfWinners(winners, pot);
        } else {
            winners = Collections.singletonList(winner);
            playerCommunicationService.notifyPlayersOfWinners(winners, pot);
        }

        return winners;
    }

    protected PlayerModel playRound(int roundNumber) {
        dealRoundCards(roundNumber);
        //TODO: Betting Scheme

        int currentBet = 0;
        List<PlayerModel> activePlayers = players.stream()
                .filter(player -> !player.isFolded())
                .collect(Collectors.toList());
        for (PlayerModel player : activePlayers) {
            currentBet = promptPlayerForAction(player, currentBet);
        }

        if (playersRemaining == 1) {
            return players.stream()
                    .filter(player -> !player.isFolded())
                    .findFirst().get();
        }

        return null;
    }

    private int promptPlayerForAction(PlayerModel player, int currentBet) {
        int newBet = currentBet;
        if (playersRemaining > 1) {
            PlayerAction playerAction = playerCommunicationService.promptPlayerForAction(player, currentBet);
            switch (playerAction.action) {
                case FOLD:
                    player.setFolded(true);
                    playersRemaining--;
                    break;
                case CHECK:
                    break;
                case CALL:
                case RAISE:
                    newBet = playerAction.amount;
                    pot += playerAction.amount;
                    break;
                case ANTE:
                default:
                    throw new IllegalBetException("Illegal bet type");
            }
            playerCommunicationService.notifyPlayersOfAction(player, playerAction);
        }

        return newBet;
    }

    protected void dealRoundCards(int round) {
        logger.debug("Round: " + round);
        dealCardsToPlayers(round);
        dealCommunityCards(round);
    }

    private void dealCardsToPlayers(int round) {
        //down cards
        int numberOfDownCards = playerCardRule.getNumberToDealForRound(round)[0];
        logger.debug("Player dn cards to deal this round: " + numberOfDownCards);
        dealCardsToPlayer(numberOfDownCards, false);

        //Up cards
        int numberOfUpCards = (playerCardRule.getNumberToDealForRound(round))[1];
        logger.debug("Player up cards to deal this round: " + numberOfUpCards);
        dealCardsToPlayer(numberOfUpCards, true);
    }

    private void dealCommunityCards(int round) {
        if (communityCardRule != null) {
            int[] numberToDeal = communityCardRule.getNumberToDealForRound(round);
            boolean[] dealUp = communityCardRule.getConditionToDealForRound(round);
            logger.debug("Community cards to deal this round: " + Arrays.toString(numberToDeal));
            logger.debug("Community card conditions for deal: " + Arrays.toString(dealUp));
            for (int k = 0; k < numberToDeal.length; k++) {
                for (int l = 0; l < numberToDeal[k]; l++) {
                    int[] dealLocation = communityCardRule.getNextDealLocation();
                    if (dealLocation != null) {
                        int x = dealLocation[0];
                        int y = dealLocation[1];
                        boolean up = dealUp[k];
                        Card card = deck.drawCard();
                        logger.debug("Dealing " + card + " to grid at: " + x + "," + y);
                        cardGrid.dealCardToGrid(x, y, deck.drawCard(), up);
                        playerCommunicationService.notifyPlayersOfCardDealtToGrid(x, y, card, up);
                    }
                }
            }
        }
    }

    private void dealCardsToPlayer(int numberOfCards, boolean dealFacingUp) {
        for (int deal = 0; deal < numberOfCards; deal++) {
            for (PlayerModel player : players) {
                Card card = deck.drawCard();
                card.setUp(dealFacingUp);
                logger.debug("Dealing " + card + " to " + player.getName());
                player.receiveCard(card);
                playerCommunicationService.notifyPlayersOfCardDealtToPlayer(player, card, dealFacingUp);

                if (specialCardRules != null) {
                    for (SpecialCardRule rule : specialCardRules) {
                        for (RuleCard ruleCard : rule.card) {
                            if (ruleCard.isCard(card)) {
                                //TODO: apply action
                            }
                        }

                    }
                }

            }
        }
    }

    private int getBestHand(Card[] hand) {
        List<Card> totalHand = new ArrayList<>(7);
        Collections.addAll(totalHand, hand);
        int bestHandValue = 8000;
        for (CommunityCardCollection cardCollection : communityCardRule.communityCardCollection) {
            cardCollection.resetNext();
            while (cardCollection.hasNext()) {
                int[] cardGridLocation = cardCollection.getNextGridLocation();
                Card currentCard = cardGrid.getCardFromGrid(cardGridLocation[0], cardGridLocation[1]);
                totalHand.add(currentCard);
            }
            int handValue = HandEvaluator.evaluateBestFiveCardHand(totalHand.toArray(new Card[totalHand.size()]));
            if (handValue < bestHandValue) {
                bestHandValue = handValue;
                logger.trace("getBestHand: new bestHandValue = " + bestHandValue);
                logger.trace("getBestHand: totalHand = " + totalHand.toString());
            }
        }
        logger.trace("getBestHand: hand = " + Arrays.toString(hand));
        logger.trace("getBestHand: cardCollection = " + communityCardRule.communityCardCollection.toString());
        logger.trace("getBestHand: totalHand = " + totalHand.toString());

        return bestHandValue;

    }

    protected List<PlayerModel> showdown() {
        List<PlayerModel> winners = new ArrayList<>();
        int winningHandValue = 8000;
        for (PlayerModel player : players) {
            int curHandValue = getBestHand(player.getHand());
            logger.debug("Player: " + player.getName() + ", HandValue: " + curHandValue + ", HandName: " + HandNameTable.handNames[curHandValue - 1]);
            if (curHandValue < winningHandValue) {
                winningHandValue = curHandValue;
                winners.clear();
                winners.add(player);
            } else if (curHandValue == winningHandValue) {
                winners.add(player);
            }

        }
        winningHandName = HandNameTable.handNames[winningHandValue - 1];
        return winners;
    }

    public String getGameState() {
        String state = cardGrid.getCurrentStateAsString() + "\n";
        for (PlayerModel player : players) {
            state += player.getName() + " has " + player.getHandAsString() + "\n";
        }
        return state;
    }


}
