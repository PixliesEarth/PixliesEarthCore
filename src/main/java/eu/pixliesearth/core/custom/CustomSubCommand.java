package eu.pixliesearth.core.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import eu.pixliesearth.Main;
import org.bukkit.command.CommandSender;

import eu.pixliesearth.core.custom.interfaces.ITabable;

/**
 * 
 * @author BradBot_1
 * 
 */
public abstract class CustomSubCommand {
	/**
	 * Initialises the class
	 */
	public CustomSubCommand() {
		
	}

	protected static final Main instance = Main.getInstance();
	
	public abstract String getCommandName();
	
	public abstract String getCommandUsage();
	
	public Set<String> getCommandAliases() { return null; }
	
	public String getPermission() { return null; }
	
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		commandSender.sendMessage("You have performed the command "+aliasUsed);
		return true;
	}
	
	public ITabable[] getParams() { return null; }
	
	public static class TabableSubCommand implements ITabable {
		
		CustomSubCommand[] subCommands;
		
		public TabableSubCommand(CustomSubCommand... subCommands) {
			this.subCommands = subCommands;
		}
		
		public CustomSubCommand getCustomSubCommandFromName(String name) {
			for (CustomSubCommand subCommand : subCommands) {
				if (subCommand.getCommandName().equalsIgnoreCase(name)) {
					return subCommand;
				} else {
					if (subCommand.getCommandAliases()==null || subCommand.getCommandAliases().isEmpty()) continue;
					for (String s : subCommand.getCommandAliases()) {
						if (s.equalsIgnoreCase(name)) {
							return subCommand;
						}
					}
				}
			}
			return null;
		}
		
		@Override
		public List<String> getTabable(CommandSender commandSender, String[] params) {
			List<String> list = new ArrayList<>();
			for (CustomSubCommand subCommand : subCommands) {
				list.add(subCommand.getCommandName());
				if (subCommand.getCommandAliases()!=null) {
					list.addAll(subCommand.getCommandAliases());
				}
			}
			return list;
		}

		@Override
		public String getTabableName() {
			return "subcommand";
		}

	}
}
