package pokernight.test;

import pokernight.rule.GameRules;
import pokernight.rule.RuleFileParser;

public class GameRulesTester {

	public static void main(String[] args) {
		GameRules gameRules;
		String ruleFile = "simple.json";
		gameRules = RuleFileParser.parse(ruleFile);
		System.out.print(gameRules);

	}

}
