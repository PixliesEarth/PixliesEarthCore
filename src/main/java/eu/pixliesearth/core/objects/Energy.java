package eu.pixliesearth.core.objects;

import eu.pixliesearth.core.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Energy {

    public static boolean take(Profile profile, double amount) {
        if (profile.getEnergy() - amount < 0) {
            return false;
        }
        profile.setEnergy(profile.getEnergy() - amount);
        profile.backup();
        if (Bukkit.getPlayer(profile.getUniqueId()) != null)
            Bukkit.getPlayer(profile.getUniqueId()).sendMessage("§eYou lost §a" + amount + " §eenergy.");
        return true;
    }

    public static void add(Profile profile, double amount) {
        profile.setEnergy(profile.getEnergy() + amount);
        profile.backup();
        if (Bukkit.getPlayer(profile.getUniqueId()) != null)
            Bukkit.getPlayer(profile.getUniqueId()).sendMessage("§eYou lost §a" + amount + " §eenergy.");
    }

    public static double calculateNeeded(Location a, Location b) {
        return Methods.calculateDistance(a.getX(), b.getX(), a.getZ(), b.getZ()) / 2500;
    }

    public static double calculateTime(Location a, Location b) {
        return Methods.calculateDistance(a.getX(), b.getX(), a.getZ(), b.getZ()) / 1000;
    }

}
