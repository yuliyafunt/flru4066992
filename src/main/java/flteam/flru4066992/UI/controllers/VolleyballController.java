package flteam.flru4066992.UI.controllers;

import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.bot.Notifier;
import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.core.conditions.resolvers.ConditionResolver;
import flteam.flru4066992.model.Match;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class VolleyballController extends ConditionResolver {

    @Inject
    public VolleyballController(Context context, Notifier notifier) {
        super(context, notifier);
    }

    @Override
    public boolean resolve(Match match, Collection<Expression> expressions) {
        return false;
    }
}
