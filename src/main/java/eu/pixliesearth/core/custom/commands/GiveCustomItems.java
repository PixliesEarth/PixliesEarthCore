package eu.pixliesearth.core.custom.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.interfaces.ITabable;

public class GiveCustomItems extends CustomCommand {
	
	public GiveCustomItems() {
		
	}
	
	@Override
	public String getCommandName() {
		return "cigive";
	}
	
	@Override
	public String getCommandDescription() {
		return "Gives a custom item based on the input";
	}
	
	@Override
	public String getPermission() {
		return "eu.pixlies.customitems.give";
	}
	
	@Override
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		if (parameters.length<2) {
			commandSender.sendMessage("Please enter a valid amount of arguments");
			return false;
		}
		Player p = Bukkit.getPlayer(parameters[0]);
		CustomItem c = CustomFeatureLoader.getLoader().getHandler().getCustomItemFromUUID(parameters[1]);
		if (p==null) {
			commandSender.sendMessage("Please enter a valid player");
			return false;
		}
		if (c==null) {
			commandSender.sendMessage("Please enter a valid customitem UUID");
			return false;
		}
		CustomFeatureLoader.getLoader().getHandler().giveCustomItem(p, c.getClass());
		commandSender.sendMessage("§rGave the player §a"+p.getDisplayName()+"§r the custom item §a"+c.getDefaultDisplayName()+"§r(§a"+c.getUUID()+"§r)");
		return true;
	}
	
	@Override
	public ITabable[] getParams() {
		return new ITabable[] {new TabablePlayer(), new TabableCustomItems()};
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
			Collections.sort(list);
			return list;
		}

		@Override
		public String getTabableName() {
			return "UUID";
		}
		
	}
}
