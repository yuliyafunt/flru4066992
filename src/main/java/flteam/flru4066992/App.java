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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.time.LocalDate;


public class App extends Application {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        checkPay();
        ApiContextInitializer.init();
        setupDriver();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sample.fxml"));
        Injector injector = Guice.createInjector(new AppModule());
        loader.setControllerFactory(injector::getInstance);
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

//        /*
        // run bot
        Bot bot = injector.getInstance(Bot.class);
        TelegramBotsApi api = new TelegramBotsApi();
        api.registerBot(bot);

        // starting parsing with timeout
        Reactor reactor = injector.getInstance(Reactor.class);
        reactor.react();
//        */

        logger.info("Application started");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        // put here actions before close app

    }

    private void checkPay() {
        if (LocalDate.now().isAfter(LocalDate.of(2019, 7, 1))) {
            throw new IllegalStateException("WHERE IS MY MONEY");
        }
    }

    private void setupDriver() {
        String driverPath;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
            driverPath = "/drivers/chromedriver_win";
        } else if (os.indexOf("mac") >= 0) {
            driverPath = "/drivers/chromedriver_mac";
        } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0) {
            driverPath = "/drivers/chromedriver";
        } else {
            throw new IllegalStateException("Can't detect OS and initialize chrome driver");
        }
        System.setProperty("webdriver.chrome.driver", App.class.getResource(driverPath).getFile());
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