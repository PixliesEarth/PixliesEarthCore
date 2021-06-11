package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AfkCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "afk";
    }

    @Override
    public String getCommandDescription() {
        return "Set yourself AFK";
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] args, boolean ranByPlayer) {
        if (!ranByPlayer) {
            Lang.ONLY_PLAYERS_EXEC.send(sender);
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("earth.command.afk")) {
            Lang.NO_PERMISSIONS.send(sender);
            return false;
        }
        player.setAfk(true);
        return true;
    }

}
