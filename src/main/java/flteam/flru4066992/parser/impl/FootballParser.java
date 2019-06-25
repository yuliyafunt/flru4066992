package flteam.flru4066992.parser.impl;

import flteam.flru4066992.model.Match;
import flteam.flru4066992.model.Team;
import flteam.flru4066992.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class FootballParser implements Parser {

    private static final String URL = "https://www.myscore.ru/";
    private static final String LIVE_MATCHES_CLASSNAME = "event__match--oneLine";
    private static final String LEAGUE_HEADER_CLASSNAME = "event__header";
    private static final String LEAGUE_CLASSNAME = "event__title--name";
    private static final String SCORES_CLASSNAME = "event__scores fontBold";
    private static final String HOME_TEAM_CLASSNAME = "event__participant event__participant--home";
    private static final String AWAY_TEAM_CLASSNAME = "event__participant event__participant--away";
    private static final String TIME_CLASSNAME = "event__stage";

    @Override
    public List<Match> parse() {
        List<Match> matches = new ArrayList<>();
        String html = getHtml(URL);
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass(LIVE_MATCHES_CLASSNAME);
        for (Element element : elements) {
            String league = getLeagueName(element.previousElementSiblings());
            Element score = element.getElementsByClass(SCORES_CLASSNAME).get(0);
            Elements spans = score.getElementsByTag("span");
            int homeScore = Integer.parseInt(spans.get(0).text());
            int awayScore = Integer.parseInt(spans.get(1).text());
            String homeTeam = element.getElementsByClass(HOME_TEAM_CLASSNAME).get(0).text();
            String awayTeam = element.getElementsByClass(AWAY_TEAM_CLASSNAME).get(0).text();
            String time = element.getElementsByClass(TIME_CLASSNAME).get(0).text();
            Team home = new Team(homeTeam, homeScore, 0);
            Team away = new Team(awayTeam, awayScore, 0);
            Match match = new Match(league, home, away, time);
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

    @Override
    public List<Match> call() throws Exception {
        return parse();
    }

}
