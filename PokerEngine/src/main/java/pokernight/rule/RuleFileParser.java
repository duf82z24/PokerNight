package pokernight.rule;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RuleFileParser {

    public static GameRules parse(String ruleFile) {
        GameRules gameRules = null;
        ObjectMapper mapper = new ObjectMapper();
        //mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
        try {
            gameRules = mapper.readValue(new File(ruleFile), GameRules.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return gameRules;
    }

}
