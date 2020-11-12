package eu.pixliesearth.core.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import eu.pixliesearth.core.custom.CustomSubCommand.TabableSubCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.utils.Methods;
import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to make creating commands easier</h3>
 * <h5>Version 2</h5>
 * 
 */
public abstract class CustomCommand {
	/**
	 * Initialises the class
	 */
	public CustomCommand() {
		
	}
	
	public abstract String getCommandName();
	
	public abstract String getCommandDescription();
	
	public String getCommandUsageAutoGenerated() {
		String s = "/"+getCommandName();
		if (getParams()==null || getParams().length==0) {
			return s;
		}
		for (ITabable tab : getParams()) {
			s += " <"+tab.getTabableName()+">";
		}
		return s;
	}
	
	@Deprecated
	public String getCommandUsage(){ return null; };
	
	public Set<String> getCommandAliases() { return null; }
	
	public String getPermission() { return null; }
	
	public boolean isPlayerOnlyCommand() { return false; }
	
	public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
		commandSender.sendMessage("You have performed the command "+aliasUsed);
		return true;
	}
	
	public ITabable[] getParams() { return null; }
	
	// REGISTERING STUFF
	
	public static class RegisterableCommand extends BukkitCommand {
		
		CustomCommand c;
		
		protected RegisterableCommand(CustomCommand c) {
			super(c.getCommandName(), c.getCommandDescription(), (c.getCommandUsage()==null) ? c.getCommandUsageAutoGenerated() : c.getCommandUsage(), (c.getCommandAliases()==null) ? new ArrayList<String>() : Methods.convertSetIntoList(c.getCommandAliases()));
			if (c.getPermission()!=null) 
				setPermission(c.getPermission());
			this.c = c;
		}
		
		@Override
		public boolean execute(CommandSender sender, String commandLabel, String[] args) {
			for (int i = 0; i < args.length; i++) {
				try {
					ITabable t = c.getParams()[i];
					if (t instanceof TabableSubCommand) {
						TabableSubCommand ts = (TabableSubCommand) t;
						return ts.getCustomSubCommandFromName(args[i]).onExecuted(sender, commandLabel, args, (sender instanceof Player));
					} else {
						continue;
					}
				} catch (Exception ignore) { continue; /*goto the next loop*/}
			}
			if (!sender.hasPermission(c.getPermission())) {
				Lang.NO_PERMISSIONS.send(sender);
			}
			if (c.isPlayerOnlyCommand()) {
				if ((sender instanceof Player)) {
					return this.c.onExecuted(sender, commandLabel, args, (sender instanceof Player));
				} else {
					Lang.ONLY_PLAYERS_EXEC.send(sender);
					//sender.sendMessage("You need to be a player to perform this command.");
					return false;
				}
			} else {
				return this.c.onExecuted(sender, commandLabel, args, (sender instanceof Player));
			}
		}
		
		@Override
		public List<String> tabComplete(CommandSender commandsender, String alias, String[] args) {
			if (c.getParams()==null || c.getParams().length==0) {
				return new ArrayList<>();
			} else {
				for (int i = 0; i < args.length-1; i++) {
					try {
						ITabable t = c.getParams()[i];
						if (t instanceof TabableSubCommand) {
							try {
								TabableSubCommand t2 = (TabableSubCommand) t;
								CustomSubCommand s = t2.getCustomSubCommandFromName(args[i]);
								ITabable[] t3 = s.getParams();
								ITabable t4 = t3[args.length-i-2];
								return t4.getTabable(commandsender, args);
							} catch (Exception ignore) {return new ArrayList<String>() {private static final long serialVersionUID = -17124577512450984L;{
								add("?");
							}};}
						}
					} catch (Exception e) {
						continue;
					}
				}
				try {
					return c.getParams()[args.length-1].getTabable(commandsender, args);
				} catch (Exception ignore) { return new ArrayList<String>() {private static final long serialVersionUID = -17124577512450984L;{
					add("?");
				}};}
			}
		}
	}
	
	// TAB STUFF
	
	public static class TabablePlayer implements ITabable {
		
		public List<String> getPlayerNames() {
			List<String> list = new ArrayList<>();
			Bukkit.getServer().getOnlinePlayers().forEach(player -> list.add(ChatColor.stripColor(player.getDisplayName())));
			return list;
		}
		
		@Override
		public List<String> getTabable(CommandSender commandSender, String[] params) {
			List<String> list = new ArrayList<>();
			StringUtil.copyPartialMatches(params[params.length-1], getPlayerNames(), list);
			Collections.sort(list);
			return list;
		}

		@Override
		public String getTabableName() {
			return "player";
		}
		
	}
	
	public static class TabableString implements ITabable {
		
		private List<String> string = new ArrayList<>();
		
		public TabableString(String string) {
			this.string.add(string);
		}
		
		@Override
		public List<String> getTabable(CommandSender commandSender, String[] params) {
			return string;
		}
		
		@Override
		public String getTabableName() {
			return string.get(0);
		}
		
	}
	
	public static class TabableStrings implements ITabable {
		
		private List<String> string = new ArrayList<>();
		
		public TabableStrings(String... strings) {
			for (String s : strings) 
				this.string.add(s);
		}
		
		@Override
		public List<String> getTabable(CommandSender commandSender, String[] params) {
			return string;
		}
		
		@Override
		public String getTabableName() {
			return "string";
		}
		
	}
}
