package eu.pixliesearth.core.custom.interfaces;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface ITabable {
	
	public List<String> getTabable(CommandSender commandSender, String[] params);
	
	public String getTabableName();
	
}
