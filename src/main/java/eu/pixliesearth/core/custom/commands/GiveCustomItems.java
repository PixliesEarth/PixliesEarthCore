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

public class GiveCustomItems extends CustomCommand {
	
	public GiveCustomItems() {
		
	}
	
	@Override
	public String getName() {
		return "cigive";
	}
	
	@Override
	public String getDescription() {
		return "Gives a custom item based on the input";
	}
	
	@Override
	public String getPermission() {
		return "eu.pixlies.customitems.give";
	}
	
	@Override
	public boolean execute(CommandSender commandsender, String alias, String[] args) {
		if (args.length<2) {
			commandsender.sendMessage("Please enter a valid amount of arguments");
			return false;
		}
		Player p = Bukkit.getPlayer(args[0]);
		CustomItem c = CustomFeatureLoader.getLoader().getHandler().getCustomItemFromUUID(args[1]);
		if (p==null) {
			commandsender.sendMessage("Please enter a valid player");
			return false;
		}
		if (c==null) {
			commandsender.sendMessage("Please enter a valid customitem UUID");
			return false;
		}
		CustomFeatureLoader.getLoader().getHandler().giveCustomItem(p, c.getClass());
		commandsender.sendMessage("§rGave the player §a"+p.getDisplayName()+"§r the custom item §a"+c.getDefaultDisplayName()+"§r(§a"+c.getUUID()+"§r)");
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender commandsender, String alias, String[] args) {
		List<String> array = new ArrayList<String>();
		
		if (args.length<2) {
			StringUtil.copyPartialMatches(args[0], getOnlinePlayersAsStringList(), array);
		} else if (args.length<3) {
			StringUtil.copyPartialMatches(args[1], getCustomItemsAsStringList(), array);
		}
		Collections.sort(array);
		return array;
	}
	
	private static List<String> getOnlinePlayersAsStringList() {
		ArrayList<String> array = new ArrayList<String>();
		for (Player p : Bukkit.getOnlinePlayers()) 
			array.add(p.getDisplayName());
		return array;
	}
	
	private static List<String> getCustomItemsAsStringList() {
		ArrayList<String> array = new ArrayList<String>();
		for (CustomItem c : CustomFeatureLoader.getLoader().getHandler().getCustomItems()) 
			array.add(c.getUUID());
		return array;
	}
}
