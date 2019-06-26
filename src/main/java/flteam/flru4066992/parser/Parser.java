package flteam.flru4066992.parser;

import flteam.flru4066992.model.Match;
import flteam.flru4066992.model.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Parser implements Callable<List<Match>> {

    private static final WebDriver WEB_DRIVER = new ChromeDriver(getChromeOptions());

    private static final String LIVE_MATCHES_CLASSNAME = "active-bet";
    private static final String LEAGUE_HEADER_CLASSNAME = "event__header";
    private static final String LEAGUE_CLASSNAME = "event__title--name";
    private static final String SCORES_CLASSNAME = "event__scores fontBold";
    private static final String HOME_TEAM_CLASSNAME = "event__participant--home";
    private static final String AWAY_TEAM_CLASSNAME = "event__participant--away";
    private static final String TIME_CLASSNAME = "event__stage";
    private static final String HOME_COEFF_CLASSNAME = "o_1";
    private static final String AWAY_COEFF_CLASSNAME = "o_2";

    private static final long TIMEOUT_FOR_LOADING = TimeUnit.MINUTES.toMillis(1);
    private static final Duration POLLING_INTERVAL = Duration.ofSeconds(1);

    private static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        return chromeOptions;
    }

    public abstract List<Match> parse();

    private synchronized String getCoeffHtml(String url) {
        WEB_DRIVER.get(url);
        waitForElementToBeClickable(ExpectedConditions.elementToBeClickable(By.linkText("Коэффициенты")));
        WEB_DRIVER.findElements(By.className("expand"))
                .forEach(webElement -> waitForElementToBeClickable(ExpectedConditions.elementToBeClickable(webElement)));
        return WEB_DRIVER.getPageSource();
    }

    private synchronized void waitForElementToBeClickable(Function<WebDriver, WebElement> condition) {
        WebDriverWait webDriverWait = new WebDriverWait(WEB_DRIVER, TIMEOUT_FOR_LOADING);
        webDriverWait
                .pollingEvery(POLLING_INTERVAL)
                .until(condition)
                .click();
    }

    /**
     * Mandatory invoke this before closing app!
     */
    public static void closeBrowser() {
        WEB_DRIVER.quit();
    }

    @Override
    public List<Match> call() {
        return parse();
    }

    protected List<Match> defaultParse(String url) {
        List<Match> matches = new ArrayList<>();
        String html = getCoeffHtml(url);
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass(LIVE_MATCHES_CLASSNAME).stream()
                .map(Element::parent).collect(Collectors.toCollection(Elements::new));
        for (Element element : elements) {
            String league = getLeagueName(element.previousElementSiblings());
            Element score = element.getElementsByClass(SCORES_CLASSNAME).get(0);
            Elements spans = score.getElementsByTag("span");
            int homeScore = Integer.parseInt(spans.get(0).text());
            int awayScore = Integer.parseInt(spans.get(1).text());
            String home = element.getElementsByClass(HOME_TEAM_CLASSNAME).get(0).text();
            String away = element.getElementsByClass(AWAY_TEAM_CLASSNAME).get(0).text();
            String time = element.getElementsByClass(TIME_CLASSNAME).get(0).text();
            double homeCoeff = 0D;
            double awayCoeff = 0D;
            Elements homeCoeffElements = element.getElementsByClass(HOME_COEFF_CLASSNAME);
            Elements awayCoeffElements = element.getElementsByClass(AWAY_COEFF_CLASSNAME);
            if (!homeCoeffElements.isEmpty()) {
                homeCoeff = Double.parseDouble(homeCoeffElements.get(0).text());
            }
            if (!awayCoeffElements.isEmpty()) {
                awayCoeff = Double.parseDouble(awayCoeffElements.get(0).text());
            }
            Team homeTeam = new Team(home, homeScore, homeCoeff);
            Team awayTeam = new Team(away, awayScore, awayCoeff);
            Match match = new Match(league, homeTeam, awayTeam, time);
            matches.add(match);
        }
        return matches;
    }

    private String getLeagueName(Elements elements) {
        String league = "";
        for (Element element : elements) {
            if (element.hasClass(LEAGUE_HEADER_CLASSNAME)) {
                league = element.getElementsByClass(LEAGUE_CLASSNAME).get(0).text();
                break;
            }
        }
        return league;
    }

}
