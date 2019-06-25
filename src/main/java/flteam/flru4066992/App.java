package flteam.flru4066992;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import flteam.flru4066992.core.Reactor;
import flteam.flru4066992.core.bot.Bot;
import flteam.flru4066992.parser.Parser;
import flteam.flru4066992.parser.impl.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApiContextInitializer.init();
        // TODO: add OS driver dependency
        System.setProperty("webdriver.chrome.driver", App.class.getResource("/chromedriver_mac").getFile());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Injector injector = Guice.createInjector(new AppModule());
        loader.setControllerFactory(injector::getInstance);
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // run bot
        Bot bot = injector.getInstance(Bot.class);
        TelegramBotsApi api = new TelegramBotsApi();
        api.registerBot(bot);

        // starting parsing with timeout
        Reactor reactor = injector.getInstance(Reactor.class);
        reactor.react();
    }

    public static void main(String[] args) {
        launch(args);
    }


    private class AppModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Parser.class).annotatedWith(Names.named("Football")).to(FootballParser.class);
            bind(Parser.class).annotatedWith(Names.named("BasketBall")).to(BasketballParser.class);
            bind(Parser.class).annotatedWith(Names.named("Volleyball")).to(VolleyballParser.class);
            bind(Parser.class).annotatedWith(Names.named("Handball")).to(HandballParser.class);
            bind(Parser.class).annotatedWith(Names.named("Hockey")).to(HockeyParser.class);
            bind(Parser.class).annotatedWith(Names.named("Tennis")).to(TennisParser.class);
        }
    }
}