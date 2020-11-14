package eu.pixliesearth.warsystem.gulag;

import eu.pixliesearth.Main;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.warsystem.WarParticipant;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class Gulag {

    private static final Main instance = Main.getInstance();

    private Location spectatorLocation;
    private Location fighterOne;
    private Location fighterTwo;
    private Map<WarParticipant.WarSide, List<UUID>> players;
    private List<UUID> fighting;
    private Map<String, Timer> timers;

    public void addPlayer(Player player, WarParticipant.WarSide side) {
        if (fighting.contains(player.getUniqueId())) return;
        player.teleport(spectatorLocation);
        players.get(side).add(player.getUniqueId());
        player.sendTitle("§b§lWelcome", "§7to the §cgulag", 20, 20 * 3, 20);
        instance.getUtilLists().inGulag.add(player.getUniqueId());
    }

    public void placeFighters() {
        Player defender = Bukkit.getPlayer(players.get(WarParticipant.WarSide.DEFENDER).get(0));
        Player aggressor = Bukkit.getPlayer(players.get(WarParticipant.WarSide.AGGRESSOR).get(0));
        if (defender == null) return;
        if (aggressor == null) return;
        defender.teleport(fighterOne);
        aggressor.teleport(fighterTwo);
        players.get(WarParticipant.WarSide.AGGRESSOR).remove(aggressor.getUniqueId());
        players.get(WarParticipant.WarSide.DEFENDER).remove(defender.getUniqueId());
        timers.put("gulagStart", new Timer(5000));
        BossBar bar = Bukkit.createBossBar("§7Starting in §b§l" + timers.get("gulagStart").getRemainingAsString(), BarColor.BLUE, BarStyle.SEGMENTED_10);
        bar.addPlayer(aggressor);
        bar.addPlayer(defender);
        new BukkitRunnable() {
            @Override
            public void run() {
                bar.setTitle("§7Starting in §b§l" + timers.get("gulagStart").getRemainingAsString());
                bar.setProgress(timers.get("gulagStart").getRemaining() / timers.get("gulagStart").getExpiry());
                if (timers.get("gulagStart").hasExpired()) {
                    defender.sendTitle("§c§lFIGHT!", "§7The gulag has started", 20, 20 * 3, 20);
                    aggressor.sendTitle("§c§lFIGHT!", "§7The gulag has started", 20, 20 * 3, 20);
                    timers.remove("gulagStart");
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(instance, 0, 20);
    }

}
