package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import org.bukkit.command.CommandSender;

public class PunishCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "punish";
    }

    @Override
    public String getCommandDescription() {
        return "Punish people";
    }

    @Override
    public String getPermission() {
        return "net.pixlies.punish";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        //TODO
        return true;
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[] {new CustomSubCommand.TabableSubCommand()};
    }

}
