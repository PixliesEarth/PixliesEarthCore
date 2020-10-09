package eu.pixliesearth.core.custom.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;

public class GiveCustomItems extends CustomCommand implements CommandExecutor, TabExecutor {
	
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
		ArrayList<String> array = new ArrayList<String>();
		if (args.length<2) {
			for (Player p : Bukkit.getOnlinePlayers()) 
				array.add(p.getDisplayName());
		} else if (args.length<3) {
			for (CustomItem c : CustomFeatureLoader.getLoader().getHandler().getCustomItems()) 
				array.add(c.getUUID());
		}
		return array;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		return execute(commandSender, s, strings);
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
		return tabComplete(commandSender, s, strings);
	}
}
