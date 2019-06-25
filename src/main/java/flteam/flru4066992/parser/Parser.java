package flteam.flru4066992.parser;

import flteam.flru4066992.model.Match;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.Callable;

public interface Parser extends Callable<List<Match>> {

    List<Match> parse();

    default String getHtml(String url) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        webDriver.get(url);
        webDriver.findElement(By.linkText("LIVE")).click();
        webDriver.findElements(By.className("expand"))
                .forEach(WebElement::click);
        String html = webDriver.getPageSource();
        webDriver.close();
        return html;
    }

}
