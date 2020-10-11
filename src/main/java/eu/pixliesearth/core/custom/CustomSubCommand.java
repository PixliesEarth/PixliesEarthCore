package eu.pixliesearth.core.custom;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>This class is not done yet!</h3>
 *
 * @deprecated Not completed
 * 
 * TODO: notes
 */
@Deprecated
public class CustomSubCommand {
	
	public CustomSubCommand() {
		
	}
	
	public String getName() {
		return null;
	}
	
	public String getDescription() {
		return null;
	}
	
	public String getUsage() {
		return null;
	}
	
	public ArrayList<String> getAliases() {
		return new ArrayList<String>();
	}
	
	public CustomCommand getParentCommand() {
		return null;
	}
	
	public boolean execute(CommandSender sender, String alias, String[] args) {return false;}
}
