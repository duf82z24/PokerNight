package com.pokernight.rule;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleFileParser {

    private static Logger logger = LoggerFactory.getLogger(RuleFileParser.class);

    public static GameRules parse(String ruleFile) {
        GameRules gameRules = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            gameRules = mapper.readValue(new File(ruleFile), GameRules.class);
        } catch (IOException e) {
            logger.error("unable to parse Rules", e);
        }
        return gameRules;
    }

}
