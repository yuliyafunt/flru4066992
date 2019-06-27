package flteam.flru4066992.core.conditions.sportspecific;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TennisConditions implements Conditions {

    public enum Type {
        SET_1("Сет 1"),
        SET_2("Сет 2"),
        SET_3("Сет 3"),
        SET_4("Сет 4"),
        SET_5("Сет 5"),
        MEN("Мужчины"),
        WOMEN("Женщины"),
        PLAYER_1("Игрок 1 забил"),
        PLAYER_2("Игрок 2 забил");

        public final String value;

        Type(String value) {
            this.value = value;
        }

        @Nullable
        public static TennisConditions find(String value) {
            for (TennisConditions.Type type : values()) {
                if (type.value.equals(value)) {
                    return new TennisConditions(type);
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private final TennisConditions.Type type;

    public TennisConditions(TennisConditions.Type type) {
        this.type = type;
    }

    @Override
    public TennisConditions.Type get() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TennisConditions that = (TennisConditions) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
