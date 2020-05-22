package eu.pixliesearth.core.utils;

import lombok.Data;
import org.bukkit.plugin.java.JavaPlugin;

@Data
public class Timer {

    private final long expiry;
    private JavaPlugin plugin;
    private boolean ended;

    public Timer(long duration, JavaPlugin plugin) {
        this.expiry = System.currentTimeMillis() + duration;
        this.plugin = plugin;
    }

    public long getRemaining() {
        return expiry - System.currentTimeMillis();
    }

}
