package eu.pixliesearth.core.custom.commands;

import java.util.HashSet;
import java.util.Set;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand.TabableSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.CIControl.Disable;
import eu.pixliesearth.core.custom.commands.subcommands.CIControl.Enable;
import eu.pixliesearth.core.custom.interfaces.ITabable;

public class CIControl extends CustomCommand {
	
	public static final Set<String> DISABLED_ITEMS = new HashSet<>();
	
	@Override
	public String getCommandName() {
		return "cicontrol";
	}

	@Override
	public String getCommandDescription() {
		return "Allows for custom items to be disabled";
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new TabableSubCommand(new Disable(), new Enable())};
	}

}
