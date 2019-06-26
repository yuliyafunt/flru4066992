package flteam.flru4066992.model.time;

import org.jetbrains.annotations.Nullable;

public class BasketballTime implements Time {

    private String time;

    public BasketballTime(String time) {
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
