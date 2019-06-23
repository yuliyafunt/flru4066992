package com.parser;

import com.model.Match;
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
            System.out.println(div.text());
        }
        return matches;
    }

    @Override
    public String getHtml() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        webDriver.get(URL);
        return webDriver.getPageSource();
    }

}
