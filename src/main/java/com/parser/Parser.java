package com.parser;

import com.model.Match;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public interface Parser {

    List<Match> parse();

    default String getHtml(String url) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        webDriver.get(url);
        String html = webDriver.getPageSource();
        webDriver.close();
        return html;
    }

}
