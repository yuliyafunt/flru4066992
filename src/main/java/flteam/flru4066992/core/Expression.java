package com.core;

import static com.core.Operator.*;

public class Expression {

    private final Condition condition;
    private final String value;
    private final Operator operator;

    private Expression(Condition condition, String value, Operator operator) {
        this.condition = condition;
        this.value = value;
        this.operator = operator;
    }

    public Condition getCondition() {
        return condition;
    }

    public String getValue() {
        return value;
    }

    public Operator getOperator() {
        return operator;
    }

    public static Expression eq(Condition condition, String value) {
        return new Expression(condition, value, EQUALS);
    }

    public static Expression greater(Condition condition, String value) {
        return new Expression(condition, value, GT);
    }

    public static Expression greaterOrEq(Condition condition, String value) {
        return new Expression(condition, value, GTE);
    }

    public static Expression less(Condition condition, String value) {
        return new Expression(condition, value, LT);
    }

    public static Expression lessOrEq(Condition condition, String value) {
        return new Expression(condition, value, LTE);
    }
}
