package eu.pixliesearth.core.custom.interfaces;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface ITabable {
	
	public List<String> getTabable(CommandSender commandSender, String[] params);
	
	public String getTabableName();
	
}
