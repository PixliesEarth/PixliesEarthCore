package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
import eu.pixliesearth.core.custom.CustomSubCommand;
import eu.pixliesearth.core.custom.commands.subcommands.war.JustifyWarGoalCommand;
import eu.pixliesearth.core.custom.interfaces.ITabable;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.nations.entities.nation.ranks.Permission;
import eu.pixliesearth.nations.managers.NationManager;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.warsystem.War;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class WarCommand extends CustomCommand {

    @Override
    public String getCommandName() {
        return "war";
    }
    
    @Override
    public String getCommandDescription() {
    	return "War command";
    }
    
    @Override
    public boolean isPlayerOnlyCommand() {
    	return true;
    }
    
    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
        return true;
    }
    
    @Override
    public ITabable[] getParams() {
    	return new ITabable[] {new CustomSubCommand.TabableSubCommand(new JustifyWarGoalCommand())};
    }
    
    public static class TabableNation implements ITabable {

		@Override
		public List<String> getTabable(CommandSender commandSender, String[] params) {
			List<String> list = new ArrayList<>();
			StringUtil.copyPartialMatches(params[params.length-1], Methods.convertSetIntoList(NationManager.names.keySet()), list);
	        Collections.sort(list);
	        return list;
		}
    	
		@Override
		public String getTabableName() {
			return "nation";
		}
		
    }
    
}
