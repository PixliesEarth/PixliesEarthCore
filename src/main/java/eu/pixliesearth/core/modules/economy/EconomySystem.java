package eu.pixliesearth.core.modules.economy;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.interfaces.Module;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
        boolean transaction;
        OfflinePlayer target;
        Profile profile;
        switch (args[0].toLowerCase()) {
            case "balance":
            case "bal":
                if (args.length == 1) {
                    if (sender instanceof Player) {
                        sendBalance((Player) sender);
                        return false;
                    } else {
                        sender.sendMessage("§aECONOMY §8| §7Usage: §b/balance check <player>");
                        return false;
                    }
                }
                break;
            case "pay":
                Player player = (Player) sender;
                if (args[2].startsWith("-") || args[2].startsWith("+")) {
                    player.sendMessage(Lang.UNALLOWED_CHARS_IN_ARGS.get(sender));
                    return false;
                }
                if (Bukkit.getPlayerUniqueId(args[1]) == null) {
                    sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                    return false;
                }
                if (Bukkit.getPlayerUniqueId(args[1]) == player.getUniqueId()) {
                    player.sendMessage(Lang.PAID_HIMSELF.get(sender));
                    return false;
                }
                if (Double.parseDouble(args[2]) < config.getDouble("modules.economy.min-amount")) {
                    player.sendMessage(Lang.PAY_AMT_BELOW_MIN.get(sender).replace("%AMOUNT%", config.getDouble("modules.economy.min-amount")+""));
                    return false;
                }
                profile = instance.getProfile(player.getUniqueId());
                transaction = profile.withdrawMoney(Double.parseDouble(args[2]), "Payment to " + args[1]);
                if (!transaction) {
                    sender.sendMessage(Lang.NOT_ENOUGH_MONEY.get(sender));
                    return false;
                }

                player.sendMessage(Lang.PAID_PLAYER_MONEY.get(sender).replace("%AMOUNT%", args[2]).replace("%TARGET%", args[1]));
                target = Bukkit.getOfflinePlayer(Bukkit.getPlayerUniqueId(args[1]));
                Profile tProfile = instance.getProfile(target.getUniqueId());
                tProfile.depositMoney(Double.parseDouble(args[2]), "Payment from " + player.getName());
                if (target.getPlayer() != null)
                    target.getPlayer().sendMessage(Lang.RECEIVED_MONEY_FROM_PLAYER.get(target.getPlayer()).replace("%AMOUNT%", args[2]).replace("%TARGET%", player.getName()));
                break;

            case "set":
                if (!sender.hasPermission("earth.economy.admin")) {
                    sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                    return false;
                }
                if (Bukkit.getPlayerUniqueId(args[1]) == null) {
                    sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                    return false;
                }
                profile = instance.getProfile(Bukkit.getPlayerUniqueId(args[1]));
                profile.setBalance(Double.parseDouble(args[2]));
                profile.backup();
                sender.sendMessage(Lang.SET_PLAYER_BALANCE.get(sender));
                break;

            case "take":
                if (!sender.hasPermission("earth.economy.admin")) {
                    sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                    return false;
                }
                if (Bukkit.getPlayerUniqueId(args[1]) == null) {
                    sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                    return false;
                }
                target = Bukkit.getOfflinePlayer(Bukkit.getPlayerUniqueId(args[1]));
                Profile tProf = instance.getProfile(target.getUniqueId());
                transaction = tProf.withdrawMoney(Double.parseDouble(args[2]), "Balance withdrawal from " + sender.getName());
                if (!transaction) {
                    sender.sendMessage(Lang.PLAYER_DOESNT_HAVE_ENOUGH_MONEY.get(sender));
                    return false;
                }
                sender.sendMessage(Lang.TOOK_MONEY_FROM_PLAYER.get(sender).replace("%AMOUNT%", args[2]).replace("%PLAYER%", target.getName()));
                break;

            case "give":
                if (!sender.hasPermission("earth.economy.admin")) {
                    sender.sendMessage(Lang.NO_PERMISSIONS.get(sender));
                    return false;
                }
                if (Bukkit.getPlayerUniqueId(args[1]) == null) {
                    sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                    return false;
                }
                target = Bukkit.getOfflinePlayer(Bukkit.getPlayerUniqueId(args[1]));
                Profile tProf2 = instance.getProfile(target.getUniqueId());
                tProf2.depositMoney(Double.parseDouble(args[2]), "Balance deposit from " + sender.getName());
                sender.sendMessage(Lang.GAVE_MONEY_TO_PLAYER.get(sender).replace("%AMOUNT%", args[2]).replace("%PLAYER%", target.getName()));
                break;
        }
        return false;
    }

    private void sendBalance(Player player) {
        player.sendMessage(Lang.BALANCE_YOU.get(player).replace("%BALANCE%", Main.getEconomy().getBalance(player) + ""));
    }

}
