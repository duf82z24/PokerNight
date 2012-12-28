package pokernight.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pokernight.exception.IllegalGridLocationException;
import pokernight.rule.*;

public class GameModel {
	private CardGridModel cardGrid;
	private Card[] wilds = null;
	private DeckModel deck;
	
	private PlayerModel[] players;
	private PlayerModel winningPlayer = null;
	public int winningHand = 8000;
	
	private GameRules gameRules;
	private PlayerCardRule playerCardRule;
	private CommunityCardRule communityCardRule;
	private SpecialCardRule[] specialCardRules;
	private WinCondtion[] winConditions;
	
	public int numberOfRounds;
	//private String playerState = "";
	//private String state = "";
	private int round = 0;
	
	public GameModel(DeckModel deck, PlayerModel[] players, String ruleFile){
		// Parse rules file
		gameRules = RuleFileParser.parse(ruleFile);
	    System.out.println("Game Rules Parsed Successfully");
	    
	    // Initialize rules
	    playerCardRule = gameRules.playerCardRule;
	    if (gameRules.communityCardRule != null){
	    	communityCardRule = gameRules.communityCardRule;
	    	System.out.println("Community Card Rules: " + communityCardRule.toString());
	    }
	    if (gameRules.specialCardRules != null){
	    	specialCardRules = gameRules.specialCardRules;
	    	System.out.println("Special Card Rules: " + specialCardRules.toString());
	    }
	    winConditions = gameRules.winCondtion;
	    numberOfRounds = gameRules.rounds;

	    // Initialize Card Grid
	    cardGrid = new CardGridModel(7, 7);
	    
	    // Initialize deck
	    this.deck = deck; 
	    
	    // Initialize players
	    this.players = players;
	    
	    // ???
	    //state = cardGrid.toString() + "\n" + playerState;
	}
	
	public void dealRoundCards() throws IllegalGridLocationException {
	        //Refer to rule for next card location

	        System.out.println("Round: " + round);
	        
	        //deal cards to players
	        //down cards
	        System.out.println("Player dn cards to deal this round: " + playerCardRule.getNumberToDealForRound(round)[0]);
	        for(int deal = 0; deal < (playerCardRule.getNumberToDealForRound(round))[0]; deal++)
	        {
	            for(PlayerModel player: players)
	            {
	                Card card = deck.drawCard();
	                card.setUp(false);
	                System.out.println("Dealing " + card + " to " + player.getName());
	                player.receiveCard(card);

	                if (specialCardRules != null) {
						for (SpecialCardRule rule : specialCardRules) {
							for (RuleCard ruleCard : rule.card) {
								if (ruleCard.compareTo(card))
									player.applyAction(rule.cardAction);
							}

						}
					}
	                
	            }
	        }
	        
	        //Up cards
	        System.out.println("Player up cards to deal this round: " + playerCardRule.getNumberToDealForRound(round)[1]);
	        for(int deal = 0; deal < (playerCardRule.getNumberToDealForRound(round))[1]; deal++)
	        {
	            for(PlayerModel player: players)
	            {
	                Card card = deck.drawCard();
	                card.setUp(true);
	                System.out.println("Dealing " + card + " to " + player.getName());
	                player.receiveCard(card);

	                
	                if (specialCardRules != null) {
						for (SpecialCardRule rule : specialCardRules) {
							for (RuleCard ruleCard : rule.card) {
								if (ruleCard.compareTo(card))
									player.applyAction(rule.cardAction);
							}

						}
					}
	                
	            }
	        }
	        
	        //Community cards
	        if(communityCardRule != null) {
	            int[] numberToDeal = communityCardRule.getNumberToDealForRound(round);
	            boolean[] dealUp = communityCardRule.getConditionToDealForRound(round);
	            System.out.println("Community cards to deal this round: " + Arrays.toString(numberToDeal));
	            System.out.println("Community card conditions for deal: " + Arrays.toString(dealUp));
	            for(int k = 0; k < numberToDeal.length; k++)
	            {
	                for(int l = 0; l < numberToDeal[k]; l++)
	                {
	                    int[] dealLocation = communityCardRule.getNextDealLocation();
	                    if(dealLocation != null)
	                    {
	                        int x = dealLocation[0];
	                        int y = dealLocation[1];
	                        boolean up = dealUp[k];
	                        cardGrid.dealCardToGrid(x, y, deck.drawCard(), up);
	                    }
	                }
	            }
	        }
	        
    
	       
	        //Deal card
	        
	        //Any special action?
	        
	        //Prompt for bets (Rotating, Standard)
	        
	        //Showdown?
	        round = round + 1;
	        
	    }
	
    private int getBestHand(Card[] hand) throws IllegalGridLocationException {
        List<Card> totalHand = new ArrayList<Card>(7);
        for(Card card: hand){
            totalHand.add(card);
        }
        int bestHandValue = 8000;
        for(CommunityCardCollection cardCollection: communityCardRule.communityCardCollection)
            {
                cardCollection.resetNext();
                while(cardCollection.hasNext())
                {
                    int[] cardGridLocation = cardCollection.getNextGridLocation();
                    Card currentCard = cardGrid.getCardFromGrid(cardGridLocation[0], cardGridLocation[1]);
                    totalHand.add(currentCard);
                }
                int handValue = HandEvaluator.evaluateBestFiveCardHand(totalHand.toArray(new Card[0]));
                if(handValue < bestHandValue){
                    bestHandValue = handValue;
                    //System.out.println("getBestHand: new bestHandValue = " + bestHandValue);
                    //System.out.println("getBestHand: totalHand = " + totalHand.toString());
                }
            }
        //System.out.println("getBestHand: hand = " + Arrays.toString(hand));
        //System.out.println("getBestHand: cardCollection = " + communityCardRule.communityCardCollection.toString());
        //System.out.println("getBestHand: totalHand = " + totalHand.toString());
        
        return bestHandValue;
        
    }
    
    public PlayerModel showdown() throws IllegalGridLocationException {
            PlayerModel winner = null;
            int winningHandValue = 8000;
            for(PlayerModel player: players)
            {
                System.out.print("Player: " + player.getName() + "\t");
            	int curHandValue = getBestHand(player.getHand());
                if(curHandValue < winningHandValue)
                {
                    winningHandValue = curHandValue;
                    winner = player;
                }
            }
            winningPlayer = winner;
            winningHand = winningHandValue;
            return winner;
        }

	public String getGameState() {
		String state = cardGrid.getCurrentStateAsString() + "\n";
        for(PlayerModel player: players) {
        	state += player.getName() + " has " + player.getHandAsString() + "\n";
        }
		return state;
	}
	
	
}
