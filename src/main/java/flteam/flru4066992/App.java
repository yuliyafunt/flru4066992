package flteam.flru4066992;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import flteam.flru4066992.core.Reactor;
import flteam.flru4066992.core.bot.Bot;
import flteam.flru4066992.core.conditions.resolvers.*;
import flteam.flru4066992.parser.Browser;
import flteam.flru4066992.parser.Parser;
import flteam.flru4066992.parser.impl.*;
import javafx.application.Application;
import javafx.application.Platform;
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

    private static final boolean DEBUG = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        checkPay();
        ApiContextInitializer.init();
        setupDriver();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sample.fxml"));
        Injector injector = Guice.createInjector(new AppModule());
        loader.setControllerFactory(injector::getInstance);
        Parent root = loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            Browser.close();
            logger.info("Shutdown successful");
            System.exit(0);
        });


        if (!DEBUG) {
            // run bot
            Bot bot = injector.getInstance(Bot.class);
            TelegramBotsApi api = new TelegramBotsApi();
            api.registerBot(bot);

            // starting parsing with timeout
            Reactor reactor = injector.getInstance(Reactor.class);
            reactor.react();
        }

        logger.info("Application started");
    }

    private void checkPay() {
        if (LocalDate.now().isAfter(LocalDate.of(2019, 7, 1))) {
            throw new IllegalStateException("WHERE IS MY MONEY");
        }
    }

    private void setupDriver() {
        String driverPath;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            driverPath = "/drivers/chromedriver_win.exe";
        } else if (os.contains("mac")) {
            driverPath = "/drivers/chromedriver_mac";
        } else if (os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0) {
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

            bind(ConditionResolver.class).annotatedWith(Names.named("FResolver")).to(FootballResolver.class);
            bind(ConditionResolver.class).annotatedWith(Names.named("TResolver")).to(TennisResolver.class);
            bind(ConditionResolver.class).annotatedWith(Names.named("BResolver")).to(BasketResolver.class);
            bind(ConditionResolver.class).annotatedWith(Names.named("HResolver")).to(HockeyResolver.class);
            bind(ConditionResolver.class).annotatedWith(Names.named("VResolver")).to(VolleyballResolver.class);
            bind(ConditionResolver.class).annotatedWith(Names.named("GResolver")).to(HandballResolver.class);
        }
    }
}