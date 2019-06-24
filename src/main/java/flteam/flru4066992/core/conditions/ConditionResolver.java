package flteam.flru4066992.core.conditions;

import flteam.flru4066992.core.BetType;
import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.Filter;
import flteam.flru4066992.core.bot.Notifier;
import flteam.flru4066992.model.Match;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class ConditionResolver {

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
                notifier.notify(context.getUsers(), filter.getExpressions(), filter.getComment());
            }
        }
    }

    private boolean resolve(Match match, Collection<Expression> expressions) {
        boolean success = false;
        for (Expression expression : expressions) {
            switch (expression.getCondition()) {
                case HOME_TEAM:
                    break;
                case GUEST_TEAM:
                    break;
                case COEFFICIENT:
                    break;
                case PERIOD:
                    success = resolveOperator(expression.getOperator(), expression.getValue(), match.getTime());
                    break;
            }
            if (!success) {
                return false;
            }
        }
        return true;
    }

    private boolean resolveOperator(Operator operator, String value, String period) {
        Integer expected = Integer.valueOf(value);
        Integer actual = Integer.valueOf(period);

        switch (operator) {
            case EQUALS:
                return expected.equals(actual);
            case GT:
                return actual > expected;
            case LT:
                return actual < expected;
            case GTE:
                return actual >= expected;
            case LTE:
                return actual <= expected;
            default:
                return false;
        }
    }
}
