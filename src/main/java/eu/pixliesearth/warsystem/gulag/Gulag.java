package eu.pixliesearth.warsystem.gulag;

import eu.pixliesearth.Main;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.warsystem.WarParticipant;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Gulag {

    private static final Main instance = Main.getInstance();

    private String spectatorLocation;
    private String fighterOne;
    private String fighterTwo;
    private Map<WarParticipant.WarSide, List<UUID>> players;
    private List<UUID> fighting;
    private Map<String, Timer> timers;

    public void addPlayer(Player player, WarParticipant.WarSide side) {
        if (fighting.contains(player.getUniqueId())) return;
        players.putIfAbsent(side, new ArrayList<>());
        players.get(side).add(player.getUniqueId());
        player.sendTitle("§b§lWelcome", "§7to the §cgulag", 20, 20 * 3, 20);
        instance.getUtilLists().inGulag.add(player.getUniqueId());
    }

    public void placeFighters() {
        final Player defender = Bukkit.getPlayer(players.get(WarParticipant.WarSide.DEFENDER).get(0));
        final Player aggressor = Bukkit.getPlayer(players.get(WarParticipant.WarSide.AGGRESSOR).get(0));
        players.get(WarParticipant.WarSide.DEFENDER).remove(0);
        players.get(WarParticipant.WarSide.AGGRESSOR).remove(0);
        if (defender == null) return;
        if (aggressor == null) return;
        fighting.add(defender.getUniqueId());
        fighting.add(aggressor.getUniqueId());
        defender.teleport(Methods.locationFromSaveableString(fighterOne));
        aggressor.teleport(Methods.locationFromSaveableString(fighterTwo));
        players.get(WarParticipant.WarSide.AGGRESSOR).remove(aggressor.getUniqueId());
        players.get(WarParticipant.WarSide.DEFENDER).remove(defender.getUniqueId());
        timers.put("gulagStart", new Timer(5000));
        BossBar bar = Bukkit.createBossBar("§7Starting in §b§l" + timers.get("gulagStart").getRemainingAsString(), BarColor.BLUE, BarStyle.SEGMENTED_10);
        bar.addPlayer(aggressor);
        bar.addPlayer(defender);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (timers.get("gulagStart").hasExpired()) {
                    defender.sendTitle("§c§lFIGHT!", "§7The gulag has started", 20, 20 * 3, 20);
                    aggressor.sendTitle("§c§lFIGHT!", "§7The gulag has started", 20, 20 * 3, 20);
                    timers.remove("gulagStart");
                    setKit(defender);
                    setKit(aggressor);
                    startGulag(aggressor, defender);
                    cancel();
                    return;
                }
                bar.setTitle("§7Starting in §b§l" + timers.get("gulagStart").getRemainingAsString());
                bar.setProgress(timers.get("gulagStart").getRemaining() / timers.get("gulagStart").getExpiry());
            }
        }.runTaskTimer(instance, 0, 20);
    }

    public void startGulag(Player aggressor, Player defender) {
        timers.put("gulagCooldown", new Timer(120_000));
        BossBar bar = Bukkit.createBossBar("§b" + timers.get("gulagCooldown").getRemainingAsString() + " until end", BarColor.RED, BarStyle.SEGMENTED_20);
        bar.addPlayer(aggressor);
        bar.addPlayer(defender);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!timers.containsKey("gulagCooldown")) {
                    cancel();
                    return;
                }
                if (timers.get("gulagCooldown").hasExpired()) {
                    if (defender.getHealth() > aggressor.getHealth()) {
                        handleKill(defender, aggressor);
                    } else {
                        handleKill(aggressor, defender);
                    }
                    return;
                }
                bar.setTitle("§b" + timers.get("gulagCooldown").getRemainingAsString() + " until end");
                bar.setProgress(timers.get("gulagCooldown").getRemaining() / timers.get("gulagCooldown").getExpiry());
            }
        }.runTaskTimer(instance, 0, 20);
    }

    public void handleKill(Player winner, Player loser) {
        winner.teleport(instance.getFastConf().getSpawnLocation());
        winner.getInventory().clear();
        timers.remove("gulagCooldown");
        winner.sendTitle("§b§lYou won!", "§7The gulag has ended", 20, 20 * 3, 20);
        fighting.clear();
        instance.getCurrentWar().handleKill(instance.getProfile(loser.getUniqueId()));
    }

    public void handleLeave(Player left) {
        if (!fighting.contains(left.getUniqueId()))
            return;
        timers.remove("gulagCooldown");
        Player winner = null;
        for (UUID uuid : fighting)
            if (!uuid.equals(left.getUniqueId()))
                winner = Bukkit.getPlayer(uuid);
        if (winner == null) return;
        winner.sendTitle("§b§lYou won!", "§7The gulag has ended", 20, 20 * 3, 20);
        fighting.clear();
    }

    public void setKit(Player player) {
        player.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
        player.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE));
        player.getInventory().setItem(2, new ItemStack(Material.DIAMOND_HELMET));
        player.getInventory().setItem(3, new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setItem(4, new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setItem(5, new ItemStack(Material.IRON_BOOTS));
    }

}
