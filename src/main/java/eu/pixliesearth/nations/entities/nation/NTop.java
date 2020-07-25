package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.Main;
import eu.pixliesearth.nations.managers.NationManager;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
public class NTop extends BukkitRunnable {

    private final Map<Integer, String> topMap;

    public NTop() {
        this.topMap = new HashMap<>();
        this.runTaskTimerAsynchronously(Main.getInstance(), 0, (20 * 60) * 5);
    }

    @Override
    public void run() {
        topMap.clear();
        for (Nation nation : NationManager.nations.values()) {
            
        }
    }

}
