package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.commands.subcommands.economy.EconomyTopCommand;
import org.bukkit.command.CommandSender;

public class BalTopCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "baltop";
    }

    @Override
    public String getCommandDescription() {
        return "baltop";
    }

    @Override
    public boolean onExecuted(CommandSender sender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        new EconomyTopCommand().onExecuted(sender, aliasUsed, parameters, ranByPlayer);
        return false;
    }

}
