package flteam.flru4066992.parser.impl;

import flteam.flru4066992.model.Match;
import flteam.flru4066992.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class TennisParser implements Parser {

    private static final String URL = "https://www.myscore.ru/tennis/";

    @Override
    public List<Match> parse() {
        List<Match> matches = new ArrayList<>();
        String html = getHtml(URL);
        Document document = Jsoup.parse(html);
        return matches;
    }

}
