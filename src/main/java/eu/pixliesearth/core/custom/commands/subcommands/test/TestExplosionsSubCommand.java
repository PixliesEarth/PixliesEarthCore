package eu.pixliesearth.core.custom.commands.subcommands.test;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.pixliesearth.core.custom.CustomCommand.TabableString;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.utils.ExplosionCalculator;

public class TestExplosionsSubCommand extends CustomSubCommand {

	@Override
	public String getCommandName() {
		return "explode";
	}

	@Override
	public String getCommandUsage() {
		return "";
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new TabableString("Radius")};
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		ExplosionCalculator calc = new ExplosionCalculator(((Player) commandSender).getLocation(), Integer.parseUnsignedInt(parameters[0]), false);
		calc.explode(true);
		return true;
	}

}
