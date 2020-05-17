package eu.pixliesearth.core.modules.economy;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.interfaces.Module;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomySystem implements Module, CommandExecutor {

    @Override
    public String name() {
        return "EconomySystem";
    }

    @Override
    public boolean enabled() {
        return config.getBoolean("modules.economy.enabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                sendBalance(player);
                return false;
            } else {
                sender.sendMessage("§7-= §bAdmin economy commands §7=-");
                sender.sendMessage("§8- §b/eco add <player> <amount>");
                sender.sendMessage("§8- §b/eco take <player> <amount>");
                sender.sendMessage("§8- §b/eco set <player> <amount>");
                sender.sendMessage("§8- §b/eco reset <player>");
            }
            return false;
        }
        if (args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    sendBalance((Player) sender);
                    return false;
                } else {
                    sender.sendMessage("§aECONOMY §8| §7Usage: §b/economy balance <player>");
                    return false;
                }
            }
        }
        if (args[0].equalsIgnoreCase("pay")) {
            Player player = (Player) sender;
            if (Bukkit.getPlayer(args[1]) == null) {
                player.sendMessage("§aECONOMY §8| §7Player is not online.");
                return false;
            }
            if (Bukkit.getPlayer(args[1]) == player) {
                player.sendMessage("§aECONOMY §8| §7Changed the pocket the money was in.");
                return false;
            }
            //TODO
        }
        return false;
    }

    private void sendBalance(Player player) {
        player.sendMessage("§aECONOMY §8| §7You have §2§l$§a" + Main.getEconomy().getBalance(player) + " §7on your account.");
    }

}
