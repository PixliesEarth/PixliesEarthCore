package eu.pixliesearth.core.custom.commands.subcommands.CIControl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.CIControl;
import eu.pixliesearth.core.custom.interfaces.ITabable;

public class Disable extends CustomSubCommand {

	@Override
	public String getCommandName() {
		return "disable";
	}

	@Override
	public String getCommandUsage() {
		return "Disables a given item";
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new TabableCustomItems()};
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		if (!commandSender.isOp()) {
			commandSender.sendMessage("§c[§r❌§c] §rThis is an operator command only.");
		}
		if (parameters.length<1) {
			commandSender.sendMessage("§c[§r❌§c] §rIncorrect usage! Follow the usage /cicontrol disable <itemUUID>");
			return false;
		}
		String id = parameters[0];
		if (CIControl.DISABLED_ITEMS.contains(id)) {
			commandSender.sendMessage("§c[§r❌§c] §rThis item is already disabled!");
			return false;
		} else {
			CIControl.DISABLED_ITEMS.remove(id);
			commandSender.sendMessage("§a[§r✔️§a] §rDisabled the custom item "+id);
			return true;
		}
	}
	
	private static class TabableCustomItems implements ITabable {
		
		public static List<String> getCustomItemsAsStringList() {
			ArrayList<String> array = new ArrayList<String>();
			for (CustomItem c : CustomFeatureLoader.getLoader().getHandler().getCustomItems()) 
				array.add(c.getUUID());
			return array;
		}
		
		@Override
		public List<String> getTabable(CommandSender commandSender, String[] params) {
			List<String> list = new ArrayList<>();
			StringUtil.copyPartialMatches(params[params.length-1], getCustomItemsAsStringList(), list);
			list.removeAll(CIControl.DISABLED_ITEMS);
			Collections.sort(list);
			return list;
		}

		@Override
		public String getTabableName() {
			return "UUID";
		}
		
	}
	
}
