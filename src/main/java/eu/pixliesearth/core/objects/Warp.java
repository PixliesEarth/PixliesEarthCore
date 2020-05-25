package eu.pixliesearth.core.objects;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.utils.FileManager;
import eu.pixliesearth.core.utils.Methods;
import eu.pixliesearth.core.utils.Timer;
import eu.pixliesearth.localization.Lang;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Warp {

    private String name;
    private SimpleLocation location;
    private Material item;

    public Warp(String name, SimpleLocation loc, String item) {
        this.name = name;
        this.location = loc;
        this.item = Material.valueOf(item);
    }

    public void serialize() {
        FileManager cfg = Main.getInstance().getWarpsCfg();

        cfg.getConfiguration().set(name + ".world", location.getWorld());
        cfg.getConfiguration().set(name + ".x", location.getX());
        cfg.getConfiguration().set(name + ".y", location.getY());
        cfg.getConfiguration().set(name + ".z", location.getZ());
        cfg.getConfiguration().set(name + ".pitch", location.getPitch());
        cfg.getConfiguration().set(name + ".yaw", location.getYaw());
        cfg.getConfiguration().set(name + ".item", item.name());

        List<String> nameList = new ArrayList<>(cfg.getConfiguration().getStringList("warplist"));
        nameList.add(name);
        cfg.getConfiguration().set("warplist", nameList);

        cfg.save();
        cfg.reload();
        Bukkit.getConsoleSender().sendMessage("§7Warp §b" + name + "§7 created at §b" + location.toString());
    }

    public void remove() {
        FileManager cfg = Main.getInstance().getWarpsCfg();
        List<String> nameList = new ArrayList<>(cfg.getConfiguration().getStringList("warplist"));
        nameList.remove(name);
        cfg.getConfiguration().set("warplist", nameList);
        cfg.getConfiguration().set(name, null);
        cfg.save();
        cfg.reload();
    }

    public static Warp get(String name) {
        FileManager cfg = Main.getInstance().getWarpsCfg();

        if (!cfg.getConfiguration().contains("warps." + name + ".world")) return null;

        String world = cfg.getConfiguration().getString("warps." + name + ".world");
        double x = cfg.getConfiguration().getDouble("warps." + name + ".x");
        double y = cfg.getConfiguration().getDouble("warps." + name + ".y");
        double z = cfg.getConfiguration().getDouble("warps." + name + ".z");
        float pitch = Float.parseFloat(cfg.getConfiguration().getString("warps." + name + ".pitch"));
        float yaw = Float.parseFloat(cfg.getConfiguration().getString("warps." + name + ".yaw"));
        String item = cfg.getConfiguration().getString("warps." + name + ".item");

        SimpleLocation loc = new SimpleLocation(world, x, z, y, pitch, yaw);
        return new Warp(name, loc, item);
    }

    public static List<Warp> getWarps() {
        FileManager cfg = Main.getInstance().getWarpsCfg();

        List<Warp> warps = new ArrayList<>();
        for (String s : cfg.getConfiguration().getStringList("warplist"))
            warps.add(get(s));
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
        Profile profile = instance.getProfile(player.getUniqueId());
        long cooldown = (long) Energy.calculateTime(player.getLocation(), location.toLocation());
        if (cooldown < 1.0)
            cooldown = (long) 1.0;
        Timer timer = new Timer(cooldown * 1000);
        profile.getTimers().put("teleport", timer);
        profile.save();
        player.sendMessage(Lang.YOU_WILL_BE_TPD.get(player).replace("%LOCATION%", name).replace("%TIME%", Methods.getTimeAsString(cooldown * 1000, true)));
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            if (profile.getTimers().containsKey("teleport")) {
                profile.getTimers().remove("teleport");
                profile.save();
                player.teleport(location.toLocation());
                Energy.take(instance.getProfile(player.getUniqueId()), Energy.calculateNeeded(player.getLocation(), location.toLocation()));
                player.sendMessage(Lang.TELEPORTATION_SUCESS.get(player).replace("%LOCATION%", name));
            }
        }, cooldown * 20);
    }

}
