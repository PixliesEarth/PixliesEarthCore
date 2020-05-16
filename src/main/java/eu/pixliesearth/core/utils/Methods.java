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

    public static String getIp(Player player){ return player.getAddress().toString().substring(1).split(":")[0]; }

    public static String chat(String s) { return ChatColor.translateAlternateColorCodes('&', s); }

    public static boolean testPermission(CommandSender sender, String permission) {
        if (!(sender instanceof Player)) return true;
        return sender.hasPermission(permission);
    }

}
