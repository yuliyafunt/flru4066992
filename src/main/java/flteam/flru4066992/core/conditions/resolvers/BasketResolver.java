package flteam.flru4066992.core.conditions.resolvers;

import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.bot.Notifier;
import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.model.Match;

import javax.inject.Inject;
import java.util.Collection;

public class BasketResolver extends ConditionResolver {

    @Inject
    public BasketResolver(Context context, Notifier notifier) {
        super(context, notifier);
    }

    @Override
    public boolean resolve(Match match, Collection<Expression> expressions) {
        return false;
    }
}
