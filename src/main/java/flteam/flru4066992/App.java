package flteam.flru4066992;

import flteam.flru4066992.model.Match;
import flteam.flru4066992.parser.Parser;
import flteam.flru4066992.parser.impl.FootballParser;

import java.util.List;

public class App {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", App.class.getResource("/chromedriver").getFile());
        Parser parser = new FootballParser();
        List<Match> matches = parser.parse();
        matches.forEach(System.out::println);
    }

}
