package flteam.flru4066992.parser.impl;

import flteam.flru4066992.model.Match;
import flteam.flru4066992.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class TennisParser implements Parser {

    private static final String URL = "https://www.myscore.ru/tennis/";

    @Override
    public List<Match> parse() {
        List<Match> matches = new ArrayList<>();
        WebDriver webDriver = getWebDriver(URL);
        String html = getLiveHtml(webDriver);
        Document document = Jsoup.parse(html);
        return matches;
    }

}
