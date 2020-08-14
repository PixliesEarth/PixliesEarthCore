package eu.pixliesearth.core.objects;

import eu.pixliesearth.Main;
import eu.pixliesearth.utils.FileManager;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import eu.pixliesearth.localization.Lang;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Data
public class Warp {

    private String name;
    private Location location;
    private Material item;

    public Warp(String name, Location loc, String item) {
        this.name = name;
        this.location = loc;
        this.item = Material.valueOf(item);
    }

    public void serialize() {
        FileManager cfg = Main.getInstance().getWarpsCfg();

        cfg.getConfiguration().set("warps." + name + ".location", location);
        cfg.getConfiguration().set("warps." + name + ".item", item.name());

        cfg.save();
        cfg.reload();
        Bukkit.getConsoleSender().sendMessage("§7Warp §b" + name + "§7 created at §b" + location.toString());
    }

    public void remove() {
        FileManager cfg = Main.getInstance().getWarpsCfg();
        cfg.getConfiguration().set("warps." + name, null);
        cfg.save();
        cfg.reload();
    }

    public static Warp get(String name) {
        FileManager cfg = Main.getInstance().getWarpsCfg();


        String item = cfg.getConfiguration().getString("warps." + name + ".item");
        Location location = cfg.getConfiguration().getLocation("warps." + name + ".location");

        return new Warp(name, location, item);
    }

    public static List<Warp> getWarps() {
        FileManager cfg = Main.getInstance().getWarpsCfg();

        List<Warp> warps = new ArrayList<>();
        for (String s : cfg.getConfiguration().getConfigurationSection("warps").getKeys(false)) {
            warps.add(get(s));
        }
        return warps;
    }

    @Deprecated
    public static boolean exists(String name) {
        for (Warp warp : getWarps())
            if (warp.getName().equalsIgnoreCase(name))
                return true;
        return false;
    }

    public void teleport(Player player) {
        Main instance = Main.getInstance();
        Profile profile = instance.getProfile(player.getUniqueId());
        profile.teleport(location, name);
    }

}
