package eu.pixliesearth.core.objects;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.utils.CooldownMap;
import eu.pixliesearth.core.utils.FileManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Data
public class Warp {

    private String name;
    private SimpleLocation location;
    private Material item;

    public void serialize() {
        FileManager cfg = Main.getInstance().getWarpsCfg();

        cfg.getConfiguration().set(name + ".world", location.getWorld());
        cfg.getConfiguration().set(name + ".x", location.getX());
        cfg.getConfiguration().set(name + ".y", location.getY());
        cfg.getConfiguration().set(name + ".z", location.getZ());
        cfg.getConfiguration().set(name + ".pitch", location.getPitch());
        cfg.getConfiguration().set(name + ".yaw", location.getYaw());
        cfg.getConfiguration().set(name + ".item", item.name());

        cfg.save();
        cfg.reload();
        Bukkit.getConsoleSender().sendMessage("§7Warp §b" + name + "§7 created at §b" + location.toString());
    }

    public void remove() {
        FileManager cfg = Main.getInstance().getWarpsCfg();

        cfg.getConfiguration().set(name, null);
        cfg.save();
        cfg.reload();
    }

    public static Warp get(String name) {
        FileManager cfg = Main.getInstance().getWarpsCfg();

        if (!cfg.getConfiguration().contains("name")) return null;

        String world = cfg.getConfiguration().getString("warps." + name + ".world");
        double x = cfg.getConfiguration().getDouble("warps." + name + ".x");
        double y = cfg.getConfiguration().getDouble("warps." + name + ".y");
        double z = cfg.getConfiguration().getDouble("warps." + name + ".z");
        float pitch = (float) cfg.getConfiguration().get("warps." + name + ".pitch");
        float yaw = (float) cfg.getConfiguration().get("warps." + name + ".yaw");
        Material item = Material.valueOf(cfg.getConfiguration().getString("warps." + name + ".item"));

        SimpleLocation loc = new SimpleLocation(world, x, z, y, pitch, yaw);
        return new Warp(name, loc, item);
    }

    public static Set<Warp> getWarps() {
        FileManager cfg = Main.getInstance().getWarpsCfg();

        Set<Warp> warps = new HashSet<>();
        if (cfg.getConfiguration().getConfigurationSection("warps") == null) return warps;
        for (String s : cfg.getConfiguration().getConfigurationSection("warps").getKeys(false)) {
            warps.add(get(s));
        }
        return warps;
    }

    public static boolean exists(String name) {
        for (Warp warp : getWarps())
            if (warp.getName().equalsIgnoreCase(name))
                return true;
        return false;
    }

    public void teleport(Player player) {
        Main instance = Main.getInstance();
        FileConfiguration config = instance.getConfig();
        double cooldown = Energy.calculateTime(player.getLocation(), location.toLocation());
        if (cooldown < 1.0)
            cooldown = 1.0;
        player.sendMessage("§aEARTH §8| §7You will be teleported to §b" + name + " §7in §3" + cooldown + "§7!");
        if (cooldown != 0.0) {
            int taskId = 0;
            int finalTaskId = taskId;
            taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
                if (!instance.getPlayerLists().teleportCooldown.containsKey(player.getUniqueId())) {
                    Bukkit.getScheduler().cancelTask(finalTaskId);
                    return;
                }
                if (instance.getPlayerLists().teleportCooldown.get(player.getUniqueId()).getTimeLeft() - 0.1 == 0.0) {
                    Bukkit.getScheduler().cancelTask(instance.getPlayerLists().teleportCooldown.get(player.getUniqueId()).getTaskId());
                    instance.getPlayerLists().teleportCooldown.remove(player.getUniqueId());
                    player.teleport(location.toLocation());
                    Energy.take(instance.getProfile(player.getUniqueId()), Energy.calculateNeeded(player.getLocation(), location.toLocation()));
                    player.sendMessage("§aEARTH §8| §7You have been teleported to §b" + name + "§7!");
                    return;
                }
                instance.getPlayerLists().teleportCooldown.get(player.getUniqueId()).setTimeLeft(instance.getPlayerLists().teleportCooldown.get(player.getUniqueId()).getTimeLeft() - 0.1);
            }, 0L, 2);
            instance.getPlayerLists().teleportCooldown.put(player.getUniqueId(), new CooldownMap(cooldown, taskId));
        }
    }

}
