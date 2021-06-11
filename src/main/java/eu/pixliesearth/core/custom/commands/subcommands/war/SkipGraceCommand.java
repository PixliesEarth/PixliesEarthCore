package eu.pixliesearth.core.custom.commands.subcommands.war;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.warsystem.War;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkipGraceCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "skipgrace";
    }

    @Override
    public String getCommandUsage() {
        return "Skip grace period";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (commandSender instanceof Player && !instance.getProfile(((Player)commandSender).getUniqueId()).isStaff()) {
            Lang.NO_PERMISSIONS.send(commandSender);
            return false;
        }
        War war = instance.getCurrentWar();
        if (war == null) {
            commandSender.sendMessage(Lang.WAR + "§7There is no active war at the moment.");
            return false;
        }
        boolean skip = war.skipGrace();
        if (skip) {
            commandSender.sendMessage(Lang.WAR + "§7Skipped grace period.");
        } else {
            commandSender.sendMessage(Lang.WAR + "§7Grace period has already ended.");
        }
        return true;
    }

}
