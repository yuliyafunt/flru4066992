package flteam.flru4066992.core;

import flteam.flru4066992.core.conditions.Expression;

import java.util.HashSet;
import java.util.Set;

public class Filter {

    private final Set<Expression> expressions = new HashSet<>();
    private String comment;

    public void addExpression(Expression expression) {
        expressions.add(expression);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<Expression> getExpressions() {
        return expressions;
    }

    public String getComment() {
        return comment;
    }
}
