import flteam.flru4066992.parser.Browser;
import flteam.flru4066992.parser.Parser;
import flteam.flru4066992.parser.impl.*;
import org.junit.Test;

public class SeleniumParserTest {

    @Test
    public void parsingTest() {
        try {
            System.setProperty("webdriver.chrome.driver", SeleniumParserTest.class.getResource("/drivers/chromedriver").getFile());
            Parser[] parsers = {
                    new FootballParser(),
                    new BasketballParser(),
                    new TennisParser(),
                    new VolleyballParser(),
                    new HandballParser(),
                    new HockeyParser()
            };
            for (Parser parser : parsers) {
                parser.parse().getMatches().forEach(System.out::println);
                System.out.println();
            }
        } finally {
            Browser.close();
        }
    }

    @Test
    public void stressTest() {
        try {
            System.setProperty("webdriver.chrome.driver", SeleniumParserTest.class.getResource("/drivers/chromedriver").getFile());
            Parser[] parsers = {
                    new FootballParser(),
                    new BasketballParser(),
                    new TennisParser(),
                    new VolleyballParser(),
                    new HandballParser(),
                    new HockeyParser()
            };
            for (int i = 0; i < 100; i++) {
                for (Parser parser : parsers) {
                    parser.parse().getMatches().forEach(System.out::println);
                    System.out.println();
                }
            }
        } finally {
            Browser.close();
        }
    }

}
