package flteam.flru4066992.core;

import flteam.flru4066992.core.conditions.Condition;
import flteam.flru4066992.core.conditions.Expression;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Filter {

    private final Map<Condition, Expression> expressions = new HashMap<>();
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

    public void removeExpressions(Condition condition) {
        expressions.remove(condition);
    }
}
