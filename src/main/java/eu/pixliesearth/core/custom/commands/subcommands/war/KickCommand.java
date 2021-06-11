package eu.pixliesearth.core.custom.commands.subcommands.war;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.localization.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "kick";
    }

    @Override
    public String getCommandUsage() {
        return "kick from wars.";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomCommand.TabablePlayer()};
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        Player player = Bukkit.getPlayer(parameters[0]);
        if (player == null) {
            Lang.PLAYER_DOES_NOT_EXIST.send(commandSender);
            return false;
        }
        if (!instance.getUtilLists().playersInWar.containsKey(player.getUniqueId())) {
            commandSender.sendMessage(Lang.EARTH + "§7Player is not in war.");
            return false;
        }
        instance.getUtilLists().playersInWar.get(player.getUniqueId()).handleLeave(instance.getProfile(player.getUniqueId()));
        commandSender.sendMessage(Lang.WAR + "§7Player removed.");
        return true;
    }

}
