package com.core;

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

    public static Operator find(String v) {
        for (Operator operator : Operator.values()) {
            if(operator.value.equals(v)){
                return operator;
            }
        }
        throw new IllegalStateException("Unknown operator: " + v);
    }
}
