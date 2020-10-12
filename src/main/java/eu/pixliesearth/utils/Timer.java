package eu.pixliesearth.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Timer {

    private final long expiry;
    private boolean ended;

    public Timer(Map<String, String> map) {
        this.expiry = Long.parseLong(map.get("expiry"));
        this.ended = Boolean.parseBoolean(map.get("ended"));
    }

    public Timer(long expiry, boolean ended) {
        this.expiry = expiry;
        this.ended = ended;
    }

    public Map<String, String> toMap() {
        Map<String, String> returner = new HashMap<>();
        returner.put("expiry", Long.toString(expiry));
        returner.put("ended", Boolean.toString(ended));
        return returner;
    }

    public Timer(long duration) {
        this.expiry = System.currentTimeMillis() + duration;
    }

    public Timer addTime(long durationToAdd) {
        return new Timer(this.expiry + durationToAdd);
    }

    public long getRemaining() {
        return expiry - System.currentTimeMillis();
    }

    public String getRemainingAsString() {
        return Methods.getTimeAsString(expiry - System.currentTimeMillis(), false);
    }

    public boolean hasExpired() { return getRemaining() <= 0; }

}
