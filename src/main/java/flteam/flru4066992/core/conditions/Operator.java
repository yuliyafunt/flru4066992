package flteam.flru4066992.core.conditions;

import org.jetbrains.annotations.Nullable;

public enum Operator {
    EQUALS("="),
    GT(">"),
    LT("<"),
    GTE(">="),
    LTE("<=");

    public final String value;

    Operator(String value) {
        this.value = value;
    }

    @Nullable
    public static Operator find(String v) {
        for (Operator operator : Operator.values()) {
            if(operator.value.equals(v)){
                return operator;
            }
        }
        return null;
    }
}
