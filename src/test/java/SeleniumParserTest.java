import flteam.flru4066992.parser.Parser;
import flteam.flru4066992.parser.impl.*;
import org.junit.Test;

public class SeleniumParserTest {

    @Test
    public void parsingTest() {
        try {
            System.setProperty("webdriver.chrome.driver", SeleniumParserTest.class.getResource("/chromedriver").getFile());
            Parser[] parsers = {
                    new FootballParser(),
                    new BasketballParser(),
                    new TennisParser(),
                    new VolleyballParser(),
                    new HandballParser(),
                    new HockeyParser()
            };
            for (Parser parser : parsers) {
                parser.parse().forEach(System.out::println);
                System.out.println();
            }
        } finally {
            Parser.closeBrowser();
        }
    }

}
