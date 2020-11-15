package eu.pixliesearth.core.custom.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomCommand;
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
    	return "";
    }
    
    @Override
    public boolean isPlayerOnlyCommand() {
    	return true;
    }
    
    @Override
    public boolean onExecuted(CommandSender commandSender, String aliasUsed, String[] parameters, boolean ranByPlayer) {
    	if (parameters.length != 1) {
            Lang.WRONG_USAGE_NATIONS.send(commandSender, "%USAGE%;/war <NATION>");
            return false;
        }
        Main instance = Main.getInstance();
        Player player = (Player) commandSender;
        Profile profile = instance.getProfile(player.getUniqueId());
        if (!profile.isInNation()) {
            Lang.NOT_IN_A_NATION.send(commandSender);
            return false;
        }
        if (!Permission.hasNationPermission(profile, Permission.JUSTIFY_WAR_GOAL)) {
            Lang.NO_PERMISSIONS.send(commandSender);
            return false;
        }
        Nation aggressor = profile.getCurrentNation();
        Nation defender = Nation.getByName(parameters[0]);
        if (defender == null) {
            Lang.NATION_DOESNT_EXIST.send(commandSender);
            return false;
        }
        War war = new War(aggressor.getNationId(), defender.getNationId());
        boolean justify = war.justifyWarGoal();
        if (!justify) {
        	commandSender.sendMessage(Lang.WAR + "§cWar-goal justification failed. This could be either because you don't have enough PoliticalPower to justify a war-goal, or that you are already justifying a war-goal against this nation.");
            return false;
        }
        return true;
    }
    
    @Override
    public ITabable[] getParams() {
    	return new ITabable[] {new TabableNation()};
    }

    /**@Override
    public List<String> tabComplete(CommandSender commandsender, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();

        Map<Integer, Set<String>> argCompletions = new HashMap<>();

        argCompletions.put(0, NationManager.names.keySet());

        if (args.length > 1) {
            if (NationManager.names.get(args[0]) == null)
                return completions;
        }

        for (Map.Entry<Integer, Set<String>> entry : argCompletions.entrySet())
            if (args.length == entry.getKey() + 1)
                StringUtil.copyPartialMatches(args[entry.getKey()], entry.getValue(), completions);

        Collections.sort(completions);

        return completions;
    }*/
    
    private static class TabableNation implements ITabable {

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
