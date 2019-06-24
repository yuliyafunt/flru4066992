package com.parser.impl;

import com.model.Match;
import com.model.Team;
import com.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FootballParser implements Parser {

    private static final String URL = "https://www.myscore.ru/";
    private static final String LIVE_MATCHES_CLASSNAME = "event__live--icon icon icon--live active active-bet";
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
        int i = 0;
        for (Element element : elements) {
            Element div = element.parent();
            Element parentDiv = div.parent();
            Element leagueEl = parentDiv.getElementsByClass(LEAGUE_HEADER_CLASSNAME).get(i);
            String league = leagueEl.getElementsByClass(LEAGUE_CLASSNAME).get(0).text();
            Element score = div.getElementsByClass(SCORES_CLASSNAME).get(0);
            Elements spans = score.getElementsByTag("span");
            int homeScore = Integer.parseInt(spans.get(0).text());
            int awayScore = Integer.parseInt(spans.get(1).text());
            String homeTeam = div.getElementsByClass(HOME_TEAM_CLASSNAME).get(0).text();
            String awayTeam = div.getElementsByClass(AWAY_TEAM_CLASSNAME).get(0).text();
            String time = div.getElementsByClass(TIME_CLASSNAME).get(0).text();
            Team home = new Team(homeTeam, homeScore, 0);
            Team away = new Team(awayTeam, awayScore, 0);
            Match match = new Match(league, home, away, time);
            matches.add(match);
            i++;
        }
        return matches;
    }

}
