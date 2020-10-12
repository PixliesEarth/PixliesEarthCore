package eu.pixliesearth.nations.commands;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.utils.Methods;
import lombok.Getter;
import lombok.SneakyThrows;

public class NationCommand implements CommandExecutor, TabExecutor {
	
	public NationCommand() {
		subCommands = new HashSet<SubCommand>();
		SubCommandAliases = new HashSet<String>();
		loadSubCommands("eu.pixliesearth.nations.commands.subcommand.nation");
	}
	
	private @Getter Set<SubCommand> subCommands;
	private @Getter Set<String> SubCommandAliases;
	
	@SneakyThrows
    public void loadSubCommands(String path) {
    	for (Class<? extends SubCommand> clazz : CustomFeatureLoader.reflectBasedOnExtentionOf(path, SubCommand.class))
    		registerSubcommand(clazz.newInstance());
    }
    
    public void registerSubcommand(SubCommand subCommand) {
    	this.subCommands.add(subCommand);
    	for (String s : subCommand.aliases()) 
    		SubCommandAliases.add(s);
    	System.out.println("Regsitered the nations subcommand" + subCommand.getClass().getName());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {
        List<String> list = new ArrayList<>(Arrays.asList(strings));
        list.remove(strings[0]);
        String[] args = list.toArray(new String[0]);

	    if (!SubCommandAliases.contains(args[0])) {
    		sendHelp(sender, 1);
    		return false;
    	}

    	for (SubCommand c : getSubCommands()) {
    		for (String s : c.aliases()) 
    			if (s.equals(args[0]))
    				return c.execute(sender, args);
    	}
    	
    	sendHelp(sender, 1);
    	return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    	
    	List<String> array = new ArrayList<String>();
    	
    	if (args.length<1) {
    		for (String s : getSubCommandAliases()) 
    			array.add(s);
    		Collections.sort(array);
        	return array;
    	} else {
    		String arg = args[0];
    		for (SubCommand c : getSubCommands()) {
        		for (String s : c.aliases()) 
        			if (s.equals(arg)) {
        				if (c.autoCompletion() == null) 
        					return array;
        				for (Entry<String, Integer> entry: c.autoCompletion().entrySet()) 
        					if (entry.getValue().intValue()==(args.length+1)) 
        						array.add(entry.getKey());
        				return array;
        			}
        	}
    		array.add("?");
    		return array;
    	}
    }

    public static void sendHelp(CommandSender sender, int page) {
        sender.sendMessage(Methods.getCenteredMessage("&7-= &b&lNATIONS &7=-"));
        switch (page) {
            case 2:
                sender.sendMessage("§b* §7/n rank §c<create/addpermission/remove/removepermission> <RANKNAME> §e[PREFIX/PERMISSIONNAME] §b[RANKPRIORITY]");
                sender.sendMessage("§b* §7/n kick §c<player>");
                sender.sendMessage("§b* §7/n map §c<chat/gui/scoreboard>");
                sender.sendMessage("§b* §7/n info §c<NATION/player> §c[player]");
                sender.sendMessage("§b* §7/n settlements §c[add/remove] [NAME]");
                sender.sendMessage("§b* §7/n list §c[PAGE]");
                sender.sendMessage("§b* §7/n ally §c<NATION>");
                sender.sendMessage("§b* §7/n neutral §c<NATION>");
                sender.sendMessage("§b* §7/n menu");
                break;
            case 3:
                sender.sendMessage("§b* §7/n chat §c<public/ally/nation>");
                sender.sendMessage("§b* §7/n bank §c<deposit/withdraw/balance> [AMOUNT/NATION] §e[NATION]");
                sender.sendMessage("§b* §7/n top §c[PAGE]");
                sender.sendMessage("§b* §7/n setbanner");
                sender.sendMessage("§b* §7/n fp §cset/unset PERMISSION nation/player §eNATIONNAME/PLAYERNAME");
                sender.sendMessage("§b* §7/n access §cplayer/nation PLAYERNAME/NATIONNAME §eset/unset");
                sender.sendMessage("§b* §7/n flag <FLAG> [NATION]");
                break;
            default:
                sender.sendMessage("§b* §7/n create §c<NAME>");
                sender.sendMessage("§b* §7/n disband §c[NAME]");
                sender.sendMessage("§b* §7/n description §c<DESCRIPTION...>");
                sender.sendMessage("§b* §7/n invite §c<PLAYER> [add/remove] §e[NATION]");
                sender.sendMessage("§b* §7/n join §c<NATION> [PLAYER]");
                sender.sendMessage("§b* §7/n leave");
                sender.sendMessage("§b* §7/n claim §c<ONE/AUTO/FILL> [NATION]");
                sender.sendMessage("§b* §7/n unclaim §c<ONE/AUTO/FILL> [NATION]");
                sender.sendMessage("§b* §7/n rename §c<NAME> [NATION]");
                break;
        }
        sender.sendMessage(Methods.getCenteredMessage("§c<> = required &8| &c[] = Optional"));
        sender.sendMessage(Methods.getCenteredMessage("§7-= Page &b" + page + "&8/&b3 &7=-"));
    }

}
