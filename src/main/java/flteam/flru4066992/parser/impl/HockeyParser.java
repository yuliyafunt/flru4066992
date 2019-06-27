package flteam.flru4066992.parser.impl;

import flteam.flru4066992.core.BetType;
import flteam.flru4066992.model.time.Time;
import flteam.flru4066992.parser.Parser;
import flteam.flru4066992.parser.ParserResult;

import javax.inject.Singleton;

@Singleton
public class HockeyParser extends Parser {

    private static final String URL = "https://www.myscore.ru/hockey/";

    @Override
    public ParserResult parse() {
        return new ParserResult(BetType.HOCKEY, defaultParse(URL));
    }

    @Override
    protected Time getTime(String stringTime) {
        return null;
    }

}
