package com;

import com.parser.FootballParser;
import com.parser.Parser;

public class App {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", App.class.getResource("/chromedriver").getFile());
        Parser parser = new FootballParser();
        parser.parse();
    }

}
