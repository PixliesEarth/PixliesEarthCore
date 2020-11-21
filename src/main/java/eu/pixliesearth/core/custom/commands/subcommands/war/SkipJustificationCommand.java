package eu.pixliesearth.core.custom.commands.subcommands.war;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.warsystem.War;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class SkipJustificationCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "skipjustification";
    }

    @Override
    public Set<String> getCommandAliases() {
        Set<String> returner = new HashSet<>();
        returner.add("instantwar");
        returner.add("skipjust");
        return returner;
    }

    @Override
    public String getCommandUsage() {
        return "Skip a war-goal justification.";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new DeclareWarGoalCommand.TabableWars()};
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        if (ranByPlayer && !instance.getProfile(((Player)commandSender).getUniqueId()).isStaff()) {
            Lang.NO_PERMISSIONS.send(commandSender);
            return false;
        }
        War war = War.getById(parameters[0]);
        if (war == null) {
            commandSender.sendMessage("§7This war does not exist.");
            return false;
        }
        boolean d = war.makeDeclarable();
        if (d) {
            commandSender.sendMessage(Lang.WAR + "§aWar-justification time skipped.");
        } else {
            commandSender.sendMessage(Lang.WAR + "§7This war is already declarable.");
        }
        return true;
    }

}
