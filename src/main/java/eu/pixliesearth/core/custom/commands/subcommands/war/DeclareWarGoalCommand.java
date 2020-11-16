package eu.pixliesearth.core.custom.commands.subcommands.war;

import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.WarCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import org.bukkit.command.CommandSender;

public class DeclareWarGoalCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "declare";
    }

    @Override
    public String getCommandUsage() {
        return "Declare a war.";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new WarCommand.TabableNation()};
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {

        return true;
    }

}
