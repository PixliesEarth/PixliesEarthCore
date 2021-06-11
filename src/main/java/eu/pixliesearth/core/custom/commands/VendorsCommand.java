package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.vendors.AddItemCommand;
import eu.pixliesearth.core.custom.commands.subcommands.vendors.SetBalanceCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;

public class VendorsCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "vendors";
    }
    
    @Override
    public String getCommandDescription() {
    	return "idk what to put here";
    }

    @Override
    public String getPermission() {
        return "net.pixlies.vendor";
    }
    
    @Override
    public boolean isPlayerOnlyCommand() {
    	return true;
    }
    
    @Override
    public ITabable[] getParams() {
    	return new ITabable[] {new CustomSubCommand.TabableSubCommand(new AddItemCommand(), new SetBalanceCommand())};
    }

}
