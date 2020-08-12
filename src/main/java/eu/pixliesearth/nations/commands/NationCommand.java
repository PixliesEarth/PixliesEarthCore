package eu.pixliesearth.nations.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.commands.subcommand.nation.*;
import eu.pixliesearth.utils.Methods;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class NationCommand implements CommandExecutor, TabExecutor {

    public Set<SubCommand> getSubCommands() {
        // ADD ALL SUBCOMMANDS INTO THIS HASHSET<>();
        Set<SubCommand> subCommands = new HashSet<>();
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
        subCommands.add(new bankNation());
        subCommands.add(new topNation());
        subCommands.add(new flagNation());
        subCommands.add(new foreignPermission());
        subCommands.add(new accessNation());
        return subCommands;
    }

    public Map<String, SubCommand> subMap() {
        Map<String, SubCommand> map = new HashMap<>();
        for (SubCommand sub : getSubCommands())
            for (String s : sub.aliases())
                map.put(s, sub);
        return map;
    }

    public List<String> getSubCommandAliases() {
        // ADD ALL SUBCOMMAND ALIASES INTO THIS HASHSET
        List<String> subCommandAliases = new ArrayList<>();
        for (SubCommand sub : getSubCommands())
            subCommandAliases.addAll(Arrays.asList(sub.aliases()));
        return subCommandAliases;
    }

    private static Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {

        /*
                GO THROUGH ALL SUBCOMMANDS AND THEIR ALIASES,
                IF ONE OF THEM MATCHES WITH THE STRINGS[0],
                EXECUTE SUBCOMMAND.
         */
        boolean found = false;
        if (strings.length > 0) {

            List<String> list = new ArrayList<>(Arrays.asList(strings));
            list.remove(strings[0]);
            String[] args = list.toArray(new String[0]);

            for (SubCommand sub : getSubCommands())
                for (String s : sub.aliases())
                    if (strings[0].equalsIgnoreCase(s) ) {
                        found = true;
                        if (sub.staff() && sender instanceof Player) {
                            if (instance.getUtilLists().staffMode.contains(((Player) sender).getUniqueId())) {
                                sub.execute(sender, args);
                            } else {
                                sender.sendMessage("§aEARTH §8| §cInsufficient permissions.");
                            }
                        } else {
                            sub.execute(sender, args);
                        }
                    }
            if (!found)
                sendHelp(sender, 1);
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Profile profile = instance.getProfile(player.getUniqueId());
                infoNation.sendNationInfo(profile.getCurrentNation(), player);
            } else  {
                sendHelp(sender, 1);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        final List<String> completions = new ArrayList<>();

        Map<Integer, List<String>> argCompletions = new HashMap<>();

        argCompletions.put(0, getSubCommandAliases());

        if (args.length > 1) {
            if (subMap().get(args[0]) == null)
                return completions;
            for (Map.Entry<String, Integer> entry : subMap().get(args[0]).autoCompletion().entrySet())
                if (args.length == entry.getValue() + 1) {
                    argCompletions.computeIfAbsent(entry.getValue(), k -> new ArrayList<>());
                    argCompletions.get(entry.getValue()).add(entry.getKey());
                }
        }

        for (Map.Entry<Integer, List<String>> entry : argCompletions.entrySet())
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
                sender.sendMessage("§b* §7/n list");
                sender.sendMessage("§b* §7/n ally §c<NATION>");
                sender.sendMessage("§b* §7/n neutral §c<NATION>");
                sender.sendMessage("§b* §7/n menu");
                break;
            case 3:
                sender.sendMessage("§b* §7/n chat §c<public/ally/nation>");
                sender.sendMessage("§b* §7/n bank §c<deposit/withdraw/balance> [AMOUNT/NATION] §e[NATION]");
                sender.sendMessage("§b* §7/n top");
                sender.sendMessage("§b* §7/n setflag");
                sender.sendMessage("§b* §7/n fp §cset/unset PERMISSION nation/player §eNATIONNAME/PLAYERNAME");
                sender.sendMessage("§b* §7/n access §cplayer/nation PLAYERNAME/NATIONNAME §eset/unset");
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
