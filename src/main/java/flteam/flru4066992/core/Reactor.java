package flteam.flru4066992.core;

import flteam.flru4066992.core.conditions.resolvers.ConditionResolver;
import flteam.flru4066992.parser.Parser;
import flteam.flru4066992.parser.ParserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;

@Singleton
public class Reactor {
    private static final Logger logger = LoggerFactory.getLogger(Reactor.class);

    //    private static final long TIMEOUT = TimeUnit.MINUTES.toMillis(1);
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(20); // debug only

    private final Context context;

    private final Parser footballParser;
    private final Parser tennisParser;
    private final Parser hockeyParser;
    private final Parser basketParser;
    private final Parser volleyballParser;
    private final Parser handballParser;

    private final ConditionResolver footballResolver;
    private final ConditionResolver tennisResolver;
    private final ConditionResolver basketResolver;
    private final ConditionResolver hockeyResolver;
    private final ConditionResolver volleyResolver;
    private final ConditionResolver handballResolver;

    private final ExecutorService executorService = Executors.newFixedThreadPool(6);

    private boolean started = false;

    private final Collection<Callable<ParserResult>> invokers = new ArrayList<>(6);

    @Inject
    public Reactor(
            Context context,
            /* parsers */
            @Named("Football") Parser footballParser,
            @Named("Tennis") Parser tennisParser,
            @Named("Hockey") Parser hockeyParser,
            @Named("BasketBall") Parser basketParser,
            @Named("Volleyball") Parser volleyballParser,
            @Named("Handball") Parser handballParser,


            /* resolvers */

            @Named("FResolver") ConditionResolver footballResolver,
            @Named("TResolver") ConditionResolver tennisResolver,
            @Named("BResolver") ConditionResolver basketResolver,
            @Named("HResolver") ConditionResolver hockeyResolver,
            @Named("VResolver") ConditionResolver volleyResolver,
            @Named("GResolver") ConditionResolver handballResolver
    ) {
        this.context = context;

        this.footballParser = footballParser;
        this.tennisParser = tennisParser;
        this.hockeyParser = hockeyParser;
        this.basketParser = basketParser;
        this.volleyballParser = volleyballParser;
        this.handballParser = handballParser;

        this.footballResolver = footballResolver;
        this.tennisResolver = tennisResolver;
        this.basketResolver = basketResolver;
        this.hockeyResolver = hockeyResolver;
        this.volleyResolver = volleyResolver;
        this.handballResolver = handballResolver;

        this.invokers.add(footballParser);
        this.invokers.add(tennisParser);
        this.invokers.add(hockeyParser);
        this.invokers.add(basketParser);
        this.invokers.add(volleyballParser);
        this.invokers.add(handballParser);
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
//                    List<Future<ParserResult>> futures = executorService.invokeAll(invokers);
//                    while (!futures.isEmpty()) {
//                        Iterator<Future<ParserResult>> iterator = futures.iterator();
//                        while (iterator.hasNext()) {
//                            Future<ParserResult> future = iterator.next();
                    for (Callable<ParserResult> invoker : invokers) {
                        Future<ParserResult> future = executorService.submit(invoker);
                        try {
                            ParserResult result = future.get(20, TimeUnit.SECONDS);
                            switch (result.getType()) {
                                case FOOTBALL:
                                    footballResolver.accept(result.getMatches());
                                    break;

                                case TENNIS:
                                    tennisResolver.accept(result.getMatches());
                                    break;

                                case HOCKEY:
                                    hockeyResolver.accept(result.getMatches());
                                    break;

                                case VOLLEYBALL:
                                    volleyResolver.accept(result.getMatches());
                                    break;

                                case HANDBALL:
                                    handballResolver.accept(result.getMatches());
                                    break;

                                case BASKETBALL:
                                    basketResolver.accept(result.getMatches());
                                    break;

                                default:
                                    logger.error("Unknown type: " + result.getType());
                            }
                        } catch (TimeoutException e) {
                            // can't parse matches, don't worry next time we will success
                        } finally {
//                                iterator.remove();
                        }
                    }

//                        }
//                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(TIMEOUT);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }
            }
        }).start();
    }
}
