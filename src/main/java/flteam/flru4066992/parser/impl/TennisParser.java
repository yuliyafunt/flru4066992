package flteam.flru4066992.parser.impl;

import flteam.flru4066992.core.BetType;
import flteam.flru4066992.model.time.TennisTime;
import flteam.flru4066992.model.time.Time;
import flteam.flru4066992.parser.Parser;
import flteam.flru4066992.parser.ParserResult;
import org.jsoup.nodes.Element;

import javax.inject.Singleton;

@Singleton
public class TennisParser extends Parser {

    private static final String URL = "https://www.myscore.ru/tennis/";

    @Override
    public ParserResult parse() {
        return new ParserResult(BetType.TENNIS, defaultParse(URL));
    }

    @Override
    protected Time getTime(String time) {
        return new TennisTime(time);
    }

    @Override
    protected int getHomeScore(Element element) {
        if (checkDefaultParseHomeScore(element)) {
            return super.getHomeScore(element);
        } else {
            Element score = element.getElementsByClass(HOME_SCORE_ELEMENT).get(0);
            return Integer.parseInt(score.text());
        }
    }

    @Override
    protected int getAwayScore(Element element) {
        if (checkDefaultParseAwayScore(element)) {
            return super.getAwayScore(element);
        } else {
            Element score = element.getElementsByClass(AWAY_SCORE_ELEMENT).get(0);
            return Integer.parseInt(score.text());
        }
    }

    private boolean checkDefaultParseHomeScore(Element element) {
        try {
            super.getHomeScore(element);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkDefaultParseAwayScore(Element element) {
        try {
            super.getAwayScore(element);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
