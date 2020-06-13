package eu.pixliesearth.nations.commands;

import eu.pixliesearth.Main;
import eu.pixliesearth.nations.commands.subcommand.RegisterSub;
import eu.pixliesearth.nations.commands.subcommand.SubCommand;
import eu.pixliesearth.nations.commands.subcommand.nation.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.reflections.Reflections;

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
                sendHelp(sender);
        } else {
            sendHelp(sender);
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

    //TODO IMPLEMENT BRIGADER/COMMODORE
/*    private static void registerCompletions(Commodore commodore, PluginCommand command) {
        commodore.register(command, LiteralArgumentBuilder.literal("nations")
                .then(RequiredArgumentBuilder.argument("some-argument", StringArgumentType.string()))
                .then(RequiredArgumentBuilder.argument("some-other-argument", BoolArgumentType.bool()))
        );
    }*/

    public void sendHelp(CommandSender sender) {
        sender.sendMessage("Coming soon");
    }

}
