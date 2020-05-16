package eu.pixliesearth.core.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BackupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!sender.hasPermission("earth.admin")) {
            sender.sendMessage("§aEARTH §8| §cInsufficient permissions");
            return false;
        }
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            sender.sendMessage("§7Backing up all profiles in the database...");
            for (Profile profile : Main.getInstance().getPlayerLists().profiles.values())
                profile.backup();
            sender.sendMessage("§aDone.");
            Bukkit.getConsoleSender().sendMessage("§bAll profiles backed up in database.");
        });
        return false;
    }
}
