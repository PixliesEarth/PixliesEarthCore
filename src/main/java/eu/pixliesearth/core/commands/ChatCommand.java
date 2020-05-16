package eu.pixliesearth.core.commands;

import eu.pixliesearth.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {

    private static Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        boolean allowed = false;
        if (!(sender instanceof Player)) allowed = true;
        if (sender.hasPermission("earth.admin")) allowed = true;
        if (!allowed) {
            sender.sendMessage("§aEARTH §8| §cInsufficient permissions.");
            return false;
        }
        if (args[0].equalsIgnoreCase("mute")) {
            if (instance.getConfig().getBoolean("modules.chatsystem.muted")) {
                sender.sendMessage("§dCHAT §8| §7The chat is already muted.");
                return false;
            }
            instance.getConfig().set("modules.chatystem.muted", true);
            instance.saveConfig();
            instance.reloadConfig();
            String name = "";
            if (!(sender instanceof Player)) {
                name = "Console";
            } else {
                name = sender.getName();
            }
            Bukkit.broadcastMessage("§dCHAT §8| §7The chat has been muted by §b" + name + "§7.");
        } else if (args[0].equalsIgnoreCase("unmute")) {
            if (!instance.getConfig().getBoolean("modules.chatsystem.muted")) {
                sender.sendMessage("§dCHAT §8| §7The chat is not muted.");
                return false;
            }
            instance.getConfig().set("modules.chatystem.muted", false);
            instance.saveConfig();
            instance.reloadConfig();
            String name = "";
            if (!(sender instanceof Player)) {
                name = "Console";
            } else {
                name = sender.getName();
            }
            Bukkit.broadcastMessage("§dCHAT §8| §7The chat has been unmuted by §b" + name + "§7.");
        }

        return false;
    }

}
