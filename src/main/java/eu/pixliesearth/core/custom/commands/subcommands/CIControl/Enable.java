package eu.pixliesearth.core.custom.commands.subcommands.CIControl;

import org.bukkit.command.CommandSender;

import eu.pixliesearth.core.custom.CustomCommand.TabableStrings;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.CIControl;
import eu.pixliesearth.core.custom.interfaces.ITabable;

public class Enable extends CustomSubCommand {

	@Override
	public String getCommandName() {
		return "enable";
	}

	@Override
	public String getCommandUsage() {
		return "Enables a given item";
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new TabableStrings((String[]) CIControl.DISABLED_ITEMS.toArray(new String[CIControl.DISABLED_ITEMS.size()]))};
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		if (!commandSender.isOp()) {
			commandSender.sendMessage("§c[§r❌§c] §rThis is an operator command only.");
		}
		if (parameters.length<1) {
			commandSender.sendMessage("§c[§r❌§c] §rIncorrect usage! Follow the usage /cicontrol enable <itemUUID>");
			return false;
		}
		String id = parameters[0];
		if (!CIControl.DISABLED_ITEMS.contains(id)) {
			commandSender.sendMessage("§c[§r❌§c] §rNo item with the UUID "+id+" is disabled!");
			return false;
		} else {
			CIControl.DISABLED_ITEMS.remove(id);
			commandSender.sendMessage("§a[§r✔§a] §rEnabled the custom item "+id);
			return true;
		}
	}

}
