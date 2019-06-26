package flteam.flru4066992.core.conditions;

import org.jetbrains.annotations.Nullable;

public enum Condition {
    HOME_TEAM("Хозяева забили"),
    GUEST_TEAM("Гости забили"),
    COEFFICIENT("Коэффициент"),
    PERIOD("Период");

    public final String val;

    Condition(String val) {
        this.val = val;
    }

    @Nullable
    public static Condition find(String value) {
        for (Condition condition : values()) {
            if (condition.val.equalsIgnoreCase(value)) {
                return condition;
            }
        }
        return null;
    }
}
