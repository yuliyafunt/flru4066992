package flteam.flru4066992.core.conditions.resolvers;

import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.bot.Notifier;
import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.core.conditions.Operator;
import flteam.flru4066992.core.conditions.sportspecific.FootballConditions;
import flteam.flru4066992.model.Match;
import flteam.flru4066992.model.time.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class FootballResolver extends ConditionResolver {
    private static final Logger logger = LoggerFactory.getLogger(FootballResolver.class);

    @Inject
    public FootballResolver(Context context, Notifier notifier) {
        super(context, notifier);
    }

    @Override
    public boolean resolve(Match match, Collection<Expression> expressions) {
        boolean success = false;
        for (Expression expression : expressions) {
            if (expression.getCondition() instanceof FootballConditions) {
                FootballConditions condition = (FootballConditions) expression.getCondition();
                if (condition != null) {
                    switch (condition.get()) {
                        case HOME_GOAL:
                            success = resolveByScore(expression, match, false);
                            break;
                        case GUEST_GOAL:
                            success = resolveByScore(expression, match, true);
                            break;
                        case COEFFICIENT:
                            // TODO: i dont know we really need it or not
                            break;
                        case TIME:
                            success = resolveByPeriod(expression.getOperator(), expression.getValue(), match.getTime());
                            break;
                    }
                    if (!success) {
                        return false;
                    }
                }
            } else {
                logger.error("Football resolver can resolve only football conditions");
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
