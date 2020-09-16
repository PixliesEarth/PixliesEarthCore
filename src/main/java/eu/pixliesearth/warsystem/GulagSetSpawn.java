package eu.pixliesearth.warsystem;

import eu.pixliesearth.localization.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class GulagSetSpawn implements CommandExecutor {
    File file = new File("plugins/PixliesEarthCore", "gulag.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("gulag.command.setspawn")) {
            p.sendMessage(Lang.NO_PERMISSIONS.get(p));
            return false;
        }
        if (args.length == 0) {
            p.sendMessage(Lang.WRONG_USAGE.get(p).replace("%USAGE%", "/gulagsetspawn <spectator/fighter1/fighter2/cap>"));
            return false;
        }
        if (args[0].equalsIgnoreCase("spectator")) {
            String world = p.getWorld().getName();
            double x = p.getLocation().getX();
            double y = p.getLocation().getY();
            double z = p.getLocation().getZ();
            double yaw = p.getLocation().getYaw();
            double pitch = p.getLocation().getPitch();

            cfg.set("spectatorspawn" + ".world", world);
            cfg.set("spectatorspawn" + ".x", x);
            cfg.set("spectatorspawn" + ".y", y);
            cfg.set("spectatorspawn" + ".z", z);
            cfg.set("spectatorspawn" + ".yaw", yaw);
            cfg.set("spectatorspawn" + ".pitch", pitch);
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            p.sendMessage(Lang.GULAG_SET_SPECTATOR_SPAWN.get(p));
            return false;
        }else if(args[0].equalsIgnoreCase("fighter1")){
            String world = p.getWorld().getName();
            double x = p.getLocation().getX();
            double y = p.getLocation().getY();
            double z = p.getLocation().getZ();
            double yaw = p.getLocation().getYaw();
            double pitch = p.getLocation().getPitch();

            cfg.set("fighter1" + ".world", world);
            cfg.set("fighter1" + ".x", x);
            cfg.set("fighter1" + ".y", y);
            cfg.set("fighter1" + ".z", z);
            cfg.set("fighter1" + ".yaw", yaw);
            cfg.set("fighter1" + ".pitch", pitch);
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            p.sendMessage(Lang.GULAG_SET_FIGHTER_SPAWN1.get(p));
            return false;

        }else if(args[0].equalsIgnoreCase("fighter2")){
            String world = p.getWorld().getName();
            double x = p.getLocation().getX();
            double y = p.getLocation().getY();
            double z = p.getLocation().getZ();
            double yaw = p.getLocation().getYaw();
            double pitch = p.getLocation().getPitch();

            cfg.set("fighter2" + ".world", world);
            cfg.set("fighter2" + ".x", x);
            cfg.set("fighter2" + ".y", y);
            cfg.set("fighter2" + ".z", z);
            cfg.set("fighter2" + ".yaw", yaw);
            cfg.set("fighter2" + ".pitch", pitch);
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            p.sendMessage(Lang.GULAG_SET_FIGHTER_SPAWN2.get(p));
            return false;
        }else if(args[0].equalsIgnoreCase("cap")){
            String world = p.getWorld().getName();
            double x = p.getLocation().getX();
            double y = p.getLocation().getY();
            double z = p.getLocation().getZ();

            cfg.set("cap" + ".world", world);
            cfg.set("cap" + ".x", x);
            cfg.set("cap" + ".y", y);
            cfg.set("cap" + ".z", z);
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            p.sendMessage(Lang.GULAG_SET_CAP.get(p));
            return false;
        }
        return false;
    }

}
