package eu.pixliesearth.core.utils;

import lombok.Data;

@Data
public class Timer {

    private final long expiry;
    private boolean ended;

    public Timer(long duration) {
        this.expiry = System.currentTimeMillis() + duration;
    }

    public long getRemaining() {
        return expiry - System.currentTimeMillis();
    }

}
