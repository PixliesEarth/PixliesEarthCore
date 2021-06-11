package eu.pixliesearth.core.custom.commands.subcommands.test;

import eu.pixliesearth.core.custom.CustomCommand.TabableString;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.utils.ExplosionCalculator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Deprecated
public class TestExplosionsSubCommand2 extends CustomSubCommand {

	@Override
	public String getCommandName() {
		return "explode2";
	}

	@Override
	public String getCommandUsage() {
		return "";
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new TabableString("Radius"), new TabableString("Explosion Value")};
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		ExplosionCalculator calc = new ExplosionCalculator(((Player) commandSender).getLocation(), Integer.parseUnsignedInt(parameters[1]), false, Integer.parseUnsignedInt(parameters[2]));
		calc.explode(false);
		return true;
	}

}
