package eu.pixliesearth.core.custom.commands.subcommands.war;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.localization.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "declare";
    }

    @Override
    public String getCommandUsage() {
        return "Declare a war.";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (commandSender instanceof Player && !instance.getProfile(((Player)commandSender).getUniqueId()).isStaff()) {
            Lang.NO_PERMISSIONS.send(commandSender);
            return false;
        }
        if (instance.getCurrentWar() == null) {
            commandSender.sendMessage(Lang.WAR + "§7There is no active war at the moment.");
            return false;
        }
        instance.getCurrentWar().stop(instance.getCurrentWar().getCurrentWinner());
        commandSender.sendMessage(Lang.WAR + "§7War stopped.");
        return true;
    }

}
