package eu.pixliesearth.core.commands.economy;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                sender.sendMessage(Lang.BALANCE_YOU.get(player).replace("%BALANCE%", Main.getEconomy().getBalance(player) + ""));
            } else {
                sender.sendMessage("§aECONOMY §8| §7Usage: §b/economy balance <player>");
            }
            return false;
        } else {
            if (!sender.hasPermission("earth.economy.balance.others")) {
                sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                return false;
            }
            if (Bukkit.getPlayerUniqueId(args[0]) == null) {
                sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                return false;
            }
            UUID uuid = Bukkit.getPlayerUniqueId(args[0]);
            sender.sendMessage(Lang.BALANCE_OTHERS.get(sender).replace("%PLAYER%", args[0]).replace("%BALANCE%", Main.getEconomy().getBalance(Bukkit.getOfflinePlayer(uuid)) + ""));
        }
        return false;
    }

}
