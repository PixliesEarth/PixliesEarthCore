package eu.pixliesearth.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Timer {

    private final long expiry;
    private boolean ended;

    public Timer(Map<String, Object> map) {
        this.expiry = (long) map.get("expiry");
        this.ended = (boolean) map.get("ended");
    }

    public Map<String, Object> toMap() {
        Map<String, Object> returner = new HashMap<>();
        returner.put("expiry", expiry);
        returner.put("ended", ended);
        return returner;
    }

    public Timer(long duration) {
        this.expiry = System.currentTimeMillis() + duration;
    }

    public long getRemaining() {
        return expiry - System.currentTimeMillis();
    }

    public String getRemainingAsString() {
        return Methods.getTimeAsString(expiry - System.currentTimeMillis(), false);
    }

}
