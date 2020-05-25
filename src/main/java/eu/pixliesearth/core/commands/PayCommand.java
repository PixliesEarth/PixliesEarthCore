package eu.pixliesearth.core.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.localization.Lang;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private static final Economy economy = Main.getEconomy();
    private FileConfiguration config = Main.getInstance().getConfig();
    private static Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ONLY_PLAYERS_EXEC.get(sender));
            return false;
        }
        Player player = (Player) sender;
        if (Bukkit.getPlayerUniqueId(args[0]) == null) {
            sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
            return false;
        }
        if (Bukkit.getPlayerUniqueId(args[0]) == player.getUniqueId()) {
            player.sendMessage(Lang.PAID_HIMSELF.get(sender));
            return false;
        }
        if (Double.parseDouble(args[1]) < config.getDouble("modules.economy.min-amount")) {
            player.sendMessage(Lang.PAY_AMT_BELOW_MIN.get(sender).replace("%AMOUNT%", args[2]));
            return false;
        }
        if (!economy.has(player, Double.parseDouble(args[1]))) {
            player.sendMessage(Lang.NOT_ENOUGH_MONEY.get(sender));
            return false;
        }
        if (args[1].startsWith("-")) {
            player.sendMessage(Lang.UNALLOWED_CHARS_IN_ARGS.get(sender));
            return false;
        }
        EconomyResponse r = economy.withdrawPlayer(player, Double.parseDouble(args[1]));
        if (!r.transactionSuccess()) {
            player.sendMessage(Lang.UNEXPECTED_ECO_ERROR.get(sender));
        } else {
            player.sendMessage(Lang.PAID_PLAYER_MONEY.get(sender).replace("%AMOUNT%", args[1]).replace("%TARGET%", args[0]));
            OfflinePlayer target = Bukkit.getOfflinePlayer(Bukkit.getPlayerUniqueId(args[0]));
            EconomyResponse r2 = economy.depositPlayer(target, Double.parseDouble(args[1]));
            if (target.getPlayer() != null) {
                target.getPlayer().sendMessage(Lang.RECEIVED_MONEY_FROM_PLAYER.get(target.getPlayer()).replace("%AMOUNT%", args[1]).replace("%TARGET%", player.getName()));
            }
        }
        return false;
    }

}
