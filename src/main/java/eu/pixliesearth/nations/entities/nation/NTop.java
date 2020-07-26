package eu.pixliesearth.nations.entities.nation;

import eu.pixliesearth.Main;
import eu.pixliesearth.nations.managers.NationManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
public class NTop extends BukkitRunnable {

    private final List<NTopProfile> topMap;

    public NTop() {
        this.topMap = new ArrayList<>();
        this.runTaskTimerAsynchronously(Main.getInstance(), 0, (20 * 60) * 5);
    }

    @Override
    public void run() {
        topMap.clear();
        List<Nation> nations = new ArrayList<>(NationManager.nations.values());
        nations.sort((o1, o2) -> o2.getPoints() - o1.getPoints());
        for (Nation nation : nations)
            topMap.add(new NTopProfile(nation.getName(), nation.getPoints()));
        System.out.println("§aNation-Top updated.");
    }

    @Data
    @AllArgsConstructor
    public class NTopProfile {

        private String name;
        private int points;

    }

}
