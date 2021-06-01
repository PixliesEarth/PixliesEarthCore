package eu.pixliesearth.core.custom.commands.subcommands.vendors;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import org.bukkit.command.CommandSender;

public class SetBalanceCommand extends CustomSubCommand {

    @Override
    public String getCommandName() {
        return "setbalance";
    }

    @Override
    public String getCommandUsage() {
        return "Set vendor balance";
    }

    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        instance.getVendor().setBalance(Double.parseDouble(parameters[0]));
        commandSender.sendMessage("Balance set.");
        return false;
    }

    @Override
    public ITabable[] getParams() {
        return new ITabable[] {new CustomCommand.TabableStrings("BALANCE")};
    }

}
