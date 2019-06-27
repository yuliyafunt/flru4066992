package flteam.flru4066992.core.conditions.resolvers;

import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.bot.Notifier;
import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.model.Match;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class VolleyballResolver extends ConditionResolver {

    @Inject
    public VolleyballResolver(Context context, Notifier notifier) {
        super(context, notifier);
    }

    @Override
    public boolean resolve(Match match, Collection<Expression> expressions) {
        return false;
    }
}
