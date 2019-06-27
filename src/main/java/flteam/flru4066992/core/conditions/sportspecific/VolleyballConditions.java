package flteam.flru4066992.core.conditions.sportspecific;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class VolleyballConditions implements Conditions {

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
        public static VolleyballConditions find(String value) {
            for (VolleyballConditions.Type type : values()) {
                if (type.value.equals(value)) {
                    return new VolleyballConditions(type);
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private final VolleyballConditions.Type type;

    public VolleyballConditions(VolleyballConditions.Type type) {
        this.type = type;
    }

    @Override
    public VolleyballConditions.Type get() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolleyballConditions that = (VolleyballConditions) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
