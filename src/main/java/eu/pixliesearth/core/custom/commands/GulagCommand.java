package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.gulag.SetLocationOneCommand;
import eu.pixliesearth.core.custom.commands.subcommands.gulag.SetLocationTwoCommand;
import eu.pixliesearth.core.custom.commands.subcommands.gulag.SetSpectatorSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import org.bukkit.command.CommandSender;

public class GulagCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "gulag";
    }

    @Override
    public String getCommandDescription() {
        return "Gulag command";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomSubCommand.TabableSubCommand(new SetSpectatorSubCommand(), new SetLocationOneCommand(), new SetLocationTwoCommand())};
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {

        return true;
    }

}
