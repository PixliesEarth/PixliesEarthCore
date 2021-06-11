package eu.pixliesearth.core.custom.commands.subcommands.economy;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class EconomyBalanceCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "balance";
    }

    @Override
    public String getCommandUsage() {
        return "balance.";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomCommand.TabablePlayer()};
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (!instance.isStaff(sender)) {
            Lang.NO_PERMISSIONS.send(sender);
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(parameters[0]);
        if (target == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(sender);
            return false;
        }
        Profile profile = instance.getProfile(target.getUniqueId());
        sender.sendMessage(Lang.BALANCE_OTHERS.get(sender).replace("%PLAYER%", parameters[0]).replace("%BALANCE%", profile.getBalanceFormatted()));
        return false;
    }

}
