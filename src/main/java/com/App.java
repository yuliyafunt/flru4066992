package com;

import com.model.Match;
import com.parser.FootballParser;
import com.parser.Parser;

import java.util.List;

public class App {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", App.class.getResource("/chromedriver").getFile());
        Parser parser = new FootballParser();
        List<Match> matches = parser.parse();
        matches.forEach(System.out::println);
    }

}
