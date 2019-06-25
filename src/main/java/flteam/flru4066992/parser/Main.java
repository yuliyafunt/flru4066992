package flteam.flru4066992.parser;

import flteam.flru4066992.parser.impl.BasketballParser;
import flteam.flru4066992.parser.impl.FootballParser;
import flteam.flru4066992.parser.impl.TennisParser;
import flteam.flru4066992.parser.impl.VolleyballParser;

public class Main {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", Main.class.getResource("/chromedriver").getFile());
        Parser[] parsers = {
                new FootballParser(),
                new BasketballParser(),
                new TennisParser(),
                new VolleyballParser()
        };
        for (Parser parser : parsers) {
            parser.parse().forEach(System.out::println);
            System.out.println();
        }
    }

}
