package flteam.flru4066992.parser.impl;

import flteam.flru4066992.model.Match;
import flteam.flru4066992.model.Team;
import flteam.flru4066992.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class FootballParser implements Parser {

    private static final String URL = "https://www.myscore.ru/";
    private static final String LIVE_MATCHES_CLASSNAME = "event__match--live";
    private static final String LEAGUE_HEADER_CLASSNAME = "event__header";
    private static final String LEAGUE_CLASSNAME = "event__title--name";
    private static final String SCORES_CLASSNAME = "event__scores fontBold";
    private static final String HOME_TEAM_CLASSNAME = "event__participant event__participant--home";
    private static final String AWAY_TEAM_CLASSNAME = "event__participant event__participant--away";
    private static final String TIME_CLASSNAME = "event__stage";
    private static final String HOME_COEFF_CLASSNAME = "o_1";
    private static final String AWAY_COEFF_CLASSNAME = "o_2";

    @Override
    public List<Match> parse() {
        List<Match> matches = new ArrayList<>();
        WebDriver webDriver = getWebDriver(URL);
        String liveHtml = getLiveHtml(webDriver);
        String coeffHtml = getCoeffHtml(webDriver);
        Document liveDocument = Jsoup.parse(liveHtml);
        Document coeffDocument = Jsoup.parse(coeffHtml);
        Elements liveElements = liveDocument.getElementsByClass(LIVE_MATCHES_CLASSNAME);
        Elements coeffElements = coeffDocument.getElementsByClass(LIVE_MATCHES_CLASSNAME);
        for (int i = 0; i < liveElements.size(); i++) {
            Element liveElement = liveElements.get(i);
            Element coeffElement = i < coeffElements.size() ? coeffElements.get(i) : null;
            String league = getLeagueName(liveElement.previousElementSiblings());
            Element score = liveElement.getElementsByClass(SCORES_CLASSNAME).get(0);
            Elements spans = score.getElementsByTag("span");
            int homeScore = Integer.parseInt(spans.get(0).text());
            int awayScore = Integer.parseInt(spans.get(1).text());
            String home = liveElement.getElementsByClass(HOME_TEAM_CLASSNAME).get(0).text();
            String away = liveElement.getElementsByClass(AWAY_TEAM_CLASSNAME).get(0).text();
            String time = liveElement.getElementsByClass(TIME_CLASSNAME).get(0).text();
            double homeCoeff = 0D;
            double awayCoeff = 0D;
            if (coeffElement != null) {
                Elements homeCoeffElements = coeffElement.getElementsByClass(HOME_COEFF_CLASSNAME);
                Elements awayCoeffElements = coeffElement.getElementsByClass(AWAY_COEFF_CLASSNAME);
                if (!homeCoeffElements.isEmpty()) {
                    homeCoeff = Double.parseDouble(homeCoeffElements.get(0).text());
                }
                if (!awayCoeffElements.isEmpty()) {
                    awayCoeff = Double.parseDouble(awayCoeffElements.get(0).text());
                }
            }
            Team homeTeam = new Team(home, homeScore, homeCoeff);
            Team awayTeam = new Team(away, awayScore, awayCoeff);
            Match match = new Match(league, homeTeam, awayTeam, time);
            matches.add(match);
        }
        return matches;
    }

    private String getLeagueName(Elements elements) {
        String league = "";
        for (Element element : elements) {
            if (element.hasClass(LEAGUE_HEADER_CLASSNAME)) {
                league = element.getElementsByClass(LEAGUE_CLASSNAME).get(0).text();
                break;
            }
        }
        return league;
    }

}
