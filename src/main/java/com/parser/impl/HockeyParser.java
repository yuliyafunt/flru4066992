package com.parser.impl;

import com.model.Match;
import com.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class HockeyParser implements Parser {

    private static final String URL = "https://www.myscore.ru/hockey/";

    @Override
    public List<Match> parse() {
        List<Match> matches = new ArrayList<>();
        String html = getHtml(URL);
        Document document = Jsoup.parse(html);
        return matches;
    }

}
