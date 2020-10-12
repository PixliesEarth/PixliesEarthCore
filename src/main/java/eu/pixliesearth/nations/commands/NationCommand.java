package eu.pixliesearth.nations.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.commands.subcommand.nation.infoNation;
import eu.pixliesearth.nations.commands.subcommand.nation.menuNation;
import eu.pixliesearth.utils.Methods;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class NationCommand implements CommandExecutor, TabExecutor {

    private static Map<String, SubCommand> subCommands;

    public Collection<SubCommand> getSubCommands() {
        return subCommands.values();
    }

    @SneakyThrows
    public static void init() {
        subCommands = new HashMap<>();
        for (Class<? extends SubCommand> clazz : CustomFeatureLoader.reflectBasedOnExtentionOf("eu.pixliesearth.nations.commands.subcommand.nation", SubCommand.class)) {
            SubCommand cmd = clazz.getConstructor().newInstance();
            System.out.println("Registering subcommand " + clazz.getName() + " for command Nation");
            for (String s : cmd.aliases())
                subCommands.put(s, cmd);
            System.out.println("Registered subcommand " + clazz.getName() + " for command Nation");
        }
    }

    public Map<String, SubCommand> subMap() {
        return subCommands;
    }

    public Set<String> getSubCommandAliases() {
        return subCommands.keySet();
    }

    private static final Main instance = Main.getInstance();

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
                                Lang.NO_PERMISSIONS.send(sender);
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
                if (profile.isInNation()) {
                    infoNation.sendNationInfo(profile.getCurrentNation(), player);
                } else {
                    sendHelp(sender, 1);
                }
            } else  {
                sendHelp(sender, 1);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        final List<String> completions = new ArrayList<>();

        Map<Integer, Set<String>> argCompletions = new HashMap<>();

        argCompletions.put(0, getSubCommandAliases());

        if (args.length > 1) {
            if (subMap().get(args[0]) == null)
                return completions;
            if (subMap().get(args[0]).autoCompletion() == null) return completions;
            for (Map.Entry<String, Integer> entry : subMap().get(args[0]).autoCompletion().entrySet())
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
