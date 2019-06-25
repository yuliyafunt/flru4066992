package flteam.flru4066992.parser.impl;

import flteam.flru4066992.model.Match;
import flteam.flru4066992.parser.Parser;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class HandballParser extends Parser {

    private static final String URL = "https://www.myscore.ru/handball/";

    @Override
    public List<Match> parse() {
        return defaultParse(URL);
    }

}
