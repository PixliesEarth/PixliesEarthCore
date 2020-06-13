package eu.pixliesearth.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    public static Material getSBWoolByCC(String chatColor) {
        switch (chatColor) {
            case "§b":
                return Material.LIGHT_BLUE_WOOL;
            case "§c":
                return Material.RED_WOOL;
            case "§e":
                return Material.YELLOW_WOOL;
            case "§a":
                return Material.GREEN_WOOL;
            case "§3":
                return Material.CYAN_WOOL;
        }
        return Material.CYAN_WOOL;
    }

    public static ChatColor getNeighbourColor(String color) {
        ChatColor cc = ChatColor.AQUA;
        switch (color) {
            case "§b":
                return ChatColor.DARK_AQUA;
            case "§c":
                return ChatColor.DARK_RED;
            case "§e":
                return ChatColor.GOLD;
            case "§a":
                return ChatColor.DARK_GREEN;
            case "§3":
                return ChatColor.BLUE;
        }
        return cc;
    }

    public String getProgressBar(double current_progress, double required_progress, ChatColor finished, ChatColor left) {
        double progress_percentage = current_progress / required_progress;
        StringBuilder sb = new StringBuilder();
        int bar_length = 20;
        for (int i = 0; i < bar_length; i++) {
            if (i < bar_length * progress_percentage) {
                sb.append(finished).append("▇");
            } else {
                sb.append(left).append("▇");
            }
        }
       return sb.toString();
    }

}