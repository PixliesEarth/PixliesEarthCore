package eu.pixliesearth.core.commands.economy;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.managers.CoinManager;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CoinsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        boolean allowed;
        switch (args.length) {
            case 0:
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§aEARTH §8| §7Only executable by a player.");
                    return false;
                }
                Player player = (Player) sender;
                Profile user = Main.getInstance().getProfile(player.getUniqueId());
                sender.sendMessage(Lang.PC_BALANCE.get(sender).replace("%AMOUNT%", user.getPixliecoins()+""));
                break;
            case 2:
                allowed = false;
                if (!(sender instanceof Player)) allowed = true;
                if (sender.hasPermission("earth.coins.others")) allowed = true;
                if (!allowed) {
                    sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                    return false;
                }
                if (args[0].equalsIgnoreCase("check")) {
                    if (Bukkit.getPlayerUniqueId(args[1]) == null) {
                        sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                        return false;
                    }
                    Profile targetProf = Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(args[1]));
                    sender.sendMessage(Lang.PC_BALANCE_OTHERS.get(sender).replace("%PLAYERT%", Bukkit.getOfflinePlayer(UUID.fromString(targetProf.getUniqueId())).getName()).replace("%AMOUNT%", targetProf.getPixliecoins()+""));
                }
                break;
            case 3:
                allowed = false;
                if (!(sender instanceof Player)) allowed = true;
                if (sender.hasPermission("earth.coins.admin")) allowed = true;
                if (!allowed) {
                    sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                    return false;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    if (Bukkit.getPlayerUniqueId(args[1]) == null) {
                        sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                        return false;
                    }
                    Profile targetProf = Main.getInstance().getProfile(Bukkit.getPlayerUniqueId(args[1]));
                    if (!StringUtils.isNumeric(args[2])) {
                        sender.sendMessage(Lang.UNALLOWED_CHARS_IN_ARGS.get(sender));
                        return false;
                    }
                    CoinManager.add(targetProf, Double.parseDouble(args[2]));
                    sender.sendMessage(Lang.PC_ADDED_BALANCE.get(sender).replace("%PLAYER%", Bukkit.getOfflinePlayer(UUID.fromString(targetProf.getUniqueId())).getName()).replace("%AMOUNT%", args[2]));
                }
                break;

        }
        return false;
    }

}
