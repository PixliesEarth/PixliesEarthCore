package eu.pixliesearth.core.commands.util;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            instance.getConfig().set("modules.chatsystem.muted", true);
            instance.saveConfig();
            instance.reloadConfig();
            String name = "";
            if (!(sender instanceof Player)) {
                name = "Console";
            } else {
                name = sender.getName();
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("%PLAYER%", name);
            Lang.CHAT_MUTED.broadcast(map);
        } else if (args[0].equalsIgnoreCase("unmute")) {
            if (!instance.getConfig().getBoolean("modules.chatsystem.muted")) {
                sender.sendMessage("§dCHAT §8| §7The chat is not muted.");
                return false;
            }
            instance.getConfig().set("modules.chatsystem.muted", false);
            instance.saveConfig();
            instance.reloadConfig();
            String name = "";
            if (!(sender instanceof Player)) {
                name = "Console";
            } else {
                name = sender.getName();
            }
            Map<String, String> map = new HashMap<>();
            map.put("%PLAYER%", name);
            Lang.CHAT_UNMUTED.broadcast(map);
        } else if (args[0].equalsIgnoreCase("blacklist")) {
            if (args.length == 1) {
                sender.sendMessage("§dCHAT §8| §7You have to enter the word you want to blacklist.");
                return false;
            }
            List<String> blacklist = new ArrayList<>(instance.getConfig().getStringList("modules.chatsystem.blacklist"));
            if (blacklist.contains(args[1].toLowerCase())) {
                sender.sendMessage("§dCHAT §8| §7That word is already in the blacklist.");
                return false;
            }
            blacklist.add(args[1].toLowerCase());
            instance.getConfig().set("modules.chatsystem.blacklist", blacklist);
            instance.saveConfig();
            instance.reloadConfig();
            sender.sendMessage("§dCHAT §8| §aSuccessfully §7added §b" + args[1] + " §7from the blacklist.");
        } else if (args[0].equalsIgnoreCase("whitelist")) {
            if (args.length == 1) {
                sender.sendMessage("§dCHAT §8| §7You have to enter the word you want to whitelist.");
                return false;
            }
            List<String> blacklist = new ArrayList<>(instance.getConfig().getStringList("modules.chatsystem.blacklist"));
            if (!blacklist.contains(args[1].toLowerCase())) {
                sender.sendMessage("§dCHAT §8| §7That word is not in the blacklist.");
                return false;
            }
            blacklist.remove(args[1].toLowerCase());
            instance.getConfig().set("modules.chatsystem.blacklist", blacklist);
            instance.saveConfig();
            instance.reloadConfig();
            sender.sendMessage("§dCHAT §8| §aSuccessfully §7removed §b" + args[1] + " §7from the blacklist.");
        } else if (args[0].equalsIgnoreCase("clear")) {
            for (int i = 0; i < 50; i++)
                for (Player player : Bukkit.getOnlinePlayers())
                    player.sendMessage(" ");
            Bukkit.broadcastMessage("§7Chat cleared by §6" + sender.getName() + "§7.");
        }

        return false;
    }

}
