package flteam.flru4066992.model.time;

import org.jetbrains.annotations.Nullable;

public class TennisTime implements Time {

    private String time;

    public TennisTime(String time) {
        this.time = time;
    }

    @Nullable
    @Override
    public String getTime() {
        return time;
    }

    @Nullable
    @Override
    public Integer asInt() {
        return null;
    }

}
