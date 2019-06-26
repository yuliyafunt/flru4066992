package flteam.flru4066992.model.time;

import org.jetbrains.annotations.Nullable;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class FootballTime implements Time {

    private String time;

    public FootballTime(String time) {
        this.time = time;
    }

    @Nullable
    @Override
    public String getTime() {
        return time;
    }

    @Override
    @Nullable
    public Integer asInt() {
        try {
            return time.equalsIgnoreCase("перерыв") ? 45 : Integer.parseInt(time);
        } catch (NumberFormatException ignore) {
            return null;
        }
    }

    @Override
    @Nullable
    public LocalTime asLocalTime() {
        Integer intTime = asInt();
        if (intTime != null) {
            try {
                return LocalTime.of(intTime / 60, intTime % 60);
            } catch (DateTimeParseException ignore) {
            }
        }
        return null;
    }

}
