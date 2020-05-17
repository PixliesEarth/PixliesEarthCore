package eu.pixliesearth.core.commands;

import eu.pixliesearth.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                sender.sendMessage("§aECONOMY §8| §7You have §2§l$§a" + Main.getEconomy().getBalance(player) + " §7on your account.");
                return false;
            } else {
                sender.sendMessage("§aECONOMY §8| §7Usage: §b/economy balance <player>");
                return false;
            }
        } else {
            if (!sender.hasPermission("earth.economy.balance.others")) {
                sender.sendMessage("§aECONOMY §8| §cInsufficient permissions.");
                return false;
            }
            if (Bukkit.getPlayerUniqueId(args[1]) == null) {
                sender.sendMessage("§aECONOMY §8| §7This player does §cnot §7exist.");
                return false;
            }
            UUID uuid = Bukkit.getPlayerUniqueId(args[1]);
            sender.sendMessage("§aECONOMY §8| §6" + args[1] + " §7balance is §2§l$§a" + Main.getEconomy().getBalance(Bukkit.getOfflinePlayer(uuid)));
        }
        return false;
    }

}
