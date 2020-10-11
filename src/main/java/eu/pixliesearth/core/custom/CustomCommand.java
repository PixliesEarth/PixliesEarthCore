package eu.pixliesearth.core.custom;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to make creating commands easier</h3>
 * 
 */
public class CustomCommand {
	/**
	 * Initialises the class
	 */
	public CustomCommand() {
		
	}
	/**
	 * The commands name
	 * 
	 * @return The commands name
	 */
	public String getName() {
		return null;
	}
	/**
	 * The commands description
	 * 
	 * @returnThe commands description
	 */
	public String getDescription() {
		return null;
	}
	/**
	 * The commands usage
	 * 
	 * @return The commands usage
	 */
	public String getUsage() {
		return "/"+getName();
	}
	/**
	 * The commands aliases
	 * 
	 * @return The commands aliases
	 */
	public ArrayList<String> getAliases() {
		return new ArrayList<String>();
	}
	/**
	 * Gets and returns
	 * 
	 * @apiNote If it is null it does not require a permission
	 * 
	 * @return The class of the permission it requires
	 */
	public String getPermission() {
		return null;
	}
	/**
	 * Called when the command is ran
	 * 
	 * @param commandsender The command sender whom ran the command
	 * @param alias The command name ran
	 * @param args The commands arguments
	 * @return If the command was successful
	 */
	public boolean execute(CommandSender commandsender, String alias, String[] args) {return false;}
	/**
	 * Called when the tabComplete is tried
	 * 
	 * @param commandsender The command sender whom ran the command
	 * @param alias The command name ran
	 * @param args The commands arguments
	 * @return The tab complete to return
	 */
	public List<String> tabComplete(CommandSender commandsender, String alias, String[] args) {return new ArrayList<String>();}
	/**
	 * Returns a version of the command that can be registered with bukkit
	 * 
	 * @return The command in a {@link RegisterableCommand} form
	 */
	public RegisterableCommand getAsRegisterableCommand() {
		return new RegisterableCommand(this);
	}
	/**
	 * 
	 * @author BradBot_1
	 * 
	 * <h3>Used to create a command that can be registered into minecraft</h3>
	 *
	 */
	private static class RegisterableCommand extends BukkitCommand {
		protected CustomCommand cmd;
		
		protected RegisterableCommand(CustomCommand c) {
			super(c.getName(), c.getDescription(), c.getUsage(), c.getAliases());
			if (c.getPermission()!=null) 
				setPermission(c.getPermission());
			this.cmd = c;
		}
		@Override
		public boolean execute(CommandSender sender, String commandLabel, String[] args) {
			return this.cmd.execute(sender, commandLabel, args);
		}
		@Override
		public List<String> tabComplete(CommandSender commandsender, String alias, String[] args) {
			return this.cmd.tabComplete(commandsender, alias, args);
		}
	}
}
