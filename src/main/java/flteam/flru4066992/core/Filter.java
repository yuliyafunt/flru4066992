package flteam.flru4066992.core;

import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.core.conditions.sportspecific.Conditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Filter {

    private final Map<String, Map<Conditions, Expression>> expressions = new HashMap<>();
    private String comment;

    public void addExpression(String tabId, Expression expression) {
        expressions.computeIfAbsent(tabId, k -> new HashMap<>())
                .put(expression.getCondition(), expression);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Collection<Collection<Expression>> getExpressions() {
        Collection<Map<Conditions, Expression>> values = expressions.values();
        Collection<Collection<Expression>> setsOfExpressions = new ArrayList<>();
        values.forEach(v -> setsOfExpressions.add(v.values()));
        return setsOfExpressions;
    }

    public String getComment() {
        return comment;
    }

    public void removeExpressions(String tabId, Conditions condition) {
        expressions.get(tabId).remove(condition);
    }
}
