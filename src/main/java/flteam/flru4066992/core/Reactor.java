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
import java.util.concurrent.*;

@Singleton
public class Reactor {
    private static final Logger logger = LoggerFactory.getLogger(Reactor.class);

//    private static final long TIMEOUT = TimeUnit.MINUTES.toMillis(1);
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(20); // debug only

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
        if (!started) {
            runParserThread();
            started = true;
            logger.info("Parser thread started");
        }
    }

    private void runParserThread() {
        new Thread(() -> {
            while (true) {
                try {
                    // TODO: call all parsers here and wait futures with timeout
                    Future<List<Match>> footballResult = executorService.submit(footballParser);
                    List<Match> matches = footballResult.get();
                    conditionResolver.accept(matches);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(TIMEOUT);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();
    }
}
