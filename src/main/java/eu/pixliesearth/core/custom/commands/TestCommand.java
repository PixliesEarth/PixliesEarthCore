package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand.TabableSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.test.TestExplosionsSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.test.TestWolf;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import org.bukkit.command.CommandSender;

public class TestCommand extends CustomCommand {

	@Override
	public String getCommandName() {
		return "test";
	}

	@Override
	public String getCommandDescription() {
		return "test custom features";
	}
	
	@Override
	public String getPermission() {
		return "eu.pixlies.test";
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new TabableSubCommand(new TestExplosionsSubCommand(), new TestWolf())};
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		commandSender.sendMessage("Please enter valid arguments!");
		return true;
	}
	
}
