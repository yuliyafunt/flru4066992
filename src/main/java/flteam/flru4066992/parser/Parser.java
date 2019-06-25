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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class Parser implements Callable<List<Match>> {

    private static final WebDriver WEB_DRIVER = new ChromeDriver(getChromeOptions());

    private static final String LIVE_MATCHES_CLASSNAME = "event__match--live";
    private static final String LEAGUE_HEADER_CLASSNAME = "event__header";
    private static final String LEAGUE_CLASSNAME = "event__title--name";
    private static final String SCORES_CLASSNAME = "event__scores fontBold";
    private static final String HOME_TEAM_CLASSNAME = "event__participant event__participant--home";
    private static final String AWAY_TEAM_CLASSNAME = "event__participant event__participant--away";
    private static final String TIME_CLASSNAME = "event__stage";
    private static final String HOME_COEFF_CLASSNAME = "o_1";
    private static final String AWAY_COEFF_CLASSNAME = "o_2";

    private static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        return chromeOptions;
    }

    public abstract List<Match> parse();

    private String getCoeffHtml(String url) {
        WEB_DRIVER.get(url);
        WEB_DRIVER.findElement(By.linkText("Коэффициенты")).click();
        WEB_DRIVER.findElements(By.className("expand"))
                .forEach(WebElement::click);
        String html = WEB_DRIVER.getPageSource();
        WEB_DRIVER.close();
        return html;
    }

    @Override
    public List<Match> call() {
        return parse();
    }

    protected List<Match> defaultParse(String url) {
        List<Match> matches = new ArrayList<>();
        String html = getCoeffHtml(url);
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass(LIVE_MATCHES_CLASSNAME);
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
