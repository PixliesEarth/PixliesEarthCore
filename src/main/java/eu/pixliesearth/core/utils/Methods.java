package eu.pixliesearth.core.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Methods {

    public static String generateId(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String getIp(Player player) {
        return player.getAddress().toString().substring(1).split(":")[0];
    }

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static boolean testPermission(CommandSender sender, String permission) {
        if (!(sender instanceof Player)) return true;
        return sender.hasPermission(permission);
    }

    public static double calculateDistance(double x1, double x2, double z1, double z2) {
        return Math.sqrt(Math.pow((x2 - z1), 2) + Math.pow((z2 - x1), 2));
    }

    public static String getTimeAsString(long duration, boolean useMilliseconds) {
        long r = (duration / 100);
        long sec = r / 10;
        if (useMilliseconds) {
            long millis = r % 10;
            return sec >= 60 ? format((int) sec) : sec + "." + millis + "s";
        } else {
            return sec >= 60 ? format((int) sec) : "00:" + (sec < 10 ? "0" + sec : sec);
        }
    }

    public static String format(int time) {
        int sec = time % 60;
        int min = time / 60 % 60;
        int h = time / 3600 % 24;

        return (h > 0 ? h + ":" : "") + (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
    }

}