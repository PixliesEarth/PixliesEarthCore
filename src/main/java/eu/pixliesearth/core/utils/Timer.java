package eu.pixliesearth.core.utils;

import com.mongodb.BasicDBObject;

public class Timer extends BasicDBObject {

    private static final long serialVersionUID = 2105061907470199595L;

    private final long expiry;
    private boolean ended;

    public static final String ENDED = "ended";
    public static final String EXPIRY = "expiry";

    public Timer(long duration) {
        this.expiry = System.currentTimeMillis() + duration;
    }

    public long getRemaining() {
        return expiry - System.currentTimeMillis();
    }

    public boolean isEnded() {
        return getBoolean(ENDED);
    }

    public void setEnded(boolean ended) {
        put(ENDED, ended);
    }

    public long getExpiry() {
        return getLong(EXPIRY);
    }

}
