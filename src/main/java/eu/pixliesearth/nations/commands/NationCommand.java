package eu.pixliesearth.nations.commands;

import eu.pixliesearth.events.NationCommandExecuteEvent;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.commands.subcommand.nation.*;
import eu.pixliesearth.utils.Methods;
import io.sentry.Sentry;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class NationCommand implements CommandExecutor, TabExecutor {
	
	public NationCommand() {
		subCommands = new HashSet<SubCommand>();
		// register subCommands
        subCommands.add(new createNation());
        subCommands.add(new claimNation());
        subCommands.add(new disbandNation());
        subCommands.add(new inviteNation());
        subCommands.add(new renameNation());
        subCommands.add(new unclaimNation());
        subCommands.add(new descriptionNation());
        subCommands.add(new joinNation());
        subCommands.add(new leaveNation());
        subCommands.add(new rankNation());
        subCommands.add(new mapNation());
        subCommands.add(new kickNation());
        subCommands.add(new infoNation());
        subCommands.add(new helpNation());
        subCommands.add(new handoverCommand());
        subCommands.add(new listNation());
        subCommands.add(new settlementsCommand());
        subCommands.add(new menuNation());
        subCommands.add(new allyNation());
        subCommands.add(new neutralNation());
        subCommands.add(new chatNation());
        // subCommands.add(new bankNation());
        subCommands.add(new bannerNation());
        subCommands.add(new foreignPermission());
        subCommands.add(new accessNation());
        subCommands.add(new flagNation());
        subCommands.add(new xpNation());
        subCommands.add(new wikiNation());
        subCommands.add(new mergeNation());
        subCommands.add(new capitalNation());

		SubCommandAliases = new HashMap<String, SubCommand>();
		for (SubCommand subCommand : subCommands)
		    for (String s : subCommand.aliases())
		        SubCommandAliases.put(s, subCommand);
	}
	
	private @Getter final Set<SubCommand> subCommands;
	private @Getter final Map<String, SubCommand> SubCommandAliases;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {
        if (strings.length == 0) {
            sendHelp(sender, 1);
            return false;
        }
        List<String> list = new ArrayList<>(Arrays.asList(strings));
        list.remove(strings[0]);
        String[] args = list.toArray(new String[0]);

	    if (!SubCommandAliases.containsKey(strings[0].toLowerCase())) {
    		sendHelp(sender, 1);
    		return false;
    	}

	    try {
            NationCommandExecuteEvent event = new NationCommandExecuteEvent(sender, SubCommandAliases.get(strings[0].toLowerCase()));
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) SubCommandAliases.get(strings[0].toLowerCase()).execute(sender, args);
        } catch (Exception e) {
            getSubCommandAliases().get(strings[0].toLowerCase()).sendSyntax(sender, strings[0]);
            e.printStackTrace();
             io.sentry.Sentry.captureException(e);
	    }

	    return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();

        Map<Integer, Set<String>> argCompletions = new HashMap<>();

        argCompletions.put(0, getSubCommandAliases().keySet());

        if (args.length > 1) {
            if (getSubCommandAliases().get(args[0]) == null)
                return completions;
            for (Map.Entry<String, Integer> entry : getSubCommandAliases().get(args[0]).autoCompletion(sender, args).entrySet())
                if (args.length == entry.getValue() + 1) {
                    argCompletions.computeIfAbsent(entry.getValue(), k -> new HashSet<>());
                    argCompletions.get(entry.getValue()).add(entry.getKey());
                }
        }

        for (Map.Entry<Integer, Set<String>> entry : argCompletions.entrySet())
            if (args.length == entry.getKey() + 1)
                StringUtil.copyPartialMatches(args[entry.getKey()], entry.getValue(), completions);

        Collections.sort(completions);

        return completions;
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
                // sender.sendMessage("§b* §7/n bank §c<deposit/withdraw/balance> [AMOUNT/NATION] §e[NATION]");
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
