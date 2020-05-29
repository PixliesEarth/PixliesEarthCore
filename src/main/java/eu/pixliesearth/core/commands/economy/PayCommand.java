package eu.pixliesearth.core.commands.economy;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private FileConfiguration config = Main.getInstance().getConfig();
    private static Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        boolean transaction;
        OfflinePlayer target;
        Profile profile;
        if (args[1].startsWith("-") || args[1].startsWith("+")) {
            player.sendMessage(Lang.UNALLOWED_CHARS_IN_ARGS.get(sender));
            return false;
        }
        if (Bukkit.getPlayerUniqueId(args[0]) == null) {
            sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.get(sender));
            return false;
        }
        if (Bukkit.getPlayerUniqueId(args[0]) == player.getUniqueId()) {
            player.sendMessage(Lang.PAID_HIMSELF.get(sender));
            return false;
        }
        if (Double.parseDouble(args[1]) < config.getDouble("modules.economy.min-amount")) {
            player.sendMessage(Lang.PAY_AMT_BELOW_MIN.get(sender).replace("%AMOUNT%", config.getDouble("modules.economy.min-amount")+""));
            return false;
        }
        profile = instance.getProfile(player.getUniqueId());
        transaction = profile.withdrawMoney(Double.parseDouble(args[1]), "Payment to " + args[0]);
        if (!transaction) {
            sender.sendMessage(Lang.NOT_ENOUGH_MONEY.get(sender));
            return false;
        }

        player.sendMessage(Lang.PAID_PLAYER_MONEY.get(sender).replace("%AMOUNT%", args[1]).replace("%TARGET%", args[0]));
        target = Bukkit.getOfflinePlayer(Bukkit.getPlayerUniqueId(args[0]));
        Profile tProfile = instance.getProfile(target.getUniqueId());
        tProfile.depositMoney(Double.parseDouble(args[1]), "Payment from " + player.getName());
        if (target.getPlayer() != null)
            target.getPlayer().sendMessage(Lang.RECEIVED_MONEY_FROM_PLAYER.get(target.getPlayer()).replace("%AMOUNT%", args[1]).replace("%TARGET%", player.getName()));
        return false;
    }

}
