package flteam.flru4066992.core.conditions;

import flteam.flru4066992.core.BetType;
import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.Filter;
import flteam.flru4066992.core.bot.Notifier;
import flteam.flru4066992.model.Match;
import flteam.flru4066992.model.time.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class ConditionResolver {
    private static final Logger logger = LoggerFactory.getLogger(ConditionResolver.class);

    private final Context context;
    private final Notifier notifier;

    @Inject
    public ConditionResolver(Context context, Notifier notifier) {
        this.context = context;
        this.notifier = notifier;
    }

    public synchronized void accept(Collection<Match> matches) {
        Filter filter = context.getFilters(BetType.FOOTBALL);
        if (filter == null) {
            // no filter so no notifications
            return;
        }
        for (Match match : matches) {
            if (resolve(match, filter.getExpressions())) {
                notifier.notify(context.getUsers(), match, filter.getExpressions(), filter.getComment());
            }
        }
    }

    private boolean resolve(Match match, Collection<Expression> expressions) {
        boolean success = false;
        for (Expression expression : expressions) {
            switch (expression.getCondition()) {
                case HOME_TEAM:
                    success = resolveByScore(expression, match, false);
                    break;
                case GUEST_TEAM:
                    success = resolveByScore(expression, match, true);
                    break;
                case COEFFICIENT:
                    // TODO: i dont know we really need it or not
                    break;
                case PERIOD:
                    success = resolveByPeriod(expression.getOperator(), expression.getValue(), match.getTime());
                    break;
            }
            if (!success) {
                return false;
            }
        }
        return true;
    }

    private boolean resolveByScore(Expression expression, Match match, boolean guestTeam) {
        int expectedValue = expression.getValue();
        switch (expression.getOperator()) {
            case EQUALS:
                return guestTeam ? match.getAwayTeam().getScore() == expectedValue : match.getHomeTeam().getScore() == expectedValue;
            case GT:
                return guestTeam ? match.getAwayTeam().getScore() > expectedValue : match.getHomeTeam().getScore() > expectedValue;
            case LT:
                return guestTeam ? match.getAwayTeam().getScore() < expectedValue : match.getHomeTeam().getScore() < expectedValue;
            case GTE:
                return guestTeam ? match.getAwayTeam().getScore() >= expectedValue : match.getHomeTeam().getScore() >= expectedValue;
            case LTE:
                return guestTeam ? match.getAwayTeam().getScore() <= expectedValue : match.getHomeTeam().getScore() <= expectedValue;
            default:
                throw new IllegalStateException("Unknown operator: " + expression.getOperator());
        }
    }

    private boolean resolveByPeriod(Operator operator, int expected, Time time) {
        Integer period = time.asInt();
        if (period != null) {
            switch (operator) {
                case EQUALS:
                    return expected == period;
                case GT:
                    return period > expected;
                case LT:
                    return period < expected;
                case GTE:
                    return period >= expected;
                case LTE:
                    return period <= expected;
                default:
                    logger.error("Unknown operator: {}", operator);
                    return false;
            }
        }
        return false;
    }
}
