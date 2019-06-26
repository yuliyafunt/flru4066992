package flteam.flru4066992.parser;

import flteam.flru4066992.model.Match;
import flteam.flru4066992.model.Team;
import flteam.flru4066992.model.time.Time;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

    private static final String LIVE_MATCHES_CLASSNAME = "active-bet";
    private static final String LEAGUE_HEADER_CLASSNAME = "event__header";
    private static final String LEAGUE_CLASSNAME = "event__title--name";
    private static final String SCORES_CLASSNAME = "event__scores";
    private static final String HOME_TEAM_CLASSNAME = "event__participant--home";
    private static final String AWAY_TEAM_CLASSNAME = "event__participant--away";
    private static final String TIME_CLASSNAME = "event__stage";
    private static final String HOME_COEFF_CLASSNAME = "o_1";
    private static final String AWAY_COEFF_CLASSNAME = "o_2";

    protected static final String HOME_SCORE_ELEMENT = "event__score--home";
    protected static final String AWAY_SCORE_ELEMENT = "event__score--away";

    private static final long TIMEOUT_FOR_LOADING = TimeUnit.MINUTES.toMillis(1);
    private static final Duration POLLING_INTERVAL = Duration.ofSeconds(1);

    private static final WebDriverWait WEB_DRIVER_WAIT = new WebDriverWait(Browser.instance(), TIMEOUT_FOR_LOADING);

    public abstract List<Match> parse();

    protected abstract Time getTime(String stringTime);

    private synchronized String getCoeffHtml(String url) {
        Browser.instance().get(url);
        waitForElementToBeClickable(ExpectedConditions.elementToBeClickable(By.linkText("Коэффициенты")));
        Browser.instance().findElements(By.className("expand"))
                .forEach(webElement -> waitForElementToBeClickable(ExpectedConditions.elementToBeClickable(webElement)));
        return Browser.instance().getPageSource();
    }

    private void waitForElementToBeClickable(Function<WebDriver, WebElement> condition) {
        try {
            WebElement webElement = WEB_DRIVER_WAIT
                    .pollingEvery(POLLING_INTERVAL)
                    .until(condition);
            new Actions(Browser.instance()).click(webElement).perform();
        } catch (UnhandledAlertException ignore) {
        }
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
            int homeScore = getHomeScore(element);
            int awayScore = getAwayScore(element);
            String home = getHomeTeam(element);
            String away = getAwayTeam(element);
            Time time = getTime(getStringTime(element));
            double homeCoeff = getHomeCoeff(element);
            double awayCoeff = getAwayCoeff(element);
            Team homeTeam = new Team(home, homeScore, homeCoeff);
            Team awayTeam = new Team(away, awayScore, awayCoeff);
            Match match = new Match(league, homeTeam, awayTeam, time);
            matches.add(match);
        }
        return matches;
    }

    private String getStringTime(Element element) {
        return element.getElementsByClass(TIME_CLASSNAME).get(0).text();
    }

    private String getHomeTeam(Element element) {
        return element.getElementsByClass(HOME_TEAM_CLASSNAME).get(0).text();
    }

    private String getAwayTeam(Element element) {
        return element.getElementsByClass(AWAY_TEAM_CLASSNAME).get(0).text();
    }

    private double getHomeCoeff(Element element) {
        double homeCoeff = 0D;
        Elements homeCoeffElements = element.getElementsByClass(HOME_COEFF_CLASSNAME);
        if (!homeCoeffElements.isEmpty()) {
            homeCoeff = Double.parseDouble(homeCoeffElements.get(0).text());
        }
        return homeCoeff;
    }

    private double getAwayCoeff(Element element) {
        double awayCoeff = 0D;
        Elements awayCoeffElements = element.getElementsByClass(AWAY_COEFF_CLASSNAME);
        if (!awayCoeffElements.isEmpty()) {
            awayCoeff = Double.parseDouble(awayCoeffElements.get(0).text());
        }
        return awayCoeff;
    }

    protected int getHomeScore(Element element) {
        Element score = element.getElementsByClass(SCORES_CLASSNAME).get(0);
        Elements spans = score.getElementsByTag("span");
        return Integer.parseInt(spans.get(0).text());
    }

    protected int getAwayScore(Element element) {
        Element score = element.getElementsByClass(SCORES_CLASSNAME).get(0);
        Elements spans = score.getElementsByTag("span");
        return Integer.parseInt(spans.get(1).text());
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
