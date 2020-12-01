package eu.pixliesearth.core.objects;

import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class Energy {

    public static boolean take(Profile profile, double amount) {
        if (profile.getEnergy() - amount < 0) {
            return false;
        }
        profile.setEnergy(profile.getEnergy() - amount);
        profile.save();
        if (Bukkit.getPlayer(profile.getUniqueId()) != null)
            Bukkit.getPlayer(profile.getUniqueId()).sendMessage("§eYou lost §a" + amount + " §eenergy.");
        return true;
    }

    public static void add(Profile profile, double amount) {
        if (profile.getEnergy() + amount > 5D) {
            profile.setEnergy(5);
        } else {
            profile.setEnergy(profile.getEnergy() + amount);
        }
        profile.save();
        if (Bukkit.getPlayer(UUID.fromString(profile.getUniqueId())) != null)
            Bukkit.getPlayer(UUID.fromString(profile.getUniqueId())).sendActionBar("§eYou gained §a" + amount + " §eenergy.");
    }

    public static double calculateNeeded(Location a, Location b) {
        // return Methods.calculateDistance(a.getBlockX(), b.getBlockX(), a.getBlockZ(), b.getBlockZ()) / 2000;
        double needed = Methods.calculateDistance(a, b) / 2000;
        if (needed < 0.2) needed = 0.2;
        return needed;
    }

    public static double calculateTime(Location a, Location b) {
        // return Methods.calculateDistance(a.getBlockX(), b.getBlockX(), a.getBlockZ(), b.getBlockZ()) / 500;
        return Methods.calculateDistance(a, b) / 200;
    }

}
