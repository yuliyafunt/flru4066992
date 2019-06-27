package flteam.flru4066992.core.conditions.sportspecific;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FootballConditions implements Conditions {

    public enum Type {
        HOME_GOAL("Хозяева забили"),
        GUEST_GOAL("Гости забили"),
        COEFFICIENT("Коэффициент"),
        TIME("Время");

        public final String value;

        Type(String value) {
            this.value = value;
        }

        @Nullable
        public static FootballConditions find(String value) {
            for (Type type : values()) {
                if (type.value.equals(value)) {
                    return new FootballConditions(type);
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private final Type type;

    public FootballConditions(Type type) {
        this.type = type;
    }

    @Override
    public Type get() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FootballConditions that = (FootballConditions) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
