package pokernight.test;

import pokernight.exception.IllegalGridLocationException;
import pokernight.model.*;

public class GameModelTester {

	/**
	 * @param args
	 * @throws IllegalGridLocationException 
	 */
	public static void main(String[] args) throws IllegalGridLocationException {
		DeckModel deck = new DeckModel();
		deck.shuffle();
		PlayerModel[] players = new PlayerModel[6];
		players[0] = new PlayerModel("A", 50);
		players[1] = new PlayerModel("B", 50);
		players[2] = new PlayerModel("C", 50);
		players[3] = new PlayerModel("D", 50);
		players[4] = new PlayerModel("E", 50);
		players[5] = new PlayerModel("F", 50);
		String ruleFile = "simple.json";
		GameModel gameModel = new GameModel(deck, players, ruleFile);
		
		for (int i = 0; i < gameModel.numberOfRounds; i++){
			gameModel.dealRoundCards();
			System.out.println(gameModel.getGameState());
		}
		
		PlayerModel winningPlayer = gameModel.showdown();
		System.out.println(winningPlayer.getName() + " wins with a " + HandNameTable.handNames[gameModel.winningHand-1] + "!");
	}

}
