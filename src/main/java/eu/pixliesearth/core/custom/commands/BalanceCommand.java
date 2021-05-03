package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class BalanceCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "balance";
    }

    @Override
    public Set<String> getCommandAliases() {
        return new HashSet<>(){{
            add("checkmoney");
            add("purse");
            add("bal");
        }};
    }

    @Override
    public String getCommandDescription() {
        return "Balance command";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new TabablePlayer()};
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (parameters.length == 0) {
            if (!ranByPlayer) {
                Lang.ONLY_PLAYERS_EXEC.send(sender);
                return false;
            }
            Player player = (Player) sender;
            Profile profile = instance.getProfile(player.getUniqueId());
            sender.sendMessage(Lang.BALANCE_YOU.get(player).replace("%BALANCE%", profile.getBalanceFormatted()));
        } else {
            if (!instance.isStaff(sender)) {
                Lang.NO_PERMISSIONS.send(sender);
                return false;
            }
            OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(parameters[0]);
            if (player == null) {
                Lang.PLAYER_DOES_NOT_EXIST.send(sender);
                return false;
            }
            Profile profile = instance.getProfile(player.getUniqueId());
            sender.sendMessage(Lang.BALANCE_OTHERS.get(sender).replace("%PLAYER%", parameters[0]).replace("%BALANCE%", profile.getBalanceFormatted()));
        }
        return true;
    }

}
