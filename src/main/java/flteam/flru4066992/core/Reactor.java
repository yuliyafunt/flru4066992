package flteam.flru4066992.core;

import flteam.flru4066992.core.conditions.ConditionResolver;
import flteam.flru4066992.model.Match;
import flteam.flru4066992.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Singleton
public class Reactor {
    private static final Logger logger = LoggerFactory.getLogger(Reactor.class);

    private final Context context;
    private final Parser footballParser;
    private final ConditionResolver conditionResolver;

    private final ExecutorService executorService = Executors.newFixedThreadPool(6);

    private boolean started = false;

    @Inject
    public Reactor(Context context, @Named("Football") Parser footballParser, ConditionResolver conditionResolver) {
        this.context = context;
        this.footballParser = footballParser;
        this.conditionResolver = conditionResolver;
    }

    public void react() {
        if (started) {
            return;
        }
        runParserThread();
        started = true;
    }

    private void runParserThread() {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Future<List<Match>> footballResult = executorService.submit(footballParser);
                    List<Match> matches = footballResult.get();
                    conditionResolver.accept(matches);
                    Thread.sleep(60000);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        });
        thread.start();
    }
}
