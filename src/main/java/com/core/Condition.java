package com.core;

public enum Condition {
    HOME_TEAM("Хозяева забили"),
    GUEST_TEAM("Гости забили"),
    COEFFICIENT("Коэффициент"),
    PERIOD("Период");

    public final String val;

    Condition(String val) {
        this.val = val;
    }

    public static Condition find(String value) {
        for (Condition condition : values()) {
            if (condition.val.equalsIgnoreCase(value)) {
                return condition;
            }
        }
        throw new IllegalStateException("Unknown condition: " + value);
    }
}
