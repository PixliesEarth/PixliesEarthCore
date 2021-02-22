package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.cmobs.SpawnSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import org.bukkit.command.CommandSender;

public class CustomMobsCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "cmobs";
    }

    @Override
    public String getCommandDescription() {
        return "c-mobs command";
    }

    @Override
    public String getPermission() {
        return "earth.admin";
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[]{new CustomSubCommand.TabableSubCommand(new SpawnSubCommand())};
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        return true;
    }

}
