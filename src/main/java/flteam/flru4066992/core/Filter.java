package flteam.flru4066992.core;

import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.core.conditions.sportspecific.Conditions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Filter {

    private final Map<Conditions, Expression> expressions = new HashMap<>();
    private String comment;

    public void addExpression(Expression expression) {
        expressions.put(expression.getCondition(), expression);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Collection<Expression> getExpressions() {
        return expressions.values();
    }

    public String getComment() {
        return comment;
    }

    public void removeExpressions(Conditions condition) {
        expressions.remove(condition);
    }
}
