package com.parser;

import com.model.Match;
import com.model.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class FootballParser implements Parser {

    private static final String URL = "https://www.myscore.ru/";

    @Override
    public List<Match> parse() {
        List<Match> matches = new ArrayList<>();
        String html = getHtml();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("event__live--icon icon icon--live active active-bet");
        for (Element element : elements) {
            Element div = element.parent();
            Element parentDiv = div.parent();
            Element leagueEl = parentDiv.getElementsByClass("event__header top").get(0);
            String league = leagueEl.getElementsByClass("event__titleBox").get(0).text();
            Element score = div.getElementsByClass("event__scores fontBold").get(0);
            Elements spans = score.getElementsByTag("span");
            int homeScore = Integer.parseInt(spans.get(0).text());
            int awayScore = Integer.parseInt(spans.get(1).text());
            String homeTeam = div.getElementsByClass("event__participant event__participant--home").get(0).text();
            String awayTeam = div.getElementsByClass("event__participant event__participant--away").get(0).text();
            String time = div.getElementsByClass("event__stage").get(0).text();
            Team home = new Team(homeTeam, homeScore, 0);
            Team away = new Team(awayTeam, awayScore, 0);
            Match match = new Match(league, home, away, time);
            matches.add(match);
        }
        return matches;
    }

    @Override
    public String getHtml() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        webDriver.get(URL);
        String html = webDriver.getPageSource();
        webDriver.close();
        return html;
    }

}
