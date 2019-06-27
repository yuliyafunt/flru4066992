package flteam.flru4066992.core.conditions.resolvers;

import flteam.flru4066992.core.BetType;
import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.Filter;
import flteam.flru4066992.core.bot.Notifier;
import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;


public abstract class ConditionResolver {
    private static final Logger logger = LoggerFactory.getLogger(ConditionResolver.class);

    private final Context context;
    private final Notifier notifier;

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
            for (Collection<Expression> expression : filter.getExpressions()) {
                if (resolve(match, expression)) {
                    notifier.notify(context.getUsers(), match, expression, filter.getComment());
                }
            }

        }
    }

    public abstract boolean resolve(Match match, Collection<Expression> expressions);
}
