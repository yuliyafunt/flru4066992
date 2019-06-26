package flteam.flru4066992.parser.impl;

import flteam.flru4066992.model.Match;
import flteam.flru4066992.model.time.BasketballTime;
import flteam.flru4066992.model.time.Time;
import flteam.flru4066992.parser.Parser;
import org.jsoup.nodes.Element;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class BasketballParser extends Parser {

    private static final String URL = "https://www.myscore.ru/basketball/";

    @Override
    public List<Match> parse() {
        return defaultParse(URL);
    }

    @Override
    protected Time getTime(String stringTime) {
        return new BasketballTime(stringTime);
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
