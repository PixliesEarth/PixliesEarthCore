package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PayCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "pay";
    }

    @Override
    public String getCommandDescription() {
        return "Pay players";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new TabablePlayer(), new TabableString("Amount")};
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] args, boolean ranByPlayer) {
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
        FileConfiguration config = instance.getConfig();
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

        double amount = Methods.round(Double.parseDouble(args[1]), 2);

        player.sendMessage(Lang.PAID_PLAYER_MONEY.get(sender).replace("%AMOUNT%", amount+"").replace("%TARGET%", args[0]));
        target = Bukkit.getOfflinePlayer(Bukkit.getPlayerUniqueId(args[0]));
        Profile tProfile = instance.getProfile(target.getUniqueId());
        tProfile.depositMoney(amount, "Payment from " + player.getName());
        if (target.getPlayer() != null)
            target.getPlayer().sendMessage(Lang.RECEIVED_MONEY_FROM_PLAYER.get(target.getPlayer()).replace("%AMOUNT%", args[1]).replace("%TARGET%", player.getName()));
        return false;
    }

}
