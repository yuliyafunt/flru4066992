package flteam.flru4066992.core.conditions;


import flteam.flru4066992.core.conditions.sportspecific.Conditions;

import java.util.Objects;

import static flteam.flru4066992.core.conditions.Operator.*;

public class Expression {

    private final Conditions condition;
    private final int value;
    private final Operator operator;

    private Expression(Conditions condition, int value, Operator operator) {
        this.condition = condition;
        this.value = value;
        this.operator = operator;
    }

    public Conditions getCondition() {
        return condition;
    }

    public int getValue() {
        return value;
    }

    public Operator getOperator() {
        return operator;
    }

    public static Expression eq(Conditions condition, int value) {
        return new Expression(condition, value, EQUALS);
    }

    public static Expression greater(Conditions condition, int value) {
        return new Expression(condition, value, GT);
    }

    public static Expression greaterOrEq(Conditions condition, int value) {
        return new Expression(condition, value, GTE);
    }

    public static Expression less(Conditions condition, int value) {
        return new Expression(condition, value, LT);
    }

    public static Expression lessOrEq(Conditions condition, int value) {
        return new Expression(condition, value, LTE);
    }

    @Override
    public String toString() {
        return condition + " " + operator.value + " " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expression that = (Expression) o;
        return condition == that.condition &&
                Objects.equals(value, that.value) &&
                operator == that.operator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, value, operator);
    }
}
