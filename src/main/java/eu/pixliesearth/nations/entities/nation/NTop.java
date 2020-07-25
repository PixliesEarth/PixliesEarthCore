package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.Main;
import eu.pixliesearth.nations.managers.NationManager;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
public class NTop extends BukkitRunnable {

    private final List<String> topMap;

    public NTop() {
        this.topMap = new ArrayList<>();
        this.runTaskTimerAsynchronously(Main.getInstance(), 0, (20 * 60) * 5);
    }

    @Override
    public void run() {
        List<Nation> nations = new ArrayList<>(NationManager.nations.values());
        nations.sort((o1, o2) -> o2.getPoints() - o1.getPoints());
        for (Nation nation : nations)
            topMap.add(nation.getNationId());
    }

}
