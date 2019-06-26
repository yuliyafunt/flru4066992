package flteam.flru4066992.model.time;

import org.jetbrains.annotations.Nullable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public interface Time {

    DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm");

    default String asString() {
        return Optional.ofNullable(getTime()).orElse("");
    }

    @Nullable
    String getTime();

    @Nullable
    Integer asInt();

    @Nullable
    default LocalTime asLocalTime() {
        if (getTime() != null) {
            try {
                return LocalTime.parse(getTime(), DEFAULT_TIME_FORMATTER);
            } catch (DateTimeParseException ignore) {
            }
        }
        return null;
    }

}
