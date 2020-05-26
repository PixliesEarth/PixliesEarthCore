package eu.pixliesearth.core.modules.economy;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.interfaces.Module;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomySystem implements Module, CommandExecutor {

    private final Economy economy = Main.getEconomy();

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
        EconomyResponse r;
        switch (args[0].toLowerCase()) {
            case "balance":
            case "bal":
                if (args.length == 1) {
                    if (sender instanceof Player) {
                        sendBalance((Player) sender);
                        return false;
                    } else {
                        sender.sendMessage("§aECONOMY §8| §7Usage: §b/economy balance <player>");
                        return false;
                    }
                }
                break;

            case "pay":
                Player player = (Player) sender;
                if (Bukkit.getPlayerUniqueId(args[1]) == null) {
                    sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
                    return false;
                }
                if (Bukkit.getPlayerUniqueId(args[1]) == player.getUniqueId()) {
                    player.sendMessage(Lang.PAID_HIMSELF.get(sender));
                    return false;
                }
                if (Double.parseDouble(args[2]) < config.getDouble("modules.economy.min-amount")) {
                    player.sendMessage(Lang.PAY_AMT_BELOW_MIN.get(sender).replace("%AMOUNT%", args[2]));
                    return false;
                }
                if (!(Main.getInstance().getProfile(player.getUniqueId()).getBalance() - Double.parseDouble(args[2]) >= 0.0)) {
                    sender.sendMessage(Lang.NOT_ENOUGH_MONEY.get(sender));
                    return false;
                }
                if (args[2].startsWith("-")) {
                    player.sendMessage(Lang.UNALLOWED_CHARS_IN_ARGS.get(sender));
                    return false;
                }
                r = economy.withdrawPlayer(player, Double.parseDouble(args[2]));
                if (!r.transactionSuccess()) {
                    player.sendMessage(Lang.UNEXPECTED_ECO_ERROR.get(sender));
                } else {
                    player.sendMessage(Lang.PAID_PLAYER_MONEY.get(sender).replace("%AMOUNT%", args[2]).replace("%TARGET%", args[1]));
                    OfflinePlayer target = Bukkit.getOfflinePlayer(Bukkit.getPlayerUniqueId(args[1]));
                    EconomyResponse r2 = economy.depositPlayer(target, Double.parseDouble(args[2]));
                    if (target.getPlayer() != null) {
                        target.getPlayer().sendMessage(Lang.RECEIVED_MONEY_FROM_PLAYER.get(target.getPlayer()).replace("%AMOUNT%", args[2]).replace("%TARGET%", player.getName()));
                    }
                }
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
                Profile profile = instance.getProfile(Bukkit.getPlayerUniqueId(args[1]));
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
                OfflinePlayer target = Bukkit.getOfflinePlayer(Bukkit.getPlayerUniqueId(args[1]));
                if (!(Main.getInstance().getProfile(target.getUniqueId()).getBalance() - Double.parseDouble(args[2]) >= 0.0)) {
                    sender.sendMessage(Lang.PLAYER_DOESNT_HAVE_ENOUGH_MONEY.get(sender));
                    return false;
                }
                r = economy.withdrawPlayer(target, Double.parseDouble(args[2]));
                if (!r.transactionSuccess()) {
                    sender.sendMessage(Lang.UNEXPECTED_ECO_ERROR.get(sender));
                } else {
                    sender.sendMessage(Lang.TOOK_MONEY_FROM_PLAYER.get(sender).replace("%AMOUNT%", args[2]).replace("%PLAYER%", target.getName()));
                }
                break;
        }
        return false;
    }

    private void sendBalance(Player player) {
        player.sendMessage(Lang.BALANCE_YOU.get(player).replace("%BALANCE%", Main.getEconomy().getBalance(player) + ""));
    }

}
