package pokernight.rule;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommunityCardRule {
	public final String name; // Required
	public final String[] dealRule; // Required
	public final String[] gridDealOrder; // Required
	public final CommunityCardCollection[] communityCardCollection; // Required
	private int cardNumber = 0;

	CommunityCardRule(
			@JsonProperty("name") String name,
			@JsonProperty("dealRule") String[] dealRule,
			@JsonProperty("gridDealOrder") String[] gridDealOrder,
			@JsonProperty("communityCardCollection") CommunityCardCollection[] communityCardCollection) {
		this.name = name;
		this.dealRule = dealRule;
		this.gridDealOrder = gridDealOrder;
		this.communityCardCollection = communityCardCollection;
	}

	public int[] getNumberToDealForRound(int round) {
		int[] cardsToDeal = null;
		try {
			String[] tokens = dealRule[round].split(",");
			cardsToDeal = new int[tokens.length];
			//System.out.println("numDeal round length: " + tokens.length);
			for (int i = 0; i < tokens.length; i++) {
				//System.out.println("cardsToDeal length: " + cardsToDeal.length);
				//System.out.println("tokens length: " + tokens.length);
				cardsToDeal[i] = Integer.parseInt(tokens[i].split("-")[0]);
			}
		} catch (IndexOutOfBoundsException e) {
			// No more rounds defined so nothing to deal
			cardsToDeal = new int[1];
			cardsToDeal[0] = 0;
		}
		return cardsToDeal;
	}

	public boolean[] getConditionToDealForRound(int round) {
		boolean[] howToDeal = null;
		try {
			String[] tokens = dealRule[round].split(",");
			howToDeal = new boolean[tokens.length];
			//System.out.println("cond round length: " + tokens.length);
			for (int i = 0; i < tokens.length; i++) {
				if (tokens[i].split("-").length > 1) {
					String conditionIndicator = tokens[i].split("-")[1];
					howToDeal[i] = "u".equals(conditionIndicator);
				} else
					howToDeal[i] = false;
			}
		} catch (IndexOutOfBoundsException e) {
			howToDeal = new boolean[1];
			howToDeal[0] = false;
		}
		return howToDeal;
	}

	public int[] getNextDealLocation() {
		try {
			String[] nextLocationTokens = gridDealOrder[cardNumber].split("-");
			int x = Integer.parseInt(nextLocationTokens[0]);
			int y = Integer.parseInt(nextLocationTokens[1]);
			cardNumber = cardNumber + 1;
			return new int[] { x, y };
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "CommunityCardRule [name=" + name + ", dealRule="
				+ Arrays.toString(dealRule) + ", gridDealOrder="
				+ Arrays.toString(gridDealOrder) + ", communityCardCollection="
				+ Arrays.toString(communityCardCollection) + "]";
	}

}
