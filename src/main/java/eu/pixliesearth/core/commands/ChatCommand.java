package eu.pixliesearth.core.commands;

import eu.pixliesearth.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatCommand implements CommandExecutor {

    private static final Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§aEARTH §8| §7No arguments given.");
            return false;
        }
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
        } else if (args[0].equalsIgnoreCase("blacklist")) {
            if (args.length == 1) {
                sender.sendMessage("§dCHAT §8| §7You have to enter the word you want to blacklist.");
                return false;
            }
            List<String> blacklist = new ArrayList<>(instance.getConfig().getStringList("modules.chat.blacklist"));
            if (blacklist.contains(args[1].toLowerCase())) {
                sender.sendMessage("§dCHAT §8| §7That word is already in the blacklist.");
                return false;
            }
            blacklist.add(args[1].toLowerCase());
            instance.getConfig().set("modules.chat.blacklist", blacklist);
            instance.saveConfig();
            instance.reloadConfig();
            sender.sendMessage("§dCHAT §8| §aSuccessfully §7added §b" + args[1] + " §7from the blacklist.");
        } else if (args[0].equalsIgnoreCase("whitelist")) {
            if (args.length == 1) {
                sender.sendMessage("§dCHAT §8| §7You have to enter the word you want to whitelist.");
                return false;
            }
            List<String> blacklist = new ArrayList<>(instance.getConfig().getStringList("modules.chat.blacklist"));
            if (!blacklist.contains(args[1].toLowerCase())) {
                sender.sendMessage("§dCHAT §8| §7That word is not in the blacklist.");
                return false;
            }
            blacklist.remove(args[1].toLowerCase());
            instance.getConfig().set("modules.chat.blacklist", blacklist);
            instance.saveConfig();
            instance.reloadConfig();
            sender.sendMessage("§dCHAT §8| §aSuccessfully §7removed §b" + args[1] + " §7from the blacklist.");
        }

        return false;
    }

}
